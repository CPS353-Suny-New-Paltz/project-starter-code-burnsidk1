package api;

public class UserJobStartResponse {
    // Status for the network
	private final NetworkStatusCode code;

    public UserJobStartResponse(NetworkStatusCode code) {
    	// Setter for the network status
        this.code = code;
    }

    public NetworkStatusCode getCode() {
    	// Getter for the network status
        return code;
    }
}