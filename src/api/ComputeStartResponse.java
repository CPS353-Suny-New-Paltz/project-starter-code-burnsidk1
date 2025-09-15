package api; 

public class ComputeStartResponse { 
	// Status for the start request
	private final ComputeStatusCode code; 
	
	public ComputeStartResponse(ComputeStatusCode code) {
		// Setter for the status
		this.code = code; } 
	
	public ComputeStatusCode getCode() {
		// Getter for the status
		return code; 
		} 
	}