package api;

import project.annotations.ConceptualAPI;

@ConceptualAPI 
public interface ComputeEngineAPI {
	//1) Start a new computation 
	ComputeStartResponse startCompute(ComputeStartRequest request); 
	
	// 2) Complete the computation 
	ComputeCompleteResponse completeCompute(ComputeCompleteRequest request);
}