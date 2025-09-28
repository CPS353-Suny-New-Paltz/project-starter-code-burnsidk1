package api;

import java.util.List;

public interface DataStore {
    // Reads integers from a given input location
    List<Integer> readIntegers(String inputLocation);
    // Writes lines to a given output location
    boolean writeLines(String outputLocation, List<String> lines);
}