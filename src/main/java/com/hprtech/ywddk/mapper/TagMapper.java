package com.hprtech.ywddk.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.contants.Constant;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagMapper {
    public static void main(String[] args) throws IOException {
        System.out.println(mapTags());
    }

    public static Map<Integer, String> mapTags() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, String> categoryMap = new HashMap<>();
        String myCategory = Constant.typePathMap.get("tags");
        File myfile = new File(myCategory);
        JsonNode myCategoryNode = objectMapper.readTree(myfile);

        // Iterate through each element in the first array
        for (JsonNode element1 : myCategoryNode) {
            // Extract name and id from the first array
            String name1 = element1.get("name").asText();
            int id1 = element1.get("id").asInt();
            categoryMap.put(id1, name1);
        }
        return categoryMap;
    }
}
