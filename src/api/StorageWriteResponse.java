package api;

public class StorageWriteResponse {
    
    private final StorageStatusCode code;

    public StorageWriteResponse(StorageStatusCode code) {
    	// Setter for the storage status code
        this.code = code;
    }

    public StorageStatusCode getCode() {
    	// Getter for the storage status code
        return code;
    }
}