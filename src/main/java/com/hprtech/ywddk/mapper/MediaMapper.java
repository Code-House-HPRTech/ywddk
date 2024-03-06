package com.hprtech.ywddk.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.steps.MyMediaDownload;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaMapper {
    public static void main(String[] args) throws IOException {
        System.out.println(mapMedia());
    }

    public static Map<Integer, Integer> mapMedia() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, Integer> postMap = new HashMap<>();

        List<JsonNode> remoteMediaList = objectMapper.readValue(new File(Constant.typePathMap.get("media")), objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));
        List<JsonNode> myMediaList = objectMapper.readValue(new File(Constant.myMediaUrl), objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));

        for (JsonNode element1 : remoteMediaList) {
            int remoteMediaId = element1.get("id").asInt();
            String remoteMediaFullName = element1.get("guid").get("rendered").asText();
            String remoteMediaName = remoteMediaFullName.substring(remoteMediaFullName.lastIndexOf("/") + 1);

            for (JsonNode element2 : myMediaList) {
                int myMediaId = element2.get("id").asInt();
                String myMediaFullName = element2.get("guid").get("rendered").asText();
                String myMediaName = myMediaFullName.substring(myMediaFullName.lastIndexOf("/") + 1);

                if (remoteMediaName.equalsIgnoreCase(myMediaName)) {
                    postMap.put(remoteMediaId, myMediaId);
                    break;
                }
            }
        }
        System.out.println(postMap.size());
        return postMap;
    }
}
