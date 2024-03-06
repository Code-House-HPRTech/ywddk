package com.hprtech.ywddk.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.converter.Step4ConvertToCsv;
import com.hprtech.ywddk.importer.MediaDownload;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class MyMediaDownload {
    public static final String mainUrl = "https://freedownload.in.net/wp-json/wp/v2/";
    public static final String myMediaUrl = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\mymedia.json";

    public static void main(String[] args) throws IOException {

        MediaDownload.disableSSLValidation();
        Step4ConvertToCsv.deleteFile(myMediaUrl);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myMediaUrl, true))) {
            int i = 1;
            while (true) {
                System.out.println("------" + i);
                String response = getResponseFromUrl(mainUrl + "media" + "?per_page=100&page=" + i);
                if (response != null && (!(response.equalsIgnoreCase("rest_post_invalid_page_number") || response.equalsIgnoreCase("[]")))) {
                    if (i==1){
                        response = response.substring(0, response.length() - 1);
                    }
                    if (i > 1) {
                        response = response.replaceFirst("\\[", ",");
                        // Remove the last "]"
                        if (response.endsWith("]")) {
                            response = response.substring(0, response.length() - 1);
                        }
                    }
                    writer.write(response);
                } else {
                    writer.write("]");
                    break;
                }
                i++;
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
    //--------------------------------Data Downloaded Successfully---------------------

    private static String getResponseFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Set the User-Agent header
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        // Set additional headers based on browser inspection
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
        connection.setRequestProperty("Referer", "https://www.google.com");
        // Add more headers if needed

        // Get the response code
        int responseCode = connection.getResponseCode();
        if (responseCode==400) return null;
        System.out.println("Response Code: " + responseCode);

        // Read the response content
        StringBuilder responseStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseStringBuilder.append(line);
            }
        } finally {
            connection.disconnect();
        }
        return responseStringBuilder.toString();
    }

    private static String[] getHeader(JsonNode rootNode) {
        // Assuming all objects in the JSON have the same structure
        JsonNode firstObject = rootNode.isArray() ? rootNode.get(0) : rootNode;
        Iterator<String> fieldNames = firstObject.fieldNames();

        List<String> headerList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(fieldNames, 0), false).toList();

        return headerList.toArray(new String[0]);
    }

    private static void writeData(CSVWriter csvWriter, JsonNode rootNode) {
        if (rootNode.isArray()) {
            for (JsonNode jsonNode : rootNode) {
                String[] data = getData(jsonNode);
                csvWriter.writeNext(data);
            }
        } else {
            String[] data = getData(rootNode);
            csvWriter.writeNext(data);
        }
    }

    private static String[] getData(JsonNode jsonNode) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), 0), false)
                .map(JsonNode::asText)
                .toArray(String[]::new);
    }
}
