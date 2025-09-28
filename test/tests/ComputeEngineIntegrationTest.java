package tests;

import impl.DataStorageAPIImpl;
import impl.UserNetworkAPIImpl;
import impl.ComputeEngineAPIImpl;

import org.junit.jupiter.api.Test;

import api.DataStorageAPI;
import api.ComputeEngineAPI;
import api.UserNetworkAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

	  @Test
	  void integration_1_10_25_writes_factors_defaultDelimiters() {
	    // Inputs [1,10,25], no delimiter specified
	    InMemoryInputConfig inCfg = new InMemoryInputConfig("mem://inputs",
	        Arrays.asList(1, 10, 25));
			// Output buffer to capture results
	    List<String> outBuffer = new ArrayList<>();
		// Output configuration with the buffer
	    InMemoryOutputConfig outCfg = new InMemoryOutputConfig("mem://outputs", outBuffer);

		// DataStorageAPI with in and out configs
	    DataStorageAPI storage = new InMemoryDataStorageAPI().withConfigs(inCfg, outCfg);
	    ComputeEngineAPI engine = new ComputeEngineAPIImpl();

		// UserNetworkAPI with storage and compute engine
	    UserNetworkAPI userApi = new UserNetworkAPIImpl(storage, engine);

		// Expected formatted results
		List<String> expected = Arrays.asList(
			"1:1",
			"10:10,5,16,8,4,2,1",
			"25:25,76,38,19,58,29,88,44,22,11,34,17,52,26,13,40,20,10,5,16,8,4,2,1"
		);

		// Trigger the computation and output
		userApi.submitJob(new api.UserJobStartRequest(null, "mem://inputs", "mem://outputs"));

		// Assert the output buffer matches expected results
		assertEquals(expected, outBuffer,
			"Destination should contain default-formatted Collatz sequences for 1,10,25");
	  }
	}