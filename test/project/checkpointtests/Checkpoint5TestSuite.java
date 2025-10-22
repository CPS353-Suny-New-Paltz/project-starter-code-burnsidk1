package project.checkpointtests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Checkpoint5TestSuite {

    // Verify that exceptions do not propagate back to the user as exceptions
    @Test
    public void testFileWritten() throws Exception {
        Path inputPath = Paths.get(ManualTestingFramework.INPUT);
        Files.deleteIfExists(inputPath);
        // Write a valid integer to the input file
        Files.write(inputPath, "5\n".getBytes());

        ManualTestingFramework.main(new String[] {});

        // Optionally, clean up after test
        // Files.deleteIfExists(inputPath);
    }
}
