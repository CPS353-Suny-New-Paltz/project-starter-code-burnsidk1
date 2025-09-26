package api;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStorageAPI {
    // Store the results
    StorageWriteResponse writeResults(StorageWriteRequest request);
}