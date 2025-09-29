package api;

public class UserJobStartRequest {
	//default delimiter
	public String delimiter;
    private final String inputLocation;
    private final String outputLocation; 

    // Constructor for default delimiter
    public UserJobStartRequest() {
        this(",", null, null);
    }

    // Constructor for user delimiter
    public UserJobStartRequest(String delimiter) {
        this(delimiter, null, null);
    }

    // Full constructor for delimiter, input location, and output location
    public UserJobStartRequest(String inputLocation, String outputLocation, String delimiter) {
        this.delimiter = (delimiter == null ? "," : delimiter);
        this.inputLocation = inputLocation;
        this.outputLocation = outputLocation;
    }

	// Getter for user delimiter
	public String getDelimiter() {
		return delimiter;
	}
	
	// Getter for inputLocation
	public String getInputLocation() { 
		return inputLocation; 
		}
	
	// Getter for outputLocation
	public String getOutputLocation() { 
		return outputLocation; 
		}
}