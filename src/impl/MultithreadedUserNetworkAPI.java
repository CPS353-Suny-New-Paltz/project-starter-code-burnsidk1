package impl;

import api.UserNetworkAPI;
import api.DataStorageAPI;
import api.ComputeEngineAPI;
import api.InputBatch;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Created class extending AbstractUserNetworkAPI for Multithreaded implementation
public class MultithreadedUserNetworkAPI extends AbstractUserNetworkAPI implements UserNetworkAPI {
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
        return null;
    }
}