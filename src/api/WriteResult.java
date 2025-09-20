package api;
public class WriteResult {
    private final boolean success;
    private final String message;
    
    public WriteResult(boolean success, String message) {
        this.success = success; 
        this.message = message == null ? "" : message;
    }
    
    public boolean success() { 
    	return success; 
    }
    
    public String message() { 
    	return message; 
    }
}