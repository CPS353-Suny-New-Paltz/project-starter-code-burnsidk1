package tests;

import api.DataStorageAPI;
import api.InputBatch;
import api.StorageWriteRequest;
import api.StorageWriteResponse;
import api.WriteResult;

import org.junit.jupiter.api.Test; 

import java.util.Collections;
import java.util.List;

public class InMemoryDataStorageAPI implements DataStorageAPI {
	
	  private final InMemoryInputConfig inCfg;
	  private final InMemoryOutputConfig outCfg;

	  public InMemoryDataStorageAPI(InMemoryInputConfig inCfg, InMemoryOutputConfig outCfg) {
	    this.inCfg = inCfg;
	    this.outCfg = outCfg;
	  }

	  @Override
	  public InputBatch readInputs(String inputLocation) {
	    if (inCfg != null && inCfg.getLocation().equals(inputLocation)) {
	      return new InputBatch(inCfg.getValues());
	    }
	    return new InputBatch(Collections.emptyList());
	  }

	  @Override
	  public WriteResult writeOutputs(String outputLocation, List<String> formattedPairs) {
	    if (outCfg != null && outCfg.getLocation().equals(outputLocation) && formattedPairs != null) {
	      outCfg.getDestination().addAll(formattedPairs);
	    }
	    return null;
	  }

	  @Override
	  public StorageWriteResponse writeResults(StorageWriteRequest request) {
		return null;
	  }
	}