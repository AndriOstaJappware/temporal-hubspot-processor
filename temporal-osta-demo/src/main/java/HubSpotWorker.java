package temporalostademo;

import activity.HubSpotActivities;
import activity.HubspotActivitiesImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.WorkerFactory;
import model.HubSpotEntities;
import workflow.HubSpotProcessingWorkflow;
import workflow.HubSpotProcessingWorkflowImpl;

public class HubSpotWorker {

  private static final String TASK_QUEUE = "HubspotProcessor";

  public static void main(String[] args) {
    WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
    WorkflowClient client = WorkflowClient.newInstance(service);

    WorkerFactory factory = WorkerFactory.newInstance(client);
    var worker = factory.newWorker(TASK_QUEUE);

    worker.registerWorkflowImplementationTypes(HubSpotProcessingWorkflowImpl.class);

    HubSpotActivities activities = new HubspotActivitiesImpl(HubSpotEntities.COMPANIES.getLink(), 100);
    worker.registerActivitiesImplementations(activities);

    factory.start();
    System.out.println("Worker started for task queue: " + TASK_QUEUE);

    WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build();
    HubSpotProcessingWorkflow workflow = client.newWorkflowStub(HubSpotProcessingWorkflow.class, options);

    try {
      workflow.process("apiKey");
    } catch (WorkflowException e) {
      e.printStackTrace();
    }

    // Do not exit immediately, keep JVM alive for worker threads (optional enhancement)
    // For demo purposes, exiting here:
    System.exit(0);
  }
}
