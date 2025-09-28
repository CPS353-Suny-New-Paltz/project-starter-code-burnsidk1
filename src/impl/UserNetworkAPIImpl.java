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

    private final DataStorageAPI dataStorageApi;   // Used to read inputs and write outputs
    private final ComputeEngineAPI computeEngineApi; // Used to run the computation

    public UserNetworkAPIImpl() {
        // No argument constructor for the smoke test
        this.dataStorageApi = null;
        this.computeEngineApi = null;
    }
    
    // Dependencies
    public UserNetworkAPIImpl(DataStorageAPI dataStorageApi, ComputeEngineAPI computeEngineApi) {
        // Storage and Compute dependencies
        this.dataStorageApi = dataStorageApi;
        this.computeEngineApi = computeEngineApi;
    }

    @Override
    // Returns a response with a status code
    public UserJobStartResponse submitJob(UserJobStartRequest request) {
        if (request == null || dataStorageApi == null || computeEngineApi == null) {
            // If anything is wrong with the request, returns invalid request
                return new UserJobStartResponse(api.NetworkStatusCode.INVALID_REQUEST);
        }

        String inputLoc = request.getInputLocation();   // Reads the input location
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

                String sequence;
                // Use the ComputeEngineAPIImpl to get the Collatz sequence string
                if (computeEngineApi instanceof ComputeEngineAPIImpl impl) {
                    sequence = impl.collatzSequenceString(n); 
                } else {
                    sequence = Integer.toString(n);
                }
                // Adds the formatted pair to the list
                formattedPairs.add(n + ":" + sequence);
            }
        }
        
        // Writes the formatted pairs to the output location
        String outputLoc = request.getOutputLocation();
        dataStorageApi.writeOutputs(outputLoc, formattedPairs);

            // If everything went well, returns success
            return new UserJobStartResponse(api.NetworkStatusCode.SUCCESS);
    }
}