package impl;

 // Holds the input value and computed result for processing
public class ComputeItem {
    private final long inputValue;
    private String result;
    private final int originalIndex;
    
    // Constructor for ComputeItem
    public ComputeItem(long inputValue, int originalIndex) {
        this.inputValue = inputValue;
        this.originalIndex = originalIndex;
        this.result = null;
    }

    // Getter for input value
    public long getInputValue() {
        return inputValue;
    }
    
    // Getter for result
    public String getResult() {
        return result;
    }

    // Setter for result
    public void setResult(String result) {
        this.result = result;
    }
    
    // Getter for original index
    public int getOriginalIndex() {
        return originalIndex;
    }

    // Check if the item has been processed
    public boolean isProcessed() {
        return result != null;
    }
}
