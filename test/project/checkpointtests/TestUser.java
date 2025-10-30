package project.checkpointtests;

import java.io.File;
import impl.MultithreadedUserNetworkAPI;
import api.UserJobStartRequest;
import api.UserJobStartResponse;


public class TestUser {
	
	// TODO 3: change the type of this variable to the name you're using for your
	// @NetworkAPI interface; also update the parameter passed to the constructor
	private final MultithreadedUserNetworkAPI coordinator;

	public TestUser(MultithreadedUserNetworkAPI coordinator) {
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";
		
		// TODO 4: Call the appropriate method(s) on the coordinator to get it to 
		// run the compute job specified by inputPath, outputPath, and delimiter
		UserJobStartRequest request = new UserJobStartRequest(inputPath, outputPath, String.valueOf(delimiter));
		UserJobStartResponse response = coordinator.submitJob(request);
	}

}
