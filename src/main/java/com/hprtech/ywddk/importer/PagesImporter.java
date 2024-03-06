package com.hprtech.ywddk.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.dto.CategoryDTO;
import com.hprtech.ywddk.dto.PostDTO;
import com.hprtech.ywddk.util.ConverterUtils;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PagesImporter {
    public static void main(String[] args) throws IOException {
        Map<String, String> typePathMap = Constant.typePathMap;
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(typePathMap.get("pages"));
        List<PostDTO> postDTOList = new ArrayList<>();

        // Read JSON array from file and convert it to a List of objects
        List<JsonNode> myObjects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));

        for (JsonNode jsonNode : myObjects) {
            postDTOList.add(PostDTO.builder()
                    .title(jsonNode.get("title").get("rendered").asText())
                    .content(jsonNode.get("content").get("rendered").asText())
                    .excerpt(jsonNode.get("excerpt").get("rendered").asText())
                    .featureImage(jsonNode.get("featured_media").asInt())
                    .slug(jsonNode.get("slug").asText())
                    .status(jsonNode.get("status").asText())
                    .build());
        }

        JsonNode rootNode = objectMapper.convertValue(postDTOList, JsonNode.class);
        // Create CSVWriter with FileWriter
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(Constant.typePathMap2.get("pagesCsv")))) {
            // Write header row
            csvWriter.writeNext(ConverterUtils.getHeader(rootNode));

            // Write data rows
            ConverterUtils.writeData(csvWriter, rootNode);
        }
        System.out.println("Conversion successful. CSV file created at: " + Constant.typePathMap2.get("pagesCsv"));
    }
}
