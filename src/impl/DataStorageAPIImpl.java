package impl;

import api.*;
import java.util.Collections;
import java.util.List;

public class DataStorageAPIImpl implements DataStorageAPI {

    private final DataStore dataStore; 

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