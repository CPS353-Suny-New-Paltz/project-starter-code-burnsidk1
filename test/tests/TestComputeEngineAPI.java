package tests;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeEngineAPI;
import api.ComputeStartRequest;
import api.ComputeStartResponse;
import api.ComputeStatusCode;
import impl.ComputeEngineAPIImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestComputeEngineAPI {
	
	 @Test
	 void startCompute_nominal_returnsResponse() {
	     // No mocks needed
	     ComputeEngineAPI engine = new ComputeEngineAPIImpl();
	     ComputeStartRequest request = new ComputeStartRequest();

	     // Starts
	     ComputeStartResponse response = engine.startCompute(request);
	     
	     // Asserts
	     assertNotNull(response, "Return a response for input");
	     assertEquals(ComputeStatusCode.SUCCESS, response.getCode());
	 }

	 @Test
	 void completeCompute_nominal_returnsResponse() {
	     ComputeEngineAPI engine = new ComputeEngineAPIImpl();
	     ComputeCompleteRequest request = new ComputeCompleteRequest();

	     // Starts
	     ComputeCompleteResponse response = engine.completeCompute(request);

	     // Asserts
	     assertNotNull(response, "ComputeComplete should return a response");
	     assertEquals(ComputeStatusCode.SUCCESS, response.getCode());
	 }
}