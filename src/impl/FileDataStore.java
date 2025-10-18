package impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.DataStore;
// File based implementation of DataStore
public class FileDataStore implements DataStore {

    @Override
    // Reads integers from a file location
    public List<Integer> readIntegers(String inputLocation) {
        List<Integer> outputLines = new ArrayList<>();
        // If the input location is null or empty, returns an empty list
        if (inputLocation == null || inputLocation.isEmpty()) {
        return outputLines;
        }

        // Reads the file line by line
        File file = new File(inputLocation);
        if (!file.exists()) {
            return outputLines;
        }

        // Try to check if the file can be read
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String s = line.trim();
                if (s.isEmpty()) {
                continue;
                }

                // Tries to parse the integer
                try {
                    int n = Integer.parseInt(s);
                    if (n > 0) {
                     outputLines.add(n);
                    }
                } catch (NumberFormatException ignore) {
                    // Ignore non integer lines
                }
            }
        } catch (IOException ignore) {
            // Fail soft for the checkpoint
        }
        return outputLines;
    }

    @Override
    // Writes lines to a file location
    public boolean writeLines(String outputLocation, List<String> lines) {
        // If the output location is null or empty, returns false
        if (outputLocation == null || outputLocation.isEmpty()) { 
        return false;
        }

        // Ensures the parent directories exist
        File file = new File(outputLocation);
        File parent = file.getParentFile();
        if (parent != null) {
        parent.mkdirs();
        }

        // Writes the lines to the file, overwriting existing content
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            // Writes each line followed by a newline
            if (lines != null && !lines.isEmpty()) {
                // For loop to write each line
                for (int i = 0; i < lines.size(); i++) {
                    String s = lines.get(i);
                    bw.write(s == null ? "" : s);
                    if (i < lines.size() - 1) {
                     bw.newLine(); // One file line per list entry
                    }
                }
            }
            return true;
            // Catches IOException if the file cannot be written to
        } catch (Exception e) {
            return false;
        }
    }
}