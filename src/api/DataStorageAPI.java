package api;

import project.annotations.ProcessAPI;
import java.util.List;
//Comment to see if this fixes Gradle issue from Checkpoint3
@ProcessAPI
public interface DataStorageAPI {
    StorageWriteResponse writeResults(StorageWriteRequest request);

    // Input and output locations
    InputBatch readInputs(String inputLocation);
    WriteResult writeOutputs(String outputLocation, List<String> formattedPairs);

    // Overload
    default InputBatch readInputs(InputConfig cfg) {
        return readInputs(cfg.getLocation());
    }
    default WriteResult writeOutputs(OutputConfig cfg, List<String> formattedPairs) {
        return writeOutputs(cfg.getLocation(), formattedPairs);
    }
}