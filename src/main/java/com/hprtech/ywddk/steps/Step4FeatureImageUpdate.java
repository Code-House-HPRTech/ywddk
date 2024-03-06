package com.hprtech.ywddk.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.mapper.MediaMapper;
import com.hprtech.ywddk.mapper.TagMapper;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Step4FeatureImageUpdate {
    public static void main(String[] args) throws IOException {
        Map<String, String> typePathMap = Constant.typePathMap;
        Map<Integer, Integer> mediaMap = MediaMapper.mapMedia();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonArray = objectMapper.readTree(new File(typePathMap.get("posts")));

        if (jsonArray.isArray()) {
            for (JsonNode jsonNode : jsonArray) {
                JsonNode mediaNode = jsonNode.get("featured_media");
                if (mediaNode != null && mediaNode.isInt()) {
                    int oldValue = mediaNode.asInt();
                    if (mediaMap.containsKey(oldValue)) {
                        JsonNode newValue = objectMapper.valueToTree(mediaMap.get(oldValue));
                        ((ObjectNode) jsonNode).replace("featured_media", newValue);
                    }
                }
            }
            objectMapper.writeValue(new File(typePathMap.get("posts")), jsonArray);
            System.out.println("Media updated successfully in the file.");
        }
    }
}
