package impl;

import api.ComputeEngineAPI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ComputeWorker implements Runnable {
    // Compute engine API
    private final ComputeEngineAPI computeEngineApi;
    // Queue for work items
    private final BlockingQueue<ComputeItem> workQueue;
    // Queue for results
    private final BlockingQueue<ComputeItem> resultQueue;
    private volatile boolean shutdown = false;
    private volatile boolean failed = false;
    private volatile Exception failureException = null;
    
    // Constructor for the ComputeWorker
    public ComputeWorker(ComputeEngineAPI computeEngineApi,
                        BlockingQueue<ComputeItem> workQueue,
                        BlockingQueue<ComputeItem> resultQueue) {
        this.computeEngineApi = computeEngineApi;
        this.workQueue = workQueue;
        this.resultQueue = resultQueue;
    }
    
    @Override
    public void run() {
        try {
            while (!shutdown) {
                // Poll with timeout to check shutdown flag
                ComputeItem workItem = workQueue.poll(100, TimeUnit.MILLISECONDS);
                
                // If no item, check shutdown
                if (workItem == null) {
                    // Skips to next iteration if no work item is available
                    continue;
                }
                
                // Performs the computation
                long inputValue = workItem.getInputValue();
                String sequence = computeEngineApi.collatzSequenceString(inputValue);
                // Formats the result
                String formattedResult = inputValue + ":" + sequence;
                
                // Stores the result in the work item
                workItem.setResult(formattedResult);
                
                // Adds to result queue for the Consumer
                resultQueue.put(workItem); // Blocks if queue is full
            }
            
            // Handle shutdown
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            failed = true;
            failureException = e;
        } catch (Exception e) {
            failed = true;
            failureException = e;
        }
    }
    
    public void shutdown() {
        this.shutdown = true;
    }
    
    public boolean hasFailed() {
        return failed;
    }
    
    public Exception getFailureException() {
        return failureException;
    }
}
