package tests;

import api.InputConfig;
import java.util.List;

public class InMemoryInputConfig implements InputConfig {
	
    private final String location;      // defines the location of the values
    private final List<Integer> values; // test integers
    
    public InMemoryInputConfig(String location, List<Integer> values) {
    	// Location and value setters
        this.location = location; 
        this.values = values;
    }
    
    @Override public String getLocation() { 
    	// Getter for the location
    	return location; 
    }
    
    // Getter for the values
    public List<Integer> getValues() { 
    	// test only
    	return values; 
    } 
}