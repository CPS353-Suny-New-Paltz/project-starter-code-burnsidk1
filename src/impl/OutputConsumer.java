package impl;

import api.DataStorageAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class OutputConsumer implements Runnable {
    private final DataStorageAPI dataStorageApi;
    private final String outputLocation;
    private final BlockingQueue<ComputeItem> resultQueue;
    private final int expectedResultCount;
    private volatile boolean shutdown = false;
    private volatile boolean failed = false;
    private volatile Exception failureException = null;
    
    // Constructor for the OutputConsumer
    public OutputConsumer(DataStorageAPI dataStorageApi, String outputLocation,
                       BlockingQueue<ComputeItem> resultQueue,
                       int expectedResultCount) {
        this.dataStorageApi = dataStorageApi;
        this.outputLocation = outputLocation;
        this.resultQueue = resultQueue;
        this.expectedResultCount = expectedResultCount;
    }
    
    @Override
    public void run() {
        try {
            // Array that holds results in original order
            String[] orderedResults = new String[expectedResultCount];
            // Counter for received results
            int receivedCount = 0;
            
            // Collect all results
            while (receivedCount < expectedResultCount && !shutdown) {
                // Poll with timeout to allow checking shutdown flag
                ComputeItem workItem = resultQueue.poll(100, TimeUnit.MILLISECONDS);
                
                // If no item, then continue to next iteration
                if (workItem == null) {
                    continue;
                }
                
                // Place result in correct position based on their original index
                int index = workItem.getOriginalIndex();
                if (index >= 0 && index < expectedResultCount) {
                    orderedResults[index] = workItem.getResult();
                    // Increment received count so there is a record of how many have been received
                    receivedCount++;
                }
            }
            
            // Convert array to list
            List<String> resultList = new ArrayList<>();
            for (String result : orderedResults) {
                // Only add non-null results
                if (result != null) {
                    resultList.add(result);
                }
            }
            
            // Write to storage
            if (!resultList.isEmpty()) {
                dataStorageApi.writeOutputs(outputLocation, resultList);
            }
            
        // Catch exceptions and mark failures
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            failed = true;
            failureException = e;
        } catch (Exception e) {
            failed = true;
            failureException = e;
        }
    }
    
    // Shutdown method to stop the Consumer
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
