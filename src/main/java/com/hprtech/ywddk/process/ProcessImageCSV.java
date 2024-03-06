package com.hprtech.ywddk.process;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.contants.Constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProcessImageCSV {
    public static void main(String[] args) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\FreeCourseSite\\text.json", true))) {
            JsonFactory jsonFactory = new JsonFactory();
            String myCategory = Constant.typePathMap.get("tags");
            File myfile = new File(myCategory);

            // Create a JsonParser from the file
            ObjectMapper objectMapper =  new ObjectMapper();
            try (JsonParser jsonParser = jsonFactory.createParser(myfile)) {
                // Move to the start of the array
                if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                    // Iterate through each object in the array
                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                        // Check if the current token is the start of an object
                        if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
                            TreeNode treeNode = objectMapper.readTree(jsonParser);
                            writer.write(treeNode.toString());
                            writer.write(",");
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
