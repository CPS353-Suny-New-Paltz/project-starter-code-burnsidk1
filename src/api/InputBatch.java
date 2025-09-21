package api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class InputBatch {
    private final List<Integer> values;
    
    public InputBatch(List<Integer> values) {
        this.values = (values == null) ? Collections.emptyList() : new ArrayList<>(values);
    }
    
    public List<Integer> values() { 
    	return Collections.unmodifiableList(values); 
    }
}