package impl;  // Created a new package for the empty implementations

import java.util.ArrayList;
import java.util.List;

import api.ComputeEngineAPI;
// Import all of the APIs made in Checkpoint 2
import api.DataStorageAPI;
import api.InputBatch;
import api.NetworkStatusCode;
import api.UserJobStartRequest;
import api.UserJobStartResponse;

// Abstract class for UserNetworkAPI implementations
public abstract class AbstractUserNetworkAPI {

    // No validation needed, any values are valid
    protected final DataStorageAPI dataStorageApi;   // Used to read inputs and write outputs
    protected final ComputeEngineAPI computeEngineApi; // Used to run the computation

    public AbstractUserNetworkAPI() {
        // No argument constructor for the smoke test, no validation needed
        this.dataStorageApi = null;
        this.computeEngineApi = null;
    }

    // Dependencies 
    public AbstractUserNetworkAPI(DataStorageAPI dataStorageApi, ComputeEngineAPI computeEngineApi) {
        // Storage and Compute dependencies, no validation needed, any values are valid
        this.dataStorageApi = dataStorageApi;
        this.computeEngineApi = computeEngineApi;
    }

    // Returns a response with a status code
    public UserJobStartResponse submitJob(UserJobStartRequest request) {
        try {
            // If anything is wrong with the request, returns invalid request
            if (!isValidRequest(request)) {
                return new UserJobStartResponse(NetworkStatusCode.INVALID_REQUEST);
            }

            // Validate input location
            String inputLoc = request.getInputLocation();  // Reads the input location
            if (inputLoc == null || inputLoc.trim().isEmpty()) {
            // If the input location is null or empty, returns INVALID_REQUEST
                return new UserJobStartResponse(NetworkStatusCode.INVALID_REQUEST);
            }

            InputBatch batch = dataStorageApi.readInputs(inputLoc); // Reads the batch of inputs (string)

            // Formats the output as a list of strings
            List<String> formattedPairs = processInputBatch(batch);
            if (formattedPairs == null) {
                formattedPairs = new ArrayList<>();
            }

            // Writes the formatted pairs to the output location
            String outputLoc = request.getOutputLocation();
            if (!isValidLocation(outputLoc)) {
                return new UserJobStartResponse(NetworkStatusCode.INVALID_REQUEST);
            }

            dataStorageApi.writeOutputs(outputLoc, formattedPairs);
            // If everything went well, returns success
            return new UserJobStartResponse(NetworkStatusCode.SUCCESS);
        } catch (IllegalArgumentException e) {
            // Catch bad inputs and return an invalid request
            return new UserJobStartResponse(NetworkStatusCode.INVALID_REQUEST);
        } catch (RuntimeException e) {
            // Catch runtime issues and return an network unavailable response
            return new UserJobStartResponse(NetworkStatusCode.NETWORK_UNAVAILABLE);
        }
    }

    // Validation helper for the request
    protected boolean isValidRequest(UserJobStartRequest request) {
        return (request != null && dataStorageApi != null && computeEngineApi != null);
    }

    // Location validation helper
    protected boolean isValidLocation(String location) {
        return (location != null && !(location.trim().isEmpty()));
    }

    // Formats the Collatz pair
    protected String formatCollatzPair(Integer n) {
        if (n == null || n <= 0) {
            return null; // Skip invalid inputs
        }

        // Uses the ComputeEngineAPI to get the Collatz sequence string
        String sequence = computeEngineApi.collatzSequenceString(n);
        return (n + ":" + sequence);
    }

    protected abstract List<String> processInputBatch(InputBatch batch);
}
