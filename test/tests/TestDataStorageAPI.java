package tests;

import api.DataStore;
import api.DataStorageAPI;
import api.InputBatch;
import api.StorageWriteRequest;
import api.StorageWriteResponse;
import api.WriteResult;
import impl.DataStorageAPIImpl;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TestDataStorageAPI {
	
    @Test
    void placeholder_smoke() {
        assertTrue(true);
    }

	// List of integers based on the input example provided in the assignment
    private static final List<Integer> INPUTS = Arrays.asList(1, 10, 25);

    @Test
    void readInputs_returnsBatch_for_1_10_25() {
        // Set up mocks for DataStore
        DataStore ds = Mockito.mock(DataStore.class);
        when(ds.readIntegers(anyString())).thenReturn(INPUTS); // Accepts any location
        DataStorageAPI api = new DataStorageAPIImpl(ds);

        // Takes in the file and integers
        InputBatch batch = api.readInputs("input file name");

        // Assert
        assertNotNull(batch, "Returns an InputBatch");
    }

    @Test
    void writeOutputs_returnsResult() {
        // Set up mocks for DataStore
        DataStore ds = Mockito.mock(DataStore.class);
        when(ds.writeLines(eq("output file name"), any())).thenReturn(true);
        DataStorageAPI api = new DataStorageAPIImpl(ds);

        // Dummy values for the input test 1, 10, 25
        List<String> formatted = Arrays.asList("1", "10", "25");

        // Start
        WriteResult result = api.writeOutputs("output file name", formatted);

        // Assert 
        assertNotNull(result, "Should return a WriteResult");
    }

    @Test
    void writeResults_wrapper_returnsResponse() {
        // Set up mocks for DataStore
        DataStore ds = Mockito.mock(DataStore.class);
        DataStorageAPI api = new DataStorageAPIImpl(ds);
        StorageWriteRequest request = Mockito.mock(StorageWriteRequest.class);

        // Write response
        StorageWriteResponse response = api.writeResults(request);

        // Assert
        assertNotNull(response, "Should return a response");
    }
}