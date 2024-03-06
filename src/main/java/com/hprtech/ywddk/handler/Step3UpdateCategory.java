package com.hprtech.ywddk.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.mapper.CategoryMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Step3UpdateCategory {
    public static void main(String[] args) throws IOException {
        // Path to your JSON file
        String filePath = Constant.typePathMap.get("posts");

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON file into JsonNode
        JsonNode jsonArray = objectMapper.readTree(new File(filePath));

        // Map to replace category values
        Map<Integer, String> categoryMap = CategoryMapper.mapCategory();
        // Iterate through each object in the array
        if (jsonArray.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonArray;
            for (JsonNode jsonNode : arrayNode) {
                // Get the "categories" array from each object
                JsonNode categoriesNode = jsonNode.get("categories");

                // Update values in the "categories" array
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

            // Write the modified JsonNode back to the file
            objectMapper.writeValue(new File(filePath), jsonArray);

            // Output success message
            System.out.println("Categories updated successfully in the file.");
        }
    }
}