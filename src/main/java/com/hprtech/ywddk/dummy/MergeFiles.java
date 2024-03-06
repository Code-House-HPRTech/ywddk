package com.hprtech.ywddk.dummy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

public class MergeFiles {
    public static void main(String[] args) {
        String firstFilePath = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\rangeenraatein\\namemap2.txt";
        String secondFilePath = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\rangeenraatein\\namemap.txt";
        String outputFilePath = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\rangeenraatein\\output.txt";

        mergeFiles(firstFilePath, secondFilePath, outputFilePath);
    }

    public static void mergeFiles(String firstFilePath, String secondFilePath, String outputFilePath) {
        try (BufferedReader firstReader = new BufferedReader(new FileReader(firstFilePath));
             BufferedReader secondReader = new BufferedReader(new FileReader(secondFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String firstLine;
            String secondLine;

            // Read lines from both files and merge them
            while ((firstLine = firstReader.readLine()) != null && (secondLine = secondReader.readLine()) != null) {
                String mergedLine = firstLine + "#" + secondLine;
                // Write the merged line to the output file
                writer.write(mergedLine);
                writer.newLine(); // Add a newline character after each merged line
            }

            System.out.println("Files merged successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mai321n(String[] args) {
        // Specify the path to your input file
        String inputFile = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\posts.csv";

        try {
            // Read the content of the input file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;

            // Create a BufferedWriter to write the modified content to a new file
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\antterwasnayestory\\postsu.csv"));

            while ((line = reader.readLine()) != null) {
                // Check if the line contains the target string
                if (line.contains("http://localhost/rangeenraatein/wp-content/uploads/")) {
                    // Extract the filename from the line
                    String[] parts = line.split("/");
                    String filename = parts[parts.length - 1];

                    // Generate an ID based on the filename (you can replace this logic with your own ID generation logic)
                    String dynamicId = generateId(filename);

                    // Replace the last occurrence of '.' with '_dynamicId.'
                    int lastDotIndex = line.lastIndexOf('.');
                    if (lastDotIndex != -1) {
                        String modifiedLine = line.substring(0, lastDotIndex) + "_" + dynamicId + line.substring(lastDotIndex);
                        // Write the modified line to the output file
                        writer.write(modifiedLine);
                        writer.newLine();
                    } else {
                        // If there is no '.', write the original line
                        writer.write(line);
                        writer.newLine();
                    }
                } else {
                    // If the line does not contain the target string, write the original line
                    writer.write(line);
                    writer.newLine();
                }
            }

            // Close the readers and writers
            reader.close();
            writer.close();

            System.out.println("File renaming completed successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Replace this method with your actual ID generation logic
    private static String generateId(String filename) {
        // Your logic to generate an ID based on the filename
        // For example, you can use a hash function or any other logic suitable for your use case.
        return "generated_id_for_" + filename;
    }


    public static void main1(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Specify the path to your JSON file
            String filePath = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\antterwasnayestory\\media.json";
            File file = new File(filePath);

            // Read JSON array from file and convert it to a List of objects
            List<JsonNode> myObjects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));

            // Do something with the list of objects
            for (JsonNode obj : myObjects) {

                System.out.println(obj.get("title").asText() + ","
                        + obj.get("content").asText() + ","
                        + obj.get("excerpt").asText() + ","
                        + obj.get("featured_media").asText() + ","
                        + obj.get("categories").asText() + ","
                        + obj.get("slug").asText() + ","
                        + obj.get("slug").asText() + ","
                        + obj.get("description").asText());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
