package com.hprtech.ywddk.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hprtech.ywddk.contants.Constant;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.cert.X509Certificate;
import java.util.List;


public class MediaDownload {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Specify the path to your JSON file
        String filePath = Constant.typePathMap.get("media");
        String folderPath = Constant.mediaFolderPath;
        File file = new File(filePath);

        // Read JSON array from file and convert it to a List of objects
        List<JsonNode> myObjects = null;
        try {
            myObjects = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Do something with the list of objects
        for (JsonNode obj : myObjects) {
            try {
                disableSSLValidation();
                downloadImage(obj.get("id").asInt(), obj.get("guid").get("rendered").asText(), folderPath);
                System.out.println("Image downloaded successfully.");
            } catch (IOException e) {
                System.err.println("Error downloading image: " + e.getMessage());
            }
        }
        System.out.println(">>> Done-----------");
    }


    public static void disableSSLValidation() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadImage(int id, String imageUrl, String folderPath) throws IOException {
        URL url = new URL(imageUrl);

        // Create the folder if it doesn't exist
        Path folder = Path.of(folderPath);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        // Get the file name from the URL
//        String[] nameArr = imageUrl.substring(imageUrl.lastIndexOf('/') + 1).split("\\.");
//        String fileName = nameArr[0] + "." + nameArr[1];
        String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

        // Create the destination path
        Path destinationPath = folder.resolve(fileName);

        try (InputStream in = url.openStream()) {
            // Download and save the image
            Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
