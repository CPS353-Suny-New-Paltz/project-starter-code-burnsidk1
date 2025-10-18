package impl; // Created a new package for the empty implementations

import java.util.ArrayList;
import java.util.List;

import api.ComputeEngineAPI;
//Import all of the APIs made in Checkpoint 2
import api.DataStorageAPI;
import api.InputBatch;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.UserNetworkAPI;

public class UserNetworkAPIImpl implements UserNetworkAPI {

    // No validation needed, any values are valid
    private final DataStorageAPI dataStorageApi;   // Used to read inputs and write outputs
    private final ComputeEngineAPI computeEngineApi; // Used to run the computation

    public UserNetworkAPIImpl() {
        // No argument constructor for the smoke test, no validation needed
        this.dataStorageApi = null;
        this.computeEngineApi = null;
    }
    
    // Dependencies
    public UserNetworkAPIImpl(DataStorageAPI dataStorageApi, ComputeEngineAPI computeEngineApi) {
        // Storage and Compute dependencies, no validation needed, any values are valid
        this.dataStorageApi = dataStorageApi;
        this.computeEngineApi = computeEngineApi;
    }

    @Override
    // Returns a response with a status code
    public UserJobStartResponse submitJob(UserJobStartRequest request) {
        try {
            // If anything is wrong with the request, returns invalid request
            if (request == null || dataStorageApi == null || computeEngineApi == null) {
                return new UserJobStartResponse(api.NetworkStatusCode.INVALID_REQUEST);
            }

        // Validate input location
        String inputLoc = request.getInputLocation(); // Reads the input location
        // If the input location is null or empty, returns INVALID_REQUEST
        if (inputLoc == null || inputLoc.trim().isEmpty()) {
            return new UserJobStartResponse(api.NetworkStatusCode.INVALID_REQUEST);
        }

        InputBatch batch = dataStorageApi.readInputs(inputLoc); // Reads the batch of inputs (string)

        // Formats the output as a list of strings
        List<String> formattedPairs = new ArrayList<>();
        if (batch != null) {
            // For each integer in the batch, gets the Collatz sequence as a string
            for (Integer n : batch.values()) {
                if (n == null || n <= 0) {
                    // Skip invalid inputs
                    continue;
                }
                // Uses the ComputeEngineAPI to get the Collatz sequence string
                String sequence = computeEngineApi.collatzSequenceString(n);
                formattedPairs.add(n + ":" + sequence);
            }
        }
        
        // Writes the formatted pairs to the output location
        String outputLoc = request.getOutputLocation();
        if (outputLoc == null || outputLoc.trim().isEmpty()) {
            return new UserJobStartResponse(api.NetworkStatusCode.INVALID_REQUEST);
        }

        dataStorageApi.writeOutputs(outputLoc, formattedPairs);

            // If everything went well, returns success
            return new UserJobStartResponse(api.NetworkStatusCode.SUCCESS);
        } catch (Exception e) {
            // Catch and return an error response
            return new UserJobStartResponse(api.NetworkStatusCode.NETWORK_UNAVAILABLE);
        }
    }
}