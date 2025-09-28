package api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class InputBatch {
    // List of integers
    private final List<Integer> values;
    
    // Constructor that initializes the list of integers
    public InputBatch(List<Integer> values) {
        this.values = (values == null) ? Collections.emptyList() : new ArrayList<>(values);
    }
    
    // Getter for list of integers
    public List<Integer> values() { 
    	return Collections.unmodifiableList(values); 
    }
}