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
}