package impl;

import java.util.List;

import api.DataStorageAPI;
import api.DataStore;
import api.InputBatch;
import api.StorageWriteRequest;
import api.StorageWriteResponse;
import api.WriteResult;

public class DataStorageAPIImpl implements DataStorageAPI {

    private final DataStore dataStore; 
    // No argument constructor for the smoke test
    public DataStorageAPIImpl() { 
    	this.dataStore = null; 
    	}

    // Storage dependency
    public DataStorageAPIImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    // Read integers from a user-defined location
	@Override
    public InputBatch readInputs(String inputLocation) {
        return null; // Returns null for now
    }

	// Write outputs to destination
    @Override
    public WriteResult writeOutputs(String outputLocation, List<String> formattedPairs) {
        return null; // Returns null for now
    }

    // Wrapper for the request/response
    @Override
    public StorageWriteResponse writeResults(StorageWriteRequest request) {
        return null; // Returns null for now
    }
}