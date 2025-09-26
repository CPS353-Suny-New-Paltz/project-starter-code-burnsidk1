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
    
    public DataStorageAPIImpl() { 
    	this.dataStore = null; 
    	}

    public DataStorageAPIImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

	@Override
    public InputBatch readInputs(String inputLocation) {
        return null;
    }

    @Override
    public WriteResult writeOutputs(String outputLocation, List<String> formattedPairs) {
        return null;
    }

    @Override
    public StorageWriteResponse writeResults(StorageWriteRequest request) {
        return null;
    }
}