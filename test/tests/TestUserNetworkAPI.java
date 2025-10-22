package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import api.ComputeEngineAPI;
import api.DataStorageAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.UserNetworkAPI;
import impl.UserNetworkAPIImpl;
import api.NetworkStatusCode;

public class TestUserNetworkAPI {
	
    @Test
    void submitJob_withCustomDelimiter_smoke() {
    	// Creates the mocks needed for the test
        DataStorageAPI storage = Mockito.mock(DataStorageAPI.class);
        ComputeEngineAPI compute = Mockito.mock(ComputeEngineAPI.class);
        
        UserNetworkAPI network = new UserNetworkAPIImpl(storage, compute);

    // Makes a request using the custom delimiter
    UserJobStartRequest request = new UserJobStartRequest("input.txt", "output.txt", ";");
       
        // Starts
        UserJobStartResponse response = network.submitJob(request);

        // Assert
        assertNotNull(response, "Smoke: submitJob returns a response");
        assertEquals(api.NetworkStatusCode.SUCCESS, response.getCode());
    }

    @Test
    void request_defaultDelimiter_isComma() {
        UserJobStartRequest request = new UserJobStartRequest();
        
        // Assert for the delimiter
        assertEquals(",", request.getDelimiter());
    }
    
    @Test
    void submitJob_NullRequest_returnsInvalidRequest() {
        // Mocks for dependencies
        DataStorageAPI storage = Mockito.mock(DataStorageAPI.class);
        ComputeEngineAPI compute = Mockito.mock(ComputeEngineAPI.class);
        // Pass mocks into UserNetworkAPIImpl
        UserNetworkAPI network = new UserNetworkAPIImpl(storage, compute);

        // Pass null request to check validation
        UserJobStartResponse response = network.submitJob(null);
        // Asserts that the response is not null
        assertNotNull(response, "Response should not be null");
        // Asserts that the status code is INVALID_REQUEST
        assertEquals(NetworkStatusCode.INVALID_REQUEST, response.getCode(), "Should return INVALID_REQUEST for null input");
    }
}