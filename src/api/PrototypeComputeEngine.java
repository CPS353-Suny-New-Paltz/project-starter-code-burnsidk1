package api; 

import project.annotations.ConceptualAPIPrototype;

public class PrototypeComputeEngine {
	
	@ConceptualAPIPrototype 
	public void prototype(ComputeEngineAPI engine) {
		// Request wrapper
		ComputeStartRequest startRequest = new ComputeStartRequest(); 
		
		// Gets a start response when trying to begin a computation
	ComputeStartResponse startResponse = engine.startCompute(startRequest); 
	} 
}