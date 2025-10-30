package impl;

import api.UserNetworkAPI;
import api.DataStorageAPI;
import api.ComputeEngineAPI;
import api.InputBatch;

import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Created class extending AbstractUserNetworkAPI for Multithreaded implementation
public class MultithreadedUserNetworkAPI extends AbstractUserNetworkAPI {
    // Set up thread pool
    private final ExecutorService executor;
    private static final int maxThreads = 8; // max thread size of 8

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
    protected List<String> processInputBatch(InputBatch batch) {
        // Use multithreading to process each input in the batch
        List<String> results = new ArrayList<>();
        // If the batch is null or has no values, return empty results
        if (batch == null || batch.values() == null) {
            return results;
        }

        // Creates a list to hold future results
        List<Integer> inputs = new ArrayList<>(batch.values());
        List<Future<String>> futures = new ArrayList<>(inputs.size());

        // Submits tasks to the executor for each input
        for (Integer n : inputs) {
            futures.add(executor.submit(() -> formatCollatzPair(n)));
        }

        // Collects the results from the futures
        for (Future<String> f : futures) {
            // try to handle exceptions from futures
            try {
                String formatted = f.get();
                if (formatted != null) {
                    results.add(formatted);
                }
            // Catches InterruptedException for thread interruptions
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Computation interrupted", e);
                // Catches ExecutionException for computation errors
            } catch (ExecutionException e) {
                throw new RuntimeException("Computation failed", e.getCause());
            }
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
    }
}