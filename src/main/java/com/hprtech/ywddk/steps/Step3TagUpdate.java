package com.hprtech.ywddk.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.mapper.TagMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Step3TagUpdate {
    public static void main(String[] args) throws IOException {
        Map<String, String> typePathMap = Constant.typePathMap;
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonArray = objectMapper.readTree(new File(typePathMap.get("posts")));

        Map<Integer, String> tagMap = TagMapper.mapTags();
        // Iterate through each object in the array
        if (jsonArray.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonArray;
            for (JsonNode jsonNode : arrayNode) {
                JsonNode categoriesNode = jsonNode.get("tags");
                if (categoriesNode != null && categoriesNode.isArray()) {
                    ArrayNode categoriesArray = (ArrayNode) categoriesNode;
                    for (int i = 0; i < categoriesArray.size(); i++) {
                        JsonNode element = categoriesArray.get(i);
                        if (element.isInt()) {
                            int oldValue = element.asInt();
                            categoriesArray.set(i, tagMap.getOrDefault(oldValue, ""));
                        }
                    }
                }
            }
            objectMapper.writeValue(new File(typePathMap.get("posts")), jsonArray);
            System.out.println("Tags updated successfully in the file.");
        }
    }
}
