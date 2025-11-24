package performance;

import impl.ComputeEngineAPIImpl;
import impl.CachedComputeEngineAPIImpl;
import impl.UserNetworkAPIImpl;
import tests.InMemoryDataStorageAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.NetworkStatusCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;

public class ComputeCoordinatorIntegrationBenchmarkTest {
    @Test
    void cachedShouldBeTenPercentFaster() {
        // Input range and output locations
        int start = 1;
        int end = 2000;
        int repetitions = 20;
        java.util.List<Integer> values = new java.util.ArrayList<>();

        // Populate input values from start to end
        for (int i = start; i <= end; i++) {
            values.add(i);
        }

        // In-memory data storage with input and output configs
        tests.InMemoryInputConfig inConfig = new tests.InMemoryInputConfig("mem://inputs", values);
        java.util.List<String> outBuffer = new java.util.ArrayList<>();
        tests.InMemoryOutputConfig outConfig = new tests.InMemoryOutputConfig("mem://outputs", outBuffer);
        InMemoryDataStorageAPI storage = new InMemoryDataStorageAPI().withConfigs(inConfig, outConfig);

        // Original
        UserNetworkAPIImpl original = new UserNetworkAPIImpl(storage, new ComputeEngineAPIImpl());
        // Start timing for original
        long originalStart = System.currentTimeMillis();
        // Iterate repetitions for original
        for (int i = 0; i < repetitions; i++) {
            // Submit job to original
            UserJobStartResponse resp = original.submitJob(new UserJobStartRequest("mem://inputs", "mem://outputs", null));
            // Assert success
            Assertions.assertEquals(NetworkStatusCode.SUCCESS, resp.getCode());
        }
        // Calculate elapsed time for original
        long originalMs = System.currentTimeMillis() - originalStart;

        // Cached
        UserNetworkAPIImpl cached = new UserNetworkAPIImpl(storage, new CachedComputeEngineAPIImpl());
        // Start timing for cached
        long cachedStart = System.currentTimeMillis();
        // Iterate repetitions for cached
        for (int i = 0; i < repetitions; i++) {
            // Submit job to cached
            UserJobStartResponse resp = cached.submitJob(new UserJobStartRequest("mem://inputs", "mem://outputs", null));
            // Assert success
            Assertions.assertEquals(NetworkStatusCode.SUCCESS, resp.getCode());
        }
        // Calculate elapsed time for cached
        long cachedMs = System.currentTimeMillis() - cachedStart;

        // Calculate improvement percentage
        double improvement = ((originalMs - cachedMs) / (double) originalMs) * 100.0;
        // Print results
        System.out.println("Original: " + originalMs + " ms, Cached: " + cachedMs + " ms, Improvement: " + String.format("%.1f", improvement) + "%");
        // Assert at least 10% improvement
        Assertions.assertTrue(improvement >= 10.0, "Improvement: " + improvement + "%");
    }
}
