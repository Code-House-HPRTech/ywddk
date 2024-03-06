package com.hprtech.ywddk.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostMapper {
    public static void main(String[] args) throws IOException {
        System.out.println(mapPost());
    }
    public static Map<Integer, Integer> mapPost() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, Integer> postMap = new HashMap<>();
        String otherpost = "C:\\Users\\hp\\Desktop\\YWDDK\\data\\media.json";
        File file = new File(otherpost);
        List<JsonNode> theirpostList = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));

        String mypost = "C:\\Users\\hp\\Desktop\\YWDDK\\ywddk_data\\media.json";
        File myfile = new File(mypost);
        List<JsonNode> mypostList = objectMapper.readValue(myfile, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));

        // Iterate through each element in the first array
        for (JsonNode element1 : theirpostList) {
            // Extract name and id from the first array
            String name1 = element1.get("slug").asText();
            int id1 = element1.get("id").asInt();

            // Iterate through each element in the second array
            for (JsonNode element2 : mypostList) {
                // Extract name and id from the second array
                String name2 = element2.get("slug").asText();
                int id2 = element2.get("id").asInt();

                // Check if names match
                if (name1.equals(name2)) {
                    // Map the ids if names match
                    postMap.put(id1, id2);
                    // If you want to break after the first match, you can add a break statement here
                    // break;
                }
            }
        }
        return postMap;
    }
}
