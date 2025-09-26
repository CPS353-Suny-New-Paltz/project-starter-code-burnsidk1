package tests;

import api.DataStorageAPI;
import api.DataStore;
import java.util.Collections;
import java.util.List;

public class InMemoryDataStore implements DataStore {
  // Input locations
  private final String inputLocation;
  // Integers to return for ReadIntegers
  private final List<Integer> inputs;
  // Output location
  private final String outputLocation;
  // Destination for writeLines to write to
  private final List<String> destination;

  // Input and output configs
  public InMemoryDataStore(InMemoryInputConfig inCfg, InMemoryOutputConfig outCfg) {
    this.inputLocation = inCfg.getLocation();
    this.inputs = inCfg.getValues();
    this.outputLocation = outCfg.getLocation();
    this.destination = outCfg.getDestination();
  }

  @Override
  // If the location matches, return inputs, otherwise return an empty list
  public List<Integer> readIntegers(String loc) {
    return (inputLocation != null && inputLocation.equals(loc)) ? inputs : Collections.emptyList();
  }

  @Override
  // If the location matches, add lines to the destination
  public boolean writeLines(String loc, List<String> lines) {
    if (outputLocation != null && outputLocation.equals(loc) && lines != null && !lines.isEmpty()) {
      destination.addAll(lines);
    }
    return true; // Always succeeds, for the sake of the test
  }

  public DataStorageAPI withConfigs(InMemoryInputConfig inCfg, InMemoryOutputConfig outCfg) {
	return null;
  }
}