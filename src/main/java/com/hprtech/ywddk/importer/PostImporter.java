package com.hprtech.ywddk.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PostImporter {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Specify the path to your JSON file
            String filePath = "C:\\Users\\Prakash\\Documents\\Personal-Work\\wordpress\\data\\posts.json";
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
