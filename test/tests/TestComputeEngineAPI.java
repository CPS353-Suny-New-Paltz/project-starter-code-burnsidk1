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
     ComputeStartRequest request = new ComputeStartRequest();
     
     // Starts
     ComputeStartResponse response = api.startCompute(request);

     // Asserts
     assertNotNull(response, "Return a response for input");
 }

 @Test
 void completeCompute_nominal_returnsResponse() {
     ComputeEngineAPI api = new ComputeEngineAPIImpl();
     ComputeCompleteRequest request = new ComputeCompleteRequest();
     
     // Starts
     ComputeCompleteResponse response = api.completeCompute(request);

     // Asserts
     assertNotNull(response, "ComputeComplete should return a response");
 }
}