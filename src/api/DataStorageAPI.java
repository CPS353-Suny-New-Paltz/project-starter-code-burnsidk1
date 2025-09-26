package api;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStorageAPI {
    // Store the results
    StorageWriteResponse writeResults(StorageWriteRequest request);
    InputBatch readInputs(String inputLocation);
    WriteResult writeOutputs(String outputLocation, java.util.List<String> formattedPairs);
}