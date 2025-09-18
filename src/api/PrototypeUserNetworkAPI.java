package api;

import project.annotations.NetworkAPIPrototype;

public class PrototypeUserNetworkAPI {

    @NetworkAPIPrototype
    public void prototype(UserNetworkAPI api) {
    	// Wrapper
        UserJobStartRequest request = new UserJobStartRequest();
        // Call
        UserJobStartResponse response = api.submitJob(request);

        if (response.getCode() == NetworkStatusCode.SUCCESS) {
            // Job can successfully start
        } else if (response.getCode() == NetworkStatusCode.INVALID_REQUEST) {
            // User did not enter valid information
        } else if (response.getCode() == NetworkStatusCode.UNAVAILABLE) {
            // For some reason the network is not available
        } else {
            // Any other future status errors
        }
    }
}