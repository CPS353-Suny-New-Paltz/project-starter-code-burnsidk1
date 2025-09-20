package tests;

import api.ComputeEngineAPI;
import api.DataStorageAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.UserNetworkAPI;
import impl.UserNetworkAPIImpl;

import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

public class TestUserNetworkAPI {
	
    @Test
    void submitJob_withCustomDelimiter_smoke() {
        DataStorageAPI storage = mock(DataStorageAPI.class);
        ComputeEngineAPI compute = mock(ComputeEngineAPI.class);
        UserNetworkAPI api = new UserNetworkAPIImpl(storage, compute);

        UserJobStartRequest req = new UserJobStartRequest(";");

        UserJobStartResponse resp = api.submitJob(req);

        assertNotNull(resp, "Smoke: submitJob returns response");
    }

    @Test
    void request_defaultDelimiter_isComma() {
        UserJobStartRequest req = new UserJobStartRequest();
        assertEquals(",", req.getDelimiter());
    }
}