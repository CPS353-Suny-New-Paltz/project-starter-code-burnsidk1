package api;

import project.annotations.ConceptualAPI;
//Comment to see if this fixes Gradle issue from Checkpoint3
@ConceptualAPI 
public interface ComputeEngineAPI {
	//1) Start a new computation 
	ComputeStartResponse startCompute(ComputeStartRequest request); 
	
	// 2) Complete the computation 
	ComputeCompleteResponse completeCompute(ComputeCompleteRequest request);
}