package tests;

import impl.DataStorageAPIImpl;
import impl.UserNetworkAPIImpl;
import impl.ComputeEngineAPIImpl;

import api.DataStorageAPI;
import api.InputBatch;
import api.ComputeEngineAPI;
import api.UserNetworkAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.NetworkStatusCode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

	  @Test
	  void integration_1_10_25_writes_factors_defaultDelimiters() {
	    // Inputs [1,10,25], no delimiter specified
	    InMemoryInputConfig inCfg = new InMemoryInputConfig("mem://inputs", Arrays.asList(1, 10, 25));
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
			"1:1,10:10|5|16|8|4|2|1,25:25|76|38|19|58|29|88|44|22|11|34|17|52|26|13|40|20|10|5|16|8|4|2|1"
		);

		// Trigger the computation and output
		userApi.submitJob(new UserJobStartRequest("mem://inputs", "mem://outputs", null));

		// Assert the output buffer matches expected results
		assertEquals(expected, outBuffer,
			"Destination should contain default-formatted Collatz sequences for 1,10,25");
	  }

	@Test
	void exceptionInComputeEngine_CaughtForNetworkUnavailable() {
		// Dependency mocks
		DataStorageAPI storage = Mockito.mock(DataStorageAPI.class);
		InputBatch batch = Mockito.mock(InputBatch.class);
		ComputeEngineAPI compute = Mockito.mock(ComputeEngineAPI.class);
		// Return a batch with a value of 7 just for compute to be called
		Mockito.when(batch.values()).thenReturn(Arrays.asList(7));
		// Return the batch when readInputs is called
		Mockito.when(storage.readInputs(Mockito.anyString())).thenReturn(batch);
		// Make collatzSequenceString throw an exception
		Mockito.when(compute.collatzSequenceString(Mockito.anyLong())).thenThrow(new RuntimeException("fail"));

		// UserNetworkAPI with mocked dependencies
		UserNetworkAPI userApi = new UserNetworkAPIImpl(storage, compute);
		// Job request
		UserJobStartRequest request = new UserJobStartRequest("input", "output", ",");
		// Submit the job
		UserJobStartResponse response = userApi.submitJob(request);

		// Assert that the response is NETWORK_UNAVAILABLE
		assertEquals(NetworkStatusCode.NETWORK_UNAVAILABLE, response.getCode(),
			"Exception in ComputeEngine should be mapped to NETWORK_UNAVAILABLE by UserNetworkAPI");
	}
}