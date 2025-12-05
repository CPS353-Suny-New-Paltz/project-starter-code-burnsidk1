package impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeStatusCode;
import api.ComputeEngineAPI;
import api.ComputeStartRequest;
import api.ComputeStartResponse;

// Steps:
// 1. Checked for common causes of bottlenecks such as loops, file systems, networking calls and caching 
// 2. Identified that repeated calls to collatzSequenceString with the same input would cause recomputation
// 3. Created a CachedComputeEngineAPIImpl that uses a ConcurrentHashMap to cache results of collatzSequenceString
// 4. Made a timing benchmark to compare performance of cached vs non-cached implementations
// 5. Identified that there was infact a significant bottleneck at the collatzSequenceString method due to recomputation
// 6. Benchmarking results showed a minimum of 85% improvement with caching for repeated inputs

// Sample run: Baseline total: 81 ms, Cached total: 8 ms, Improvement: 90.12345679012346%

public class CachedComputeEngineAPIImpl implements ComputeEngineAPI {

    // Maximum cache size
    private static final int maxCacheSize = 10000;

    // Cache Collatz sequence string representation
    private final Map<Long, String> sequenceStringCache = new ConcurrentHashMap<>();

    @Override
    public ComputeStartResponse startCompute(ComputeStartRequest request) {
        try { // Validation that request is not null
        if (request == null) {
            // If anything is wrong with the start request, returns invalid request
            return new ComputeStartResponse(ComputeStatusCode.INVALID_REQUEST);
        }
        // Returns success for now unless the request is null
        return new ComputeStartResponse(ComputeStatusCode.SUCCESS);
    } catch (IllegalArgumentException e) {
            // Catch and return an error response for illegal inputs
            return new ComputeStartResponse(ComputeStatusCode.INVALID_REQUEST);
        } catch (RuntimeException e) {
            // Catch runtime errors and return a failed code
            return new ComputeStartResponse(ComputeStatusCode.COMPUTE_FAILED);
        }
    }

    @Override
    public ComputeCompleteResponse completeCompute(ComputeCompleteRequest request) {
        try { // Validation that request is not null
        if (request == null) {
            // If anything is wrong with the complete request, returns invalid request
            return new ComputeCompleteResponse(ComputeStatusCode.INVALID_REQUEST);
        }
        // Returns success for now unless the request is null
        return new ComputeCompleteResponse(ComputeStatusCode.SUCCESS);

    } catch (IllegalArgumentException e) {
            // Catch and return an error response for illegal inputs
            return new ComputeCompleteResponse(ComputeStatusCode.INVALID_REQUEST);
        } catch (RuntimeException e) {
            // Catch runtime errors and return a failed code
            return new ComputeCompleteResponse(ComputeStatusCode.COMPUTE_FAILED);
        }
    }
	
    public List<Long> collatzSequence(long initialNum) {
        try {
        // InitialNum validation, must be positive
            if (initialNum <= 0) {
            throw new IllegalArgumentException("Collatz input must be a positive integer.");
        }
        List<Long> sequence = new ArrayList<>();
            long n = initialNum;
        sequence.add(n);

        while (n != 1) {
            if ((n & 1L) == 0L) {       // Even
                n = n / 2L;
            } else {                    // Odd
                n = 3L * n + 1L;
            }
            // Add to sequence
            sequence.add(n);
        }
        return sequence;
    }  catch (IllegalArgumentException e) {
            // Catch bad arguments and return an empty list
            return new ArrayList<>();
        } catch (RuntimeException e) {
            // Catch runtime errors and return an empty list
            return new ArrayList<>();
        }
    }

     // Comma separated string for the output with caching
    public String collatzSequenceString(long initialNum) {
        try {
            if (initialNum <= 0) {
                throw new IllegalArgumentException("Collatz input must be a positive integer.");
            }
            // Check cache first
            String cached = sequenceStringCache.get(initialNum);
            if (cached != null) {
                return cached;
            }
            // Compute and store
            List<Long> seq = collatzSequence(initialNum);
            StringBuilder sb = new StringBuilder(seq.size() * 2);
            for (int i = 0; i < seq.size(); i++) {
                if (i > 0) {
                    sb.append('|');
                }
                sb.append(seq.get(i));
            }
            String built = sb.toString();
            sequenceStringCache.put(initialNum, built);
            
            // Check cache size and call evict
            if (sequenceStringCache.size() > maxCacheSize) {
                evictOldestEntry();
            }
            
            return built;
        } catch (IllegalArgumentException e) {
            return "Bad Argument";
        } catch (RuntimeException e) {
            return "Runtime error";
        }
    }

    // Evict the oldest entry when cache exceeds maximum size
    private void evictOldestEntry() {
        if (!sequenceStringCache.isEmpty()) {
            // Remove the first key 
            Long oldestKey = sequenceStringCache.keySet().iterator().next();
            sequenceStringCache.remove(oldestKey);
        }
    }

    // Utility to clear cache for testing
    public void clearSequenceCache() {
        sequenceStringCache.clear();
    }
}
