package project.checkpointtests;

import impl.DataStorageAPIImpl;
import impl.FileDataStore;
import impl.ComputeEngineAPIImpl; 
import impl.UserNetworkAPIImpl;

import api.DataStorageAPI;
import api.DataStore;
import api.ComputeEngineAPI;  
import api.UserNetworkAPI; 

import api.UserJobStartRequest;
import api.UserJobStartResponse;

public class ManualTestingFramework {
    
    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {
        // TODO 1:
        // Instantiate a real (ie, class definition lives in the src/ folder) implementation 
        // of all 3 APIs
    	DataStore fileStore = new FileDataStore();
    	
    	DataStorageAPI dataStore = new DataStorageAPIImpl(fileStore);
    	ComputeEngineAPI engine = new ComputeEngineAPIImpl();
    	UserNetworkAPI coordinator = new UserNetworkAPIImpl(dataStore, engine);
    	
        
    	// TODO 2:
        // Run a computation with an input file of <root project dir>/manualTestInput.txt
        // and an output of <root project dir>/manualTestOutput.txt, with a delimiter of ',' 
    	UserJobStartRequest request = new UserJobStartRequest(INPUT, OUTPUT, ",");
    	
        UserJobStartResponse response = coordinator.submitJob(request);

        System.out.println("Manual run with INPUT=" + INPUT +
                           ", OUTPUT=" + OUTPUT + ", delimiter=','" +
                           " Response=" + (response == null ? "null" : response.toString()));
        // Helpful hint: the working directory of this program is <root project dir>,
        // so you can refer to the files just using the INPUT/OUTPUT constants. You do not 
        // need to (and should not) actually create those files in your repo
    }
}
