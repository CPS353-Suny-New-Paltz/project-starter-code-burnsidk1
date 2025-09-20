package api;

import java.util.List;

public interface DataStore {
    List<Integer> readIntegers(String inputLocation);
    boolean writeLines(String outputLocation, List<String> lines);
}