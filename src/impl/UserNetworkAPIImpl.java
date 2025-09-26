package impl; // Created a new package for the empty implementations

import api.ComputeEngineAPI;
//Import all of the APIs made in Checkpoint 2
import api.DataStorageAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.UserNetworkAPI;

public class UserNetworkAPIImpl implements UserNetworkAPI {

    private final DataStorageAPI dataStorageApi;   // Used to read inputs and write outputs
    private final ComputeEngineAPI computeEngineApi; // Used to run the computation

    // Dependencies
    public UserNetworkAPIImpl(DataStorageAPI dataStorageApi, ComputeEngineAPI computeEngineApi) {
        this.dataStorageApi = dataStorageApi;
        this.computeEngineApi = computeEngineApi;
    }

    @Override
    public UserJobStartResponse submitJob(UserJobStartRequest request) {
        // Empty implementation for this task
        return null; // Placeholder for now
    }
}