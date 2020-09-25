

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.WorkerFactory;
import model.HubSpotEntities;
import activity.HubSpotActivities;
import workflow.HubSpotProcessingWorkflow;
import workflow.HubSpotProcessingWorkflowImpl;
import activity.HubspotActivitiesImpl;

public  class HubSpotWorker {

  static final String TASK_QUEUE = "HubspotProcessor";

  @SuppressWarnings("CatchAndPrintStackTrace")
  public static void main(String[] args) {
    // gRPC stubs wrapper that talks to the local docker instance of temporal service.
    WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
    // client that can be used to start and signal workflows
    WorkflowClient client = WorkflowClient.newInstance(service);

    // worker factory that can be used to create workers for specific task queues
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Worker that listens on a task queue and hosts both workflow and activity implementations.
    io.temporal.worker.Worker worker = factory.newWorker(TASK_QUEUE);

    // Workflows are stateful. So you need a type to create instances.
    worker.registerWorkflowImplementationTypes(HubSpotProcessingWorkflowImpl.class);

    // Activities are stateless and thread safe. So a shared instance is used.
    HubSpotActivities tripBookingActivities =
        new HubspotActivitiesImpl(HubSpotEntities.COMPANIES.getLink(), 100);
    worker.registerActivitiesImplementations(tripBookingActivities);

    // Start all workers created by this factory.
    factory.start();
    System.out.println("Worker started for task queue: " + TASK_QUEUE);

    // now we can start running instances of our saga - its state will be persisted
    WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build();
    HubSpotProcessingWorkflow process1 = client.newWorkflowStub(HubSpotProcessingWorkflow.class, options);
    try {
      process1.process("apiKey");
    } catch (WorkflowException e) {
      // Expected
    }

    System.exit(0);
  }
}