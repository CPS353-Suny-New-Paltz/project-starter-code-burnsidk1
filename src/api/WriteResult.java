package api;
public class WriteResult {
    // Indicates if the write was successful and includes a message
    private final boolean success;
    private final String message;
    
    // Constructor for WriteResult
    public WriteResult(boolean success, String message) {
        this.success = success; 
        this.message = message == null ? "" : message;
    }
    
    // Getter for success
    public boolean success() { 
    	return success; 
    }
    
    // Getter for message
    public String message() { 
    	return message; 
    }
}