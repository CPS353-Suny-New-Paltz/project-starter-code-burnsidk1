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
        this.dataStorageApi = null;
        this.computeEngineApi = null;
    }
    
    // Dependencies
    public UserNetworkAPIImpl(DataStorageAPI dataStorageApi, ComputeEngineAPI computeEngineApi) {
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

        List<String> formattedPairs = new ArrayList<>();
        if (batch != null) {
            for (Integer n : batch.values()) {
                if (n == null || n <= 0) {
                	continue;
                }

                String sequence;
                if (computeEngineApi instanceof ComputeEngineAPIImpl impl) {
                    sequence = impl.collatzSequenceString(n); 
                } else {
                    sequence = Integer.toString(n);
                }
                formattedPairs.add(n + ":" + sequence);
            }
        }
        
        String outputLoc = request.getOutputLocation();
        dataStorageApi.writeOutputs(outputLoc, formattedPairs);

            // If everything went well, returns success
            return new UserJobStartResponse(api.NetworkStatusCode.SUCCESS);
    }
}