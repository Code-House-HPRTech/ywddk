package com.hprtech.ywddk.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.dto.PostDTO;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CSVConverter {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = "C:\\Users\\Prakash\\Documents\\Personal-Work\\wordpress\\y.json";
        String csvFilePath = "C:\\Users\\Prakash\\Documents\\Personal-Work\\wordpress\\z.csv";
        File file = new File(filePath);

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
                    .build());
        }

        JsonNode rootNode = objectMapper.convertValue(postDTOList, JsonNode.class);
        // Create CSVWriter with FileWriter
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilePath))) {
            // Write header row
            csvWriter.writeNext(getHeader(rootNode));

            // Write data rows
            writeData(csvWriter, rootNode);
        }

        System.out.println("Conversion successful. CSV file created at: " + csvFilePath);
    }

    private static String[] getHeader(JsonNode rootNode) {
        // Assuming all objects in the JSON have the same structure
        JsonNode firstObject = rootNode.isArray() ? rootNode.get(0) : rootNode;
        Iterator<String> fieldNames = firstObject.fieldNames();

        List<String> headerList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(fieldNames, 0), false)
                .collect(Collectors.toList());

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