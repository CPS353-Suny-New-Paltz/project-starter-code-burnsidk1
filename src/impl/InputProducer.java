package impl;

import api.DataStorageAPI;
import api.InputBatch;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class InputProducer implements Runnable {
    // Storage API
    private final DataStorageAPI dataStorageApi;
    // Input location to read from
    private final String inputLocation;
    // Queue to add work items to
    private final BlockingQueue<ComputeItem> workQueue;
    private volatile boolean failed = false;
    private volatile Exception failureException = null;
    
    // Constructor for the Producer
    public InputProducer(DataStorageAPI dataStorageApi, String inputLocation, 
                        BlockingQueue<ComputeItem> workQueue) {
        this.dataStorageApi = dataStorageApi;
        this.inputLocation = inputLocation;
        this.workQueue = workQueue;
    }
    
    // Run method to read inputs and add to queue
    @Override
    public void run() {
        try {
            // try to read inputs from storage
            InputBatch batch = dataStorageApi.readInputs(inputLocation);
            
            if (batch == null || batch.values() == null) {
                return; // No inputs to process
            }
            
            // Convert inputs to work items and add to queue
            List<Integer> values = batch.values();
            for (int i = 0; i < values.size(); i++) {
                Integer value = values.get(i);
                // Only add valid positive integers
                if (value != null && value > 0) {
                    // Create a new ComputeItem with the value and its original index
                    ComputeItem workItem = new ComputeItem(value, i);
                    // Add the work item to the queue
                    workQueue.put(workItem); // Blocks if queue is full
                }
            }
            
        // Catch exceptions and mark failure
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            failed = true;
            failureException = e;
        } catch (Exception e) {
            failed = true;
            failureException = e;
        }
    }
    
    // Check if the producer failed
    public boolean hasFailed() {
        return failed;
    }
    
    // Get the exception that caused the failure
    public Exception getFailureException() {
        return failureException;
    }
}
