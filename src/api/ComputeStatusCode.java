package api; 

public enum ComputeStatusCode {
	// If the computation is successful it's true
	SUCCESS(true),
	// If the request is invalid the computation won't work
	INVALID_REQUEST(false), 
	// If for some reason the computation fails, 
	// the code will be false for completion
	COMPUTE_FAILED(false); 
	
	// Private boolean because of enum
	private final boolean success; 
	
	ComputeStatusCode(boolean success) {
		// Setter for success status
		this.success = success; 
		} 
	
	public boolean isSuccess() {
		// Getter for success status
		return success; 
		} 
	}