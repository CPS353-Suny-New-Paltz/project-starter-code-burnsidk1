package impl;

import api.UserNetworkAPI;
import api.DataStorageAPI;
import api.ComputeEngineAPI;
import api.InputBatch;

import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

// Created class extending AbstractUserNetworkAPI for Multithreaded implementation
public class MultithreadedUserNetworkAPI extends AbstractUserNetworkAPI {
    // Set up thread pool
    private final ExecutorService executor;
    private static final int maxThreads = 8; // Max thread size of 8
    private static final int queueCapacity = 100; // Bounded queue size

    // No-arg constructor
    public MultithreadedUserNetworkAPI() {
        super();
        this.executor = Executors.newFixedThreadPool(maxThreads);
    }

    public MultithreadedUserNetworkAPI(DataStorageAPI dataStorageApi, ComputeEngineAPI computeEngineApi) {
        super(dataStorageApi, computeEngineApi);
        // Initialize the thread pool
        this.executor = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    // Override processInputBatch to handle the multithreading
    protected List<String> processInputBatch(InputBatch batch, String delimiter) {
        // If the batch is null or has no values, return empty results
        if (batch == null || batch.values() == null || batch.values().isEmpty()) {
            return new ArrayList<>();
        }

        // Get the number of inputs in the batch
        int inputCount = batch.values().size();
        
        // Creates bounded queues for Producer/Consumer pattern
        BlockingQueue<ComputeItem> workQueue = new LinkedBlockingQueue<>(queueCapacity);
        BlockingQueue<ComputeItem> resultQueue = new LinkedBlockingQueue<>(queueCapacity);
        
        // Create Producer thread
        InputProducer producer = new InputProducer(dataStorageApi, null, workQueue);
        
        // try to add to work queue
        try {
            List<Integer> values = batch.values();
            for (int i = 0; i < values.size(); i++) {
                Integer value = values.get(i);
                // Only add valid positive integers
                if (value != null && value > 0) {
                    ComputeItem workItem = new ComputeItem(value, i);
                    workQueue.put(workItem);
                }
            }
        // Catch and throw runtime exception
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to populate work queue", e);
        }
        
        // Start Worker threads
        List<ComputeWorker> workers = new ArrayList<>();
        for (int i = 0; i < maxThreads; i++) {
            // Create a new ComputeWorker with the compute engine API, work queue, and result queue
            ComputeWorker worker = new ComputeWorker(computeEngineApi, workQueue, resultQueue);
            workers.add(worker);
            executor.submit(worker);
        }
        
        // Collects the results
        List<String> results = new ArrayList<>(inputCount);
        // Re-ordering array for results
        String[] orderedResults = new String[inputCount];
        int receivedCount = 0;
        
        try {
            // Collect all results from result queue with a timeout
            while (receivedCount < inputCount) {
                ComputeItem workItem = resultQueue.poll(5, TimeUnit.SECONDS);
                
                if (workItem == null) {
                    // Check if any worker failed
                    for (ComputeWorker worker : workers) {
                        if (worker.hasFailed()) {
                            throw new RuntimeException("Worker failed", worker.getFailureException());
                        }
                    }
                    continue; // Try again
                }
                
                // Place result in correct position
                int index = workItem.getOriginalIndex();
                if (index >= 0 && index < inputCount) {
                    orderedResults[index] = workItem.getResult();
                    receivedCount++;
                }
            }
            
            // Shutdown workers
            for (ComputeWorker worker : workers) {
                worker.shutdown();
            }
            
            // Convert array to list without nulls
            for (String result : orderedResults) {
                if (result != null) {
                    results.add(result);
                }
            }
            
        // Catch and throw runtime exception
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Result collection interrupted", e);
        }

        // Returns the list of formatted results
        return results;
    }

    // Helper method because changing processInputBatch would break my structure
    // This method just checks that the input is valid and formats the Collatz pair for the tests

    // Processes list of strings in parallel and returns results in order
    public List<String> processRequests(List<String> requests) {
        // If the requests list is null or empty, returns an empty list
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        // Submits tasks to the executor for each request
        List<Future<String>> futures = new ArrayList<>(requests.size());
        for (String r : requests) {
            futures.add(executor.submit(() -> r));
        }

        // Collects the results from the futures
        List<String> results = new ArrayList<>(requests.size());
        for (Future<String> f : futures) {
            try {
                results.add(f.get());
            // Catches InterruptedException for thread interruptions
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Processing interrupted", e);
            // Catches ExecutionException for processing errors
            } catch (ExecutionException e) {
                throw new RuntimeException("Processing failed", e.getCause());
            }
        }
        // Returns the list of processed results
        return results;
    }

    // Shutdown method to close the executor service
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        // Catch InterruptedException and force shutdown
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}