package impl;

import java.util.ArrayList;
import java.util.List;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeEngineAPI;
import api.ComputeStartRequest;
import api.ComputeStartResponse;

public class ComputeEngineAPIImpl implements ComputeEngineAPI {

    @Override
    public ComputeStartResponse startCompute(ComputeStartRequest request) {
        if (request == null) {
            // If anything is wrong with the start request, returns invalid request
            return new ComputeStartResponse(api.ComputeStatusCode.INVALID_REQUEST);
        }
        // Returns success for now unless the request is null
        return new ComputeStartResponse(api.ComputeStatusCode.SUCCESS);
    }

    @Override
    public ComputeCompleteResponse completeCompute(ComputeCompleteRequest request) {
        if (request == null) {
            // If anything is wrong with the complete request, returns invalid request
            return new ComputeCompleteResponse(api.ComputeStatusCode.INVALID_REQUEST);
        }
        // Returns success for now unless the request is null
        return new ComputeCompleteResponse(api.ComputeStatusCode.SUCCESS);
    }
	
    public List<Integer> collatzSequence(int initialNum) {
            if (initialNum <= 0) {
            throw new IllegalArgumentException("Collatz input must be a positive integer.");
        }
        List<Integer> sequence = new ArrayList<>();
            long n = initialNum; // Long to avoid overflow
        sequence.add((int) n);

        while (n != 1) {
            if ((n & 1L) == 0L) {       // Even
                n = n / 2L;
            } else {                    // Odd
                n = 3L * n + 1L;
            }
            // Add to sequence
            sequence.add((int) n);
        }
        return sequence;
    }

     // Comma separated string for the output
    public String collatzSequenceString(int initialNum) {
        // Gets the sequence as a list of integers
            List<Integer> seq = collatzSequence(initialNum);
            // Builds the string
        StringBuilder sb = new StringBuilder(seq.size() * 2);
        // For loop to append each number and commas together
        for (int i = 0; i < seq.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(seq.get(i));
        }
        return sb.toString();
    }
}