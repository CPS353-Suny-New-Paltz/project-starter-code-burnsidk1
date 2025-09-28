package api;

import project.annotations.ConceptualAPI;

@ConceptualAPI 
public interface ComputeEngineAPI {
	// Start a new computation 
	ComputeStartResponse startCompute(ComputeStartRequest request); 
	
	// Complete the computation 
	ComputeCompleteResponse completeCompute(ComputeCompleteRequest request);
}