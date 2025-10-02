package tests;

import org.junit.jupiter.api.Test;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeEngineAPI;
import impl.ComputeEngineAPIImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestComputeEngineAPI {
	
    @Test
    void input_10_matches_expected_output() {
        // Tests the collatz sequence for 10
        ComputeEngineAPIImpl impl = new ComputeEngineAPIImpl();
        // Gets the sequence string
        String seq = impl.collatzSequenceString(10);
        // Asserts the expected output
        assertEquals("10|5|16|8|4|2|1", seq,
            "| as delimiter");
    }

    @Test
    void input_25_ends_in_1() {
        // Tests the collatz sequence ends in 1 for input 25
        ComputeEngineAPIImpl impl = new ComputeEngineAPIImpl();
        // Gets the sequence string
        String seq = impl.collatzSequenceString(25);
        // Asserts that the sequence ends in 1
        assertTrue(seq.endsWith("|1") || seq.equals("1"),
            "Sequence should end with 1");
    }
}