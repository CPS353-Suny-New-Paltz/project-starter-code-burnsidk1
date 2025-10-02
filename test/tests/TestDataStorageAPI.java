package tests;

import api.DataStore;
import api.DataStorageAPI;
import api.InputBatch;
import api.StorageWriteRequest;
import api.StorageWriteResponse;
import api.WriteResult;
import impl.DataStorageAPIImpl;
import api.StorageStatusCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TestDataStorageAPI {

	// List of integers based on the input example provided in the assignment
    private static final List<Integer> INPUTS = Arrays.asList(1, 10, 25);

    @Test
    void readInputs_returnsBatch_for_1_10_25() {
        // Set up mocks for DataStore
        DataStore ds = Mockito.mock(DataStore.class);
        // Mocks the readIntegers method
        when(ds.readIntegers(anyString())).thenReturn(Collections.emptyList());
        // Mocks the specific input location
        when(ds.readIntegers("input file name")).thenReturn(INPUTS); // Accepts any location
        
        DataStorageAPI api = new DataStorageAPIImpl(ds);

        // Takes in the file and integers
        InputBatch batch = api.readInputs("input file name");

        // Asserts that the InputBatch is not null
        assertNotNull(batch, "Returns an InputBatch");
        // Asserts that the batch values match the input list
        assertEquals(INPUTS, batch.values(), "Batch should contain 1,10,25");
        
    }

    @Test
    void readInputs_returnsExactIntegers() {
        // Set up mocks for DataStore
        DataStore ds = Mockito.mock(DataStore.class);
        when(ds.writeLines(eq("output file name"), any())).thenReturn(true);
        // Creates the DataStorageAPI with the mocked DataStore
        DataStorageAPI api = new DataStorageAPIImpl(ds);
        
        // Input list of strings
        List<String> inputs = Arrays.asList("1", "10", "25");

        // Writes the outputs to the specified file
        WriteResult result = api.writeOutputs("output file name", inputs);

        // Asserts that the result is not null
        assertNotNull(result, "Returns a WriteResult");
        //  Asserts that the write was successful
        assertEquals(true, result.success(), "Write should succeed");
    }

    @Test
    void writeResults_wrapper_returnsResponse() {
        // Set up mocks for DataStore
        DataStore ds = Mockito.mock(DataStore.class);
        // Mocks the writeLines method
        when(ds.writeLines(eq("output file name"), any())).thenReturn(true);

        // DataStorageAPI with the mocked DataStore
        DataStorageAPI storage = new DataStorageAPIImpl(ds);
        api.OutputConfig dest = Mockito.mock(api.OutputConfig.class);
        when(dest.getLocation()).thenReturn("output file name");
        
        // Storage write request mock
        StorageWriteRequest request = Mockito.mock(StorageWriteRequest.class);
        when(request.getDestination()).thenReturn(dest);
        when(request.getFormattedPairs()).thenReturn(Arrays.asList("1", "10", "25"));

        // Write response
        StorageWriteResponse response = storage.writeResults(request);

        // Asserts thet the response is not null
        assertNotNull(response, "Returns a response");
        // Asserts that the response code is SUCCESS
        assertEquals(api.StorageStatusCode.SUCCESS, response.getCode(), "Should be SUCCESS");
    }
}