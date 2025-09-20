package api;
import java.util.List;
public class InputBatch {
    private final List<Integer> values;
    
    public InputBatch(List<Integer> values) { 
    	this.values = values; 
    	}
    
    public List<Integer> values() { 
    	return values; 
    	}
}