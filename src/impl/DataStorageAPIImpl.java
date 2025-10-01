package impl;

import java.util.List;

import api.DataStorageAPI;
import api.DataStore;
import api.InputBatch;
import api.OutputConfig;
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
        // If the input location is null, returns null
        if (inputLocation == null) {
            return null;
        }
        if (dataStore != null) {
            // Read the integers from Datastore
            java.util.List<Integer> values = dataStore.readIntegers(inputLocation);
            if (values == null) {
                return null;
            }
            return new api.InputBatch(values);
        } else {
            // If no DataStore is provided, reads from a file directly
            java.util.List<Integer> values = new java.util.ArrayList<>();
            // Try statement for reading in the values from a file
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(inputLocation))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        try {
                            values.add(Integer.parseInt(line));
                            // Catches NumberFormatException if the line is not a valid integer
                        } catch (NumberFormatException exception) {
                            // Skips lines that are not valid
                        }
                    }
                }
                // Catches IOException if the file cannot be read
            } catch (java.io.IOException exception) {
                return null;
            }
        // Returns the list of integers
        return new api.InputBatch(values);
        }
    }

	// Writes outputs to destination
    @Override
    public WriteResult writeOutputs(String outputLocation, List<String> formattedPairs) {
        if (outputLocation == null || formattedPairs == null) {
             // If the output location or formatted pairs are null, returns false
            return new api.WriteResult(false, "Output location or formatted pairs is null");
        }

            // Make outputs into one line
            String line = String.join(",", formattedPairs);

            // DataStore write
            if (dataStore != null) {
                boolean good = dataStore.writeLines(outputLocation, java.util.List.of(line));
                return new api.WriteResult(good, good ? "Write successful" : "Write failed");
            }
        
        // Try statement for writing the values to a file
        try (java.io.BufferedWriter writer =
                new java.io.BufferedWriter(new java.io.FileWriter(outputLocation))) {
           writer.write(line);
     
            // Catches IOException if the file cannot be written to
        } catch (java.io.IOException e) {
            return new api.WriteResult(false, "IOException: " + e.getMessage());
        }
        // If successful, returns true
        return new api.WriteResult(true, "Write successful");
    }

    // Wrapper for the request/response
    @Override
    public StorageWriteResponse writeResults(StorageWriteRequest request) {
        if (request == null) {
            // If the request is null, returns INVALID_REQUEST
            return new api.StorageWriteResponse(api.StorageStatusCode.INVALID_REQUEST);
        }
        // Writes the outputs using the request data
        String outputLocation = null;
        if (request.getDestination() != null) {
            outputLocation = request.getDestination().getLocation();
        }
        api.WriteResult result = writeOutputs(outputLocation, request.getFormattedPairs());
        if (result != null && result.success()) {
            // If result is not null and successful, returns SUCCESS
            return new api.StorageWriteResponse(api.StorageStatusCode.SUCCESS);
        } else {
            // If result is null or unsuccessful, returns STORAGE_UNAVAILABLE
            return new api.StorageWriteResponse(api.StorageStatusCode.STORAGE_UNAVAILABLE);
        }
    }
}