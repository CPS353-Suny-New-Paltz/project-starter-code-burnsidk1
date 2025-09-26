package tests;

import impl.DataStorageAPIImpl;
import impl.ComputeEngineAPIImpl;

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

  @Test
  void integration_1_10_25() {
    // Inputs 1, 10, 25 using default delimiter
    InMemoryInputConfig inCfg = new InMemoryInputConfig("mem://inputs",
        Arrays.asList(1, 10, 25));

    List<String> outBuffer = new ArrayList<>();
    InMemoryOutputConfig outCfg = new InMemoryOutputConfig("mem://outputs", outBuffer);

    InMemoryDataStore store = new InMemoryDataStore(inCfg, outCfg);

    // Empty implementations under test (no mocks)
    DataStorageAPIImpl storage = new DataStorageAPIImpl(store);
    ComputeEngineAPIImpl engine = new ComputeEngineAPIImpl();

    // Hard-coded test input
    List<String> testInput = Arrays.asList(
        "1:1",
        "10:1,2,5,10",
        "25:1,5,25"
    );

    // Starts
    InputBatch batch = storage.readInputs(inCfg.getLocation());
    ComputeStartResponse started = engine.startCompute(new ComputeStartRequest());
    ComputeCompleteResponse finished = engine.completeCompute(new ComputeCompleteRequest());
    WriteResult writeResults = storage.writeOutputs(outCfg.getLocation(), testInput);

    // Asserts
    // These will be null
    assertNotNull(batch, "storage.readInputs should return an InputBatch");
    assertNotNull(started,  "engine.startCompute should return a response");
    assertNotNull(finished, "engine.completeCompute should return a response");
    assertNotNull(writeResults, "storage.writeOutputs should return a result");

    // Validate
    assertEquals(testInput, outBuffer,
        "Destination should contain a default formatted output");
  }
}