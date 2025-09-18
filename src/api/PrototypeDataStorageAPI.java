package api;

import project.annotations.ProcessAPIPrototype;

public class PrototypeDataStorageAPI {

    @ProcessAPIPrototype
    public void prototype(DataStorageAPI api) {
    	// Defines the destination from the user
        OutputType destination = new OutputType() {}; 

        // Destination request
        StorageWriteRequest request = new StorageWriteRequest(destination);
        // Response
        StorageWriteResponse response = api.writeResults(request);

        if (response.getCode() == StorageStatusCode.SUCCESS) {
            // Results stored
        } else if (response.getCode() == StorageStatusCode.INVALID_REQUEST) {
            // Destination input is invalid
        } else if (response.getCode() == StorageStatusCode.STORAGE_UNAVAILABLE) {
            // Storage currently unavailable
        } else {
            // Future status codes
        }
    }
}