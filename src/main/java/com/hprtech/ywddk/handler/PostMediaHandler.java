package com.hprtech.ywddk.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hprtech.ywddk.mapper.MediaMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PostMediaHandler {
    public static void main(String[] args) throws IOException {
        // Path to your JSON file
        String filePath = "C:\\Users\\hp\\Desktop\\YWDDK\\inprogress\\antterwasnayestory\\posts.json";

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON file into JsonNode
        JsonNode jsonArray = objectMapper.readTree(new File(filePath));

        // Map to replace category values
        Map<Integer, Integer> categoryMap = MediaMapper.mapMedia();
        // Iterate through each object in the array
        if (jsonArray.isArray()) {
            for (JsonNode jsonNode : jsonArray) {
                // Get the "categories" value from each object
                JsonNode categoriesNode = jsonNode.get("featured_media");

                // Update value in the "categories" field
                if (categoriesNode != null && categoriesNode.isInt()) {
                    int oldValue = categoriesNode.asInt();
                    if (categoryMap.containsKey(oldValue)) {
                        // Create a new IntNode with the updated value
                        JsonNode newValue = objectMapper.valueToTree(categoryMap.get(oldValue));

                        // Replace the old node with the new one
                        ((ObjectNode) jsonNode).replace("featured_media", newValue);
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