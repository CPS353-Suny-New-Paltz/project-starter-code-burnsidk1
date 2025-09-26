package tests;

import org.junit.jupiter.api.Test;

import api.ComputeCompleteRequest;
import api.ComputeCompleteResponse;
import api.ComputeEngineAPI;
import api.ComputeStartRequest;
import api.ComputeStartResponse;
import impl.ComputeEngineAPIImpl;

// import static org.junit.jupiter.api.Assertions.*;

public class TestComputeEngineAPI {

 @Test
 void startCompute_nominal_returnsResponse() {
     ComputeEngineAPI api = new ComputeEngineAPIImpl();

     ComputeStartRequest req = new ComputeStartRequest();
     ComputeStartResponse resp = api.startCompute(req);

     assertNotNull(resp, "Return a response for input");
 }

 @Test
 void completeCompute_nominal_returnsResponse() {
     ComputeEngineAPI api = new ComputeEngineAPIImpl();

     ComputeCompleteRequest req = new ComputeCompleteRequest();
     ComputeCompleteResponse resp = api.completeCompute(req);

     assertNotNull(resp, "Smoke: completeCompute should return a response");
 }
}