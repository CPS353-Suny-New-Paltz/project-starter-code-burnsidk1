package api;

public class StorageWriteRequest {
    private final OutputType destination;

    public StorageWriteRequest(OutputType destination) {
    	// Setter for the output destination
        this.destination = destination;
    }

    public OutputType getDestination() {
    	// Getter for the output destination
        return destination;
    }
}