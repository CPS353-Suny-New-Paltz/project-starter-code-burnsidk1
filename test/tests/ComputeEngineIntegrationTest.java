package tests;

import impl.DataStorageAPIImpl;
import impl.UserNetworkAPIImpl;
import impl.ComputeEngineAPIImpl;

import org.junit.jupiter.api.Test;

import api.DataStorageAPI;
import api.ComputeEngineAPI;
import api.UserNetworkAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

	  @Test
	  void smoke_components() {
	    InMemoryInputConfig inCfg = new InMemoryInputConfig("mem://inputs",
	        Arrays.asList(1, 10, 25));
	    List<String> outBuffer = new ArrayList<>();
	    InMemoryOutputConfig outCfg = new InMemoryOutputConfig("mem://outputs", outBuffer);

	    DataStorageAPI storage = new InMemoryDataStorageAPI(inCfg, outCfg);
	    ComputeEngineAPI engine = new ComputeEngineAPIImpl();
	    UserNetworkAPI userApi = new UserNetworkAPIImpl(storage, engine);

	    assertTrue(true);
	  }

	  @Test
	  void integration_1_10_25_writes_factors_defaultDelimiters() {
	    // Inputs [1,10,25], no delimiter specified => use defaults ":" and ","
	    InMemoryInputConfig inCfg = new InMemoryInputConfig("mem://inputs",
	        Arrays.asList(1, 10, 25));
	    List<String> outBuffer = new ArrayList<>();
	    InMemoryOutputConfig outCfg = new InMemoryOutputConfig("mem://outputs", outBuffer);

	    DataStorageAPI storage = new InMemoryDataStorageAPI(inCfg, outCfg);
	    ComputeEngineAPI engine = new ComputeEngineAPIImpl();
	    UserNetworkAPI userApi = new UserNetworkAPIImpl(storage, engine);

	    List<String> expected = Arrays.asList(
	        "1:1",
	        "10:1,2,5,10",
	        "25:1,5,25"
	    );

	    assertEquals(expected, outBuffer,
	        "Destination should contain default-formatted factors for 1,10,25");
	  }
	}