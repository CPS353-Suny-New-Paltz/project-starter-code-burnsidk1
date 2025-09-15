package api; 

public class ComputeCompleteResponse { 
	// This class is used to create the status codes
	private final ComputeStatusCode code; 
	
	public ComputeCompleteResponse(ComputeStatusCode code) { 
		// Setter for the status code
		this.code = code; 
		} 
	
	public ComputeStatusCode getCode() {
		// Getter for the status code
		return code; 
		} 
	}