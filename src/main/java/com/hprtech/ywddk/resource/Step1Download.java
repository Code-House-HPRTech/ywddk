package com.hprtech.ywddk.resource;

import com.hprtech.ywddk.importer.MediaDownload;
import com.hprtech.ywddk.restclient.WordpressRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Path("/grab-data")
public class Step1Download {
    @RestClient
    WordpressRestClient wordpressRestClient;

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllData() {
        MediaDownload.disableSSLValidation();
        // Specify the file path
        String media = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\media.json";
        String posts = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\posts.json";
        String pages = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\pages.json";
        String category = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\categories.json";
        String tags = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\tags.json";

        try {
            getAllMedia(media);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getAllPosts(posts);

        } catch (Exception e) {

        }
        try {
            getAllPages(pages);

        } catch (Exception e) {

        }
        try {
            getAllCategory(category);

        } catch (Exception e) {

        }
        try {
            getAllTags(tags);

        } catch (Exception e) {

        }

        return "Done";
    }

    private void getAllMedia(String path) {
        // Use try-with-resources to automatically close the resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            int i = 1;
            while (true) {
                System.out.println(">>>>>>>>>>>>> " + i);
                // Append the string to the file
                String response = wordpressRestClient.getMedia(100, i);
                if (!response.equalsIgnoreCase("[]")) {
                    writer.write(response);
                    writer.write("MMM");
                    i++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        System.out.println("Media Done");
    }

    private void getAllPosts(String path) {
        // Use try-with-resources to automatically close the resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            int i = 1;
            while (true) {
                System.out.println(">>>>>>>>>>>>> " + i);
                // Append the string to the file
                String response = wordpressRestClient.getPosts(50, i);
                if (!response.equalsIgnoreCase("[]")) {
                    writer.write(response);
                    writer.write("MMM");
                    i++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        System.out.println("Posts Done");
    }

    private void getAllPages(String path) {
        // Use try-with-resources to automatically close the resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            int i = 1;
            while (true) {
                System.out.println(">>>>>>>>>>>>> " + i);
                // Append the string to the file
                String response = wordpressRestClient.getPages(50, i);
                if (!response.equalsIgnoreCase("[]")) {
                    writer.write(response);
                    writer.write("MMM");
                    i++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        System.out.println("Pages Done");
    }

    private void getAllTags(String path) {
        // Use try-with-resources to automatically close the resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            int i = 1;
            while (true) {
                System.out.println(">>>>>>>>>>>>> " + i);
                // Append the string to the file
                String response = wordpressRestClient.getTags(50, i);
                if (!response.equalsIgnoreCase("[]")) {
                    writer.write(response);
                    writer.write("MMM");
                    i++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        System.out.println("Tags Done");
    }

    private void getAllCategory(String path) {
        // Use try-with-resources to automatically close the resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            int i = 1;
            while (true) {
                System.out.println(">>>>>>>>>>>>> " + i);
                // Append the string to the file
                String response = wordpressRestClient.getCategories(50, i);
                if (!response.equalsIgnoreCase("[]")) {
                    writer.write(response);
                    writer.write("MMM");
                    i++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        System.out.println("Category Done");
    }
}
