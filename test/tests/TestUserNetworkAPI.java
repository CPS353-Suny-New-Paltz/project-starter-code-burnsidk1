package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import api.ComputeEngineAPI;
import api.DataStorageAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.UserNetworkAPI;
import impl.UserNetworkAPIImpl;

public class TestUserNetworkAPI {
	
    @Test
    void submitJob_withCustomDelimiter_smoke() {
    	// Creates the mocks needed for the test
        DataStorageAPI storage = Mockito.mock(DataStorageAPI.class);
        ComputeEngineAPI compute = Mockito.mock(ComputeEngineAPI.class);
        UserNetworkAPI api = new UserNetworkAPIImpl(storage, compute);

        // Makes a request using the custom delimiter
        UserJobStartRequest request = new UserJobStartRequest(";");
       
        // Starts
        UserJobStartResponse response = api.submitJob(request);

        // Assert
        assertNotNull(response, "Smoke: submitJob returns response");
    }

    @Test
    void request_defaultDelimiter_isComma() {
        UserJobStartRequest request = new UserJobStartRequest();
        
        // Assert for the delimiter
        assertEquals(",", request.getDelimiter());
    }
}