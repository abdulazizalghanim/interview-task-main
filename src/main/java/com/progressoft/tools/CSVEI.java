package com.progressoft.tools;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVEI {
    public void exFile(List<String> data, Path destPath) {
        if(destPath == null || data == null)
            throw new IllegalArgumentException("Can not export with null values");
        try (FileWriter csvWriter = new FileWriter(String.valueOf(destPath),true)){
            csvWriter.append(String.join(",", data));
            csvWriter.append("\n");
        } catch (IOException e) {
            throw new IllegalStateException("Error while writing",e);
        }
    }

    public List<List<String>> imFile(Path path) {
        if(path == null)
            throw new IllegalArgumentException("Unable to import, null value");

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }catch (FileNotFoundException ee){
            throw new IllegalArgumentException("source file not found");
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading");
        }
        return records;
    }
}
