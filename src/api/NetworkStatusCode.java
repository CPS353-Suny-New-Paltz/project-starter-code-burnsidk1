package api;

public enum NetworkStatusCode {
	// The operation could be completed
    SUCCESS(true),
    // The user request was not valid
    INVALID_REQUEST(false),
    // Something is wrong with the network
    NETWORK_UNAVAILABLE(false);

    private final boolean success;

    NetworkStatusCode(boolean success) {
    	// Setter for the status code
        this.success = success;
    }

    public boolean isSuccess() {
    	// Getter for the status code
        return success;
    }
}