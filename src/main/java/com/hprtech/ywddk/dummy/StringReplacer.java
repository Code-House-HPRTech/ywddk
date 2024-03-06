package com.hprtech.ywddk.dummy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StringReplacer {
    public static void main(String[] args) {
        // Specify the path to your input file
        String inputFile = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\antterwasnayestory\\posts - Copy (2).csv";
        // Specify the path to your output file
        String outputFile = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\antterwasnayestory\\postsu.csv";

        // Create a map for string replacement
        Map<String, String> replacements = readReplacementsFromFile("C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\rangeenraatein\\output.txt");

        try {
            // Read the content of the input file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;

            // Create a BufferedWriter to write the modified content to a new file
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            while ((line = reader.readLine()) != null) {
                // Iterate through the map entries and replace each occurrence
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    // Using word boundaries to ensure exact match
                    line = line.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue());
                }

                // Write the modified line to the output file
                writer.write(line);
                writer.newLine();
            }

            // Close the readers and writers
            reader.close();
            writer.close();

            System.out.println("String replacement completed successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read replacements from file and create a map
    private static Map<String, String> readReplacementsFromFile(String replacementsFile) {
        Map<String, String> replacements = new HashMap<>();

        try (BufferedReader replacementsReader = new BufferedReader(new FileReader(replacementsFile))) {
            String line;
            while ((line = replacementsReader.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 2) {
                    replacements.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return replacements;
    }
}

