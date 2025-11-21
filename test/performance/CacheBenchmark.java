package performance;

import impl.CachedComputeEngineAPIImpl;

public class CacheBenchmark {
    public static void main(String[] args) {
        CachedComputeEngineAPIImpl compute = new CachedComputeEngineAPIImpl();
        int start = 1;
        int end = 10000; // Number of integers to test
        int repetitions = 100; // Number of repeated integers

    // Baseline
    compute.clearSequenceCache(); // Clear cache
        long baselineStart = System.currentTimeMillis(); // Start timer
        for (int r = 0; r < repetitions; r++) { // Iterate over repetitions
            for (int n = start; n <= end; n++) { // Iterate over range
                compute.collatzSequenceString(n); // Measure baseline
            }
        }
        long baselineTimeMs = System.currentTimeMillis() - baselineStart; // Elapsed time

        // Already built cache - not cleared and has entries from baseline run
        long builtStart = System.currentTimeMillis();
        for (int r = 0; r < repetitions; r++) {
            for (int n = start; n <= end; n++) {
                compute.collatzSequenceString(n); 
            }
        }
        long builtTimeMs = System.currentTimeMillis() - builtStart;

        double improvementPercent = ((baselineTimeMs - builtTimeMs) / (double) baselineTimeMs) * 100.0;
        
        System.out.println("Baseline total: " + baselineTimeMs + " ms, Cached total: " + builtTimeMs + " ms, Improvement: " + improvementPercent + "%");
    }
}
