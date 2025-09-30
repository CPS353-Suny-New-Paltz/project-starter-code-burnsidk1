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

public class FileDataStore implements DataStore {

    @Override
    public List<Integer> readIntegers(String inputLocation) {
        List<Integer> out = new ArrayList<>();
        if (inputLocation == null || inputLocation.isEmpty()) {
        return out;
        }

        File file = new File(inputLocation);
        if (!file.exists()) {
            return out;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String s = line.trim();
                if (s.isEmpty()) {
                continue;
                }

                try {
                    int n = Integer.parseInt(s);
                    if (n > 0) {
                     out.add(n);
                    }
                } catch (NumberFormatException ignore) {
                    // ignore non-integer lines
                }
            }
        } catch (IOException ignore) {
            // fail-soft for the checkpoint: return whatever we parsed (possibly empty)
        }
        return out; // never null
    }

    @Override
    public boolean writeLines(String outputLocation, List<String> lines) {
        if (outputLocation == null || outputLocation.isEmpty()) { 
        return false;
        }

        File file = new File(outputLocation);
        File parent = file.getParentFile();
        if (parent != null) {
        parent.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            if (lines != null && !lines.isEmpty()) {
                for (int i = 0; i < lines.size(); i++) {
                    String s = lines.get(i);
                    bw.write(s == null ? "" : s);
                    if (i < lines.size() - 1) {
                     bw.newLine(); // one file line per list entry
                    }
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}