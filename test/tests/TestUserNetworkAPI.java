package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

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
        DataStorageAPI storage = mock(DataStorageAPI.class);
        ComputeEngineAPI compute = mock(ComputeEngineAPI.class);
        UserNetworkAPI api = new UserNetworkAPIImpl(storage, compute);

        // Makes a request using the custom delimiter
        UserJobStartRequest req = new UserJobStartRequest(";");
       
        // Starts
        UserJobStartResponse resp = api.submitJob(req);

        // Assert
        assertNotNull(resp, "Smoke: submitJob returns response");
    }

    @Test
    void request_defaultDelimiter_isComma() {
        UserJobStartRequest req = new UserJobStartRequest();
        
        // Assert for the delimiter
        assertEquals(",", req.getDelimiter());
    }
}