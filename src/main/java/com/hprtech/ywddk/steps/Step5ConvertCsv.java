package com.hprtech.ywddk.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.converter.Step4ConvertToCsv;
import com.hprtech.ywddk.dto.PostDTO;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.StreamSupport;

public class Step5ConvertCsv {

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

        postDTOList.subList(100,50000);

        JsonNode rootNode = objectMapper.convertValue(postDTOList, JsonNode.class);
        // Create CSVWriter with FileWriter
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(Constant.typePathMap2.get("postsCsv")))) {
            csvWriter.writeNext(getHeader(rootNode));
            writeData(csvWriter, rootNode);
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
