package com.hprtech.ywddk.xxxxxxxxxxxxxxx;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.net.ssl.SSLContext;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Path("/")
public class MainExperimentResource {

    @RestClient
    WordPressService wordPressService;

    @GET
    @Path("hello2")
    public String das() throws Exception {

        Client client = ClientBuilder.newBuilder()
                .sslContext(SSLContext.getInstance("TLS"))
                .hostnameVerifier((s, sslSession) -> true)
                .build();


        // Replace with your WordPress site URL
        String siteUrl = "https://ywddk.infinityfreeapp.com";
        System.out.println("prgram started");
        // Fetch and store categories
        String categoriesJson = wordPressService.getCategoriesJson();
        System.out.println(categoriesJson);

        storeJson(categoriesJson, "categories.json");

//        // Fetch and store posts
//        String postsJson = wordPressService.getPostsJson(siteUrl);
//        storeJson(postsJson, "posts.json");
//
//        // Fetch and store tags
//        String tagsJson = wordPressService.getTagsJson(siteUrl);
//        storeJson(tagsJson, "tags.json");
//
//        // Fetch and store media (all records)
//        String allMediaJson = wordPressService.getAllMediaJson(siteUrl);
//        storeJson(allMediaJson, "media.json");
        return "Hello";
    }

    private void storeJson(String json, String fileName) throws Exception {
        java.nio.file.Path filePath = java.nio.file.Path.of(fileName);
        Files.write(filePath, json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}


