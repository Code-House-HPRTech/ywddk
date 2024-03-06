package com.hprtech.ywddk.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.converter.Step4ConvertToCsv;
import com.hprtech.ywddk.dto.PostDTO;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

public class Step5BatchConvertCsv {

    public static void main(String[] args) throws IOException {
        Map<String, String> typePathMap = Constant.typePathMap;
        ObjectMapper objectMapper = new ObjectMapper();

        Step4ConvertToCsv.deleteFile(Constant.typePathMap2.get("postsCsv"));

        // Convert to csv
        File file = new File(typePathMap.get("posts"));
        List<PostDTO> postDTOList = new ArrayList<>();

        // Read JSON array from file and convert it to a List of objects
        List<JsonNode> myObjects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));

        for (JsonNode jsonNode : myObjects) {
            postDTOList.add(PostDTO.builder()
                    .id(jsonNode.get("id").asInt())
                    .title(jsonNode.get("title").get("rendered").asText())
                    .content(jsonNode.get("content").get("rendered").asText())
                    .excerpt(jsonNode.get("excerpt").get("rendered").asText())
                    .featureImage(jsonNode.get("featured_media").asInt())
                    .postDate(jsonNode.get("date").asText())
                    .slug(jsonNode.get("slug").asText())
                    .status(jsonNode.get("status").asText())
                    .category(jsonNode.get("categories").toString().replace("[", "").replace("]", "").replace("\"", ""))
                    .tag(jsonNode.get("tags").toString().replace("[", "").replace("]", "").replace("\"", ""))
                    .build());
        }

        // Define the size of each chunk
        int chunkSize = 100;

        // Calculate the number of CSV files needed
        int numFiles = (int) Math.ceil((double) postDTOList.size() / chunkSize);

        // Iterate through the chunks and write each chunk to a separate CSV file
        for (int i = 0; i < numFiles; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min(startIndex + chunkSize, postDTOList.size());
            List<PostDTO> chunk = postDTOList.subList(startIndex, endIndex);

            try {
                // Generate a random filename or use some logic to create a unique filename
                String fileName = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\postsCsv_" + (i + 1) + ".csv";
                // Create CSVWriter with FileWriter
                try (CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName))) {
                    JsonNode rootNode = objectMapper.convertValue(chunk, JsonNode.class);
                    csvWriter.writeNext(getHeader(rootNode));
                    writeData(csvWriter, rootNode);
                }
                System.out.println("CSV file " + fileName + " written successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                // Handle file writing errors
            }
        }
        System.out.println("Conversion successful. CSV file created at: " + Constant.typePathMap2.get("postsCsv"));
    }

    private static String[] getHeader(JsonNode rootNode) {
        // Assuming all objects in the JSON have the same structure
        JsonNode firstObject = rootNode.isArray() ? rootNode.get(0) : rootNode;
        Iterator<String> fieldNames = firstObject.fieldNames();

        List<String> headerList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(fieldNames, 0), false).toList();

        return headerList.toArray(new String[0]);
    }

    private static void writeData(CSVWriter csvWriter, JsonNode rootNode) {
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

    private static String[] getData(JsonNode jsonNode) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), 0), false)
                .map(JsonNode::asText)
                .toArray(String[]::new);
    }
}
