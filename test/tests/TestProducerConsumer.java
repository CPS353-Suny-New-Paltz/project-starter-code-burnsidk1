package tests;

import impl.MultithreadedUserNetworkAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.NetworkStatusCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.ArrayList;

// Test to verify that the Producer/Consumer pattern is working
public class TestProducerConsumer {
    
    @Test
    void testBasicProducerConsumer() {
        // Creates test inputs 1 through 10
        List<Integer> inputs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            inputs.add(i);
        }

        // Creates test storage and compute engine
        InMemoryInputConfig inConfig = new InMemoryInputConfig("mem://input", inputs);
        List<String> outputBuffer = new ArrayList<>();
        InMemoryOutputConfig outConfig = new InMemoryOutputConfig("mem://output", outputBuffer);
        InMemoryDataStorageAPI storage = new InMemoryDataStorageAPI().withConfigs(inConfig, outConfig);
        impl.CachedComputeEngineAPIImpl compute = new impl.CachedComputeEngineAPIImpl();
        
        // Creates the multithreaded API using the Producer/Consumer pattern
        MultithreadedUserNetworkAPI api = new MultithreadedUserNetworkAPI(storage, compute);
        
        // Runs the job
        UserJobStartRequest request = new UserJobStartRequest("mem://input", "mem://output", null);
        UserJobStartResponse response = api.submitJob(request);
        
        // Check if we get success
        Assertions.assertEquals(NetworkStatusCode.SUCCESS, response.getCode());
        // Check that the outputs are as expected
        Assertions.assertEquals(1, outputBuffer.size()); // Should have 1 output string 
        
        // Check that the output is a list of all results
        String output = outputBuffer.get(0);
        System.out.println("Producer/Consumer output: " + output);
        
        // Check if the first output is correct: "1:1"
        Assertions.assertTrue(output.startsWith("1:"));
        
        // Check that the output for 5 is correct: "5:5|16|8|4|2|1"
        Assertions.assertTrue(output.contains("5:5|16|8|4|2|1"));
        
        // The test passed
        System.out.println("Producer/Consumer test passed");
        
        // Shutdown the API to prevent memory leaks
        api.shutdown();
    }
}
