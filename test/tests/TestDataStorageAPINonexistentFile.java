package tests;

import api.DataStorageAPI;
import api.InputBatch;
import impl.DataStorageAPIImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestDataStorageAPINonexistentFile {
    @Test
    // Test to ensure readInputs returns null for a file that does not exist
    void readInputs_returnsNull_forNonexistentFile() {
        // Creates an instance of DataStorageAPIImpl
        DataStorageAPI api = new DataStorageAPIImpl();
        // Attempts to read from a file that is nonexistent
        InputBatch batch = api.readInputs("definitely_not_a_real_file.txt");
        // Asserts that the batch is null for the test to pass
        assertNull(batch, "Should return null for a nonexistent file");
    }
}