package api;

public class UserJobStartRequest {
	// Default delimiter
	public String delimiter;
	private final String inputLocation;
    private final String outputLocation;

	public UserJobStartRequest() {
		this.delimiter = ",";
		this.inputLocation = null;
		this.outputLocation = null;
	}

	// Custom delimiter from user
	public UserJobStartRequest(String delimiter, String inputLocation) {
		this.delimiter = delimiter;
		this.inputLocation = inputLocation;
		this.outputLocation = null;
	}
	
	// Customer delimiter, outputLocation and/or inputLocation from user
    public UserJobStartRequest(String delimiter, String inputLocation, String outputLocation) {
        this.delimiter = (delimiter == null ? "," : delimiter);
        this.inputLocation = inputLocation;
        this.outputLocation = outputLocation;
    }

	// Getter for user delimiter
	public String getDelimiter() {
		return delimiter;
	}
	
	// Getter for user inputLocation
    public String getInputLocation() {
        return inputLocation;
    }
    
    // Getter for user inputLocation
    public String getOutputLocation() { 
    	return outputLocation; 
    	}
}