package api; 

import project.annotations.ConceptualAPIPrototype;

public class PrototypeComputeEngine {
	
	@ConceptualAPIPrototype 
	public void prototype(ComputeEngineAPI engine) {
		// Request wrapper
		ComputeStartRequest startRequest = new ComputeStartRequest(); 
		
		// Gets a start response when trying to begin a computation
	ComputeStartResponse startResponse = engine.startCompute(startRequest); 
	
	if (startResponse.getCode() == ComputeStatusCode.SUCCESS) {
		// The computation was able to start successfully 
		} else { 
			// Handle errors (invalid request, unavailable, etc.
			// that will be added later) 
			} 
	} 
}