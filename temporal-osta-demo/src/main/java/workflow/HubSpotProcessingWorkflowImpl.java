package workflow;

import activity.HubSpotActivities;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import java.util.List;
import jsontopojo.CompanyProperties;
import model.Company;


public class HubSpotProcessingWorkflowImpl implements HubSpotProcessingWorkflow {

  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setScheduleToCloseTimeout(Duration.ofHours(1))
          // disable retries for example to run faster
          .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build())
          .build();
  private final HubSpotActivities activities =
      Workflow.newActivityStub(HubSpotActivities.class, options);

  @Override
  public void process(String apiKey) {
    // Configure SAGA to run compensation activities in parallel
    Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
    Saga saga = new Saga(sagaOptions);
    try {
      while (!activities.isExit()) {
        List<CompanyProperties> companyProperties =
            activities.read(apiKey);
        // saga.addCompensation(activities::process, companyProperties);
        List<Company> companies = activities.process(companyProperties);
        activities.write(companies);
      }

    } catch (ActivityFailure e) {
      saga.compensate();
      throw e;
    }
  }
}
