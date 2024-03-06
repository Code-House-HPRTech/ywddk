package com.hprtech.ywddk.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.dto.PostDTO;
import com.hprtech.ywddk.importer.MediaDownload;
import com.hprtech.ywddk.mapper.CategoryMapper;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Step2CategoryUpdate {
    public static void main(String[] args) throws IOException {
        Map<String, String> typePathMap = Constant.typePathMap;
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonArray = objectMapper.readTree(new File(typePathMap.get("posts")));
        Map<Integer, String> categoryMap = CategoryMapper.mapCategory();

        // Iterate through each object in the array
        if (jsonArray.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonArray;
            for (JsonNode jsonNode : arrayNode) {
                JsonNode categoriesNode = jsonNode.get("categories");
                if (categoriesNode != null && categoriesNode.isArray()) {
                    ArrayNode categoriesArray = (ArrayNode) categoriesNode;
                    for (int i = 0; i < categoriesArray.size(); i++) {
                        JsonNode element = categoriesArray.get(i);
                        if (element.isInt()) {
                            int oldValue = element.asInt();
                            if (categoryMap.containsKey(oldValue)) {
                                categoriesArray.set(i, categoryMap.get(oldValue));
                            }
                        }
                    }
                }
            }
            objectMapper.writeValue(new File(typePathMap.get("posts")), jsonArray);
            System.out.println("Categories updated successfully in the file.");
        }
    }
}
