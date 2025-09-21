package tests;

import api.OutputConfig;
import java.util.List;

public class InMemoryOutputConfig implements OutputConfig {
  
	private final String location;      // defines the location of the values
    private final List<String> destination;    // writable list
    
    public InMemoryOutputConfig(String location, List<String> destination) {
        this.location = location; 
        this.destination = destination;
    }
    
    @Override public String getLocation() { 
    	// Getter for the location
    	return location; 
    	}
    
    public List<String> getDestination() { 
    	return destination; 
    	} // test-only
}