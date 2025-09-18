package api;

// Status code for storage action
public enum StorageStatusCode {
	// Able to store
    SUCCESS(true),
    // The request was not valid and failed
    INVALID_REQUEST(false),
    // There is an issue with storing due to the system itself
    STORAGE_UNAVAILABLE(false);

    private final boolean success;

    StorageStatusCode(boolean success) { 
    	// Setter for the code
    	this.success = success; 
    }
    
    public boolean isSuccess() {
    	// Getter for the code
    	return success; 
    }
}