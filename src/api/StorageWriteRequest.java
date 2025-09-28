package api;

import java.util.List;

public class StorageWriteRequest {
    private final OutputConfig destination;
    private final List<String> formattedPairs;

    // Setter for the output location and formatted pairs
    public StorageWriteRequest(OutputConfig destination, List<String> formattedPairs) {
        this.destination = destination;
        this.formattedPairs = formattedPairs;
    }

    // Getter for the output destination
    public OutputConfig getDestination() {
        return destination;
    }

    // Getter for the formatted pairs
    public List<String> getFormattedPairs() {
        return formattedPairs;
    }
}