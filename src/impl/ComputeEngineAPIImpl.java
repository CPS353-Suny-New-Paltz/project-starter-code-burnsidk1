package impl;

import java.util.ArrayList;
import java.util.List;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeStatusCode;
import api.ComputeEngineAPI;
import api.ComputeStartRequest;
import api.ComputeStartResponse;

public class ComputeEngineAPIImpl implements ComputeEngineAPI {

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

     // Comma separated string for the output
    public String collatzSequenceString(long initialNum) {
    try {
        // initialNum requires validation: must be a positive integer.
        if (initialNum <= 0) {
            throw new IllegalArgumentException("Collatz input must be a positive integer.");
        }
        List<Long> seq = collatzSequence(initialNum);
        StringBuilder sb = new StringBuilder(seq.size() * 2);
        for (int i = 0; i < seq.size(); i++) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(seq.get(i));
        }
        return sb.toString();

    } catch (IllegalArgumentException e) {
            // Catch bad arguments and return an empty string
        return "Bad Argument";
    } catch (RuntimeException e) {
            // Catch runtime errors and return an empty string
            return "Runtime error";
        }
  }
}