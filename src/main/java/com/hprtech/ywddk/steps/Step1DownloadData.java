package com.hprtech.ywddk.steps;

import com.hprtech.ywddk.contants.Constant;
import com.hprtech.ywddk.converter.Step4ConvertToCsv;
import com.hprtech.ywddk.importer.MediaDownload;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Step1DownloadData {
    public static final String mainUrl = "http://freedownload.in.net/wp-json/wp/v2/";

    public static void main(String[] args) throws IOException {
        MediaDownload.disableSSLValidation();
        Map<String, String> typePathMap = Constant.typePathMap;
        for (Map.Entry<String, String> entry : typePathMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Step4ConvertToCsv.deleteFile(value);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(value, true))) {
                int i = 1;
                System.out.println("-------" + key + "---------");
                while (true) {
                    System.out.println("------" + i);
                    String response = getResponseFromUrl(mainUrl + key + "?per_page=100&page=" + i);
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
    }

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
        System.out.println("Response Code: " + responseCode);

        // Read the response content
        if (responseCode == 400) return null;
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

}
