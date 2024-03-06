package com.hprtech.ywddk.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConverterUtils {
    public static String[] getHeader(JsonNode rootNode) {
        // Assuming all objects in the JSON have the same structure
        JsonNode firstObject = rootNode.isArray() ? rootNode.get(0) : rootNode;
        Iterator<String> fieldNames = firstObject.fieldNames();

        List<String> headerList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(fieldNames, 0), false)
                .collect(Collectors.toList());

        return headerList.toArray(new String[0]);
    }

    private static String[] getData(JsonNode jsonNode) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), 0), false)
                .map(JsonNode::asText)
                .toArray(String[]::new);
    }

    public static void deleteFile(String filePath) {
        // Use try-with-resources to automatically close the resources after use
        try {
            // Create a Path object from the file path string
            Path path = Paths.get(filePath);

            // Delete the file
            Files.deleteIfExists(path);

            System.out.println("File deleted successfully");
        } catch (IOException e) {
            // Handle the exception if the file deletion fails
            e.printStackTrace();
        }
    }

    public static void writeData(CSVWriter csvWriter, JsonNode rootNode) {
        if (rootNode.isArray()) {
            for (JsonNode jsonNode : rootNode) {
                String[] data = getData(jsonNode);
                csvWriter.writeNext(data);
            }
        } else {
            String[] data = getData(rootNode);
            csvWriter.writeNext(data);
        }
    }

}
