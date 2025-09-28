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
	
	  private InMemoryInputConfig inCfg;
	  private InMemoryOutputConfig outCfg;

	  // Empty constructor for test
	  public InMemoryDataStorageAPI() {
		  
	  }
	  
	  public InMemoryDataStorageAPI withConfigs(InMemoryInputConfig inCfg, InMemoryOutputConfig outCfg) {
		    this.inCfg = inCfg;
		    this.outCfg = outCfg;
		    return this;
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
		// If the output location or formatted pairs are null, returns false
			return new WriteResult(true, "Write successful");
		}
		// If not successful, returns false
		return new WriteResult(false, "Write failed");
	}

	  @Override
	public StorageWriteResponse writeResults(StorageWriteRequest request) {
		if (request != null && request.getDestination() != null && request.getFormattedPairs() != null) {
			writeOutputs(request.getDestination().getLocation(), request.getFormattedPairs());
			// If successful, returns SUCCESS
			return new StorageWriteResponse(api.StorageStatusCode.SUCCESS);
		}
		// If not successful, returns INVALID_REQUEST
		return new StorageWriteResponse(api.StorageStatusCode.INVALID_REQUEST);
	}
	  
	  @Test
	  void placeholder_smoke() {
		  
	  }
	}