package api;

import project.annotations.NetworkAPIPrototype;

public class PrototypeUserNetworkAPI {

    @NetworkAPIPrototype
    public void prototype(UserNetworkAPI api) {
    	// Wrapper
        UserJobStartRequest request = new UserJobStartRequest();
        // Call
        UserJobStartResponse response = api.submitJob(request);
    }
}