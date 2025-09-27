package tests;

import api.DataStorageAPI;
import api.ComputeEngineAPI;
import impl.ComputeEngineAPIImpl;
import impl.DataStorageAPIImpl;


import org.junit.jupiter.api.Test;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeStartRequest;
import api.ComputeStartResponse;
import api.InputBatch;
import api.WriteResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

  // smoke test
  @Test
  void smoke_components() {
    InMemoryInputConfig inCfg = new InMemoryInputConfig("mem://inputs",
        Arrays.asList(1, 10, 25));
    List<String> outBuffer = new ArrayList<>();
    InMemoryOutputConfig outCfg = new InMemoryOutputConfig("mem://outputs", outBuffer);

    DataStorageAPI storage = new InMemoryDataStorageAPI().withConfigs(inCfg, outCfg);

    ComputeEngineAPI engine = new ComputeEngineAPIImpl();
    UserNetworkAPI userApi = new UserNetworkAPIImpl(storage, engine);

    assertTrue(true);
  }

  // Tests will fail
  @Test
  void integration_1_10_25_writes_factors_defaultDelimiters() {
    InMemoryInputConfig inCfg = new InMemoryInputConfig("mem://inputs",
        Arrays.asList(1, 10, 25));
    List<String> outBuffer = new ArrayList<>();
    InMemoryOutputConfig outCfg = new InMemoryOutputConfig("mem://outputs", outBuffer);

    DataStorageAPI storage = new InMemoryDataStorageAPI().withConfigs(inCfg, outCfg);
    ComputeEngineAPI engine = new ComputeEngineAPIImpl();
    UserNetworkAPI userApi = new UserNetworkAPIImpl(storage, engine);

    List<String> expected = Arrays.asList(
        "1:1",
        "10:1,2,5,10",
        "25:1,5,25"
    );

    // Validation
    assertEquals(expected, outBuffer,
        "Destination should contain default-formatted factors for 1,10,25");
  }
}