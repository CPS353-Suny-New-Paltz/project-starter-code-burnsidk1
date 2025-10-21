package api;

import project.annotations.ProcessAPIPrototype;

public class PrototypeDataStorageAPI {

    @ProcessAPIPrototype
    public void prototype(DataStorageAPI api) {
        // Defines the destination from the user
        OutputConfig destination = new OutputConfig() {
            @Override
            public String getLocation() {
                return "output.txt"; // Placeholder location
            }
        };

        // Formatted pairs
        java.util.List<String> formattedPairs = java.util.Arrays.asList("1:1, 10, 25");

        // Destination request
        StorageWriteRequest request = new StorageWriteRequest(destination, formattedPairs);
        // Response
        StorageWriteResponse response = api.writeResults(request);
    }
}