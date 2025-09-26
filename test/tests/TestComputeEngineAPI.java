package tests;

import org.junit.jupiter.api.Test;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeEngineAPI;
import api.ComputeStartRequest;
import api.ComputeStartResponse;
import impl.ComputeEngineAPIImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestComputeEngineAPI {

 @Test
 void startCompute_nominal_returnsResponse() {
     // No mocks needed
     ComputeEngineAPI api = new ComputeEngineAPIImpl();
     ComputeStartRequest req = new ComputeStartRequest();
     
     // Starts
     ComputeStartResponse resp = api.startCompute(req);

     // Asserts
     assertNotNull(resp, "Return a response for input");
 }

 @Test
 void completeCompute_nominal_returnsResponse() {
     ComputeEngineAPI api = new ComputeEngineAPIImpl();
     ComputeCompleteRequest req = new ComputeCompleteRequest();
     
     // Starts
     ComputeCompleteResponse resp = api.completeCompute(req);

     // Asserts
     assertNotNull(resp, "ComputeComplete should return a response");
 }
}