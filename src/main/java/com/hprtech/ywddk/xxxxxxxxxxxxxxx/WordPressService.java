package com.hprtech.ywddk.xxxxxxxxxxxxxxx;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://ywddk.infinityfreeapp.com/wp-json")
public interface WordPressService {

    @GET
    @Path("/wp/v2/categories")
    String getCategoriesJson();

    @GET
    @Path("/wp/v2/posts")
    String getPostsJson();

    @GET
    @Path("/wp/v2/tags")
    String getTagsJson();

    @GET
    @Path("/wp/v2/media")
    String getAllMediaJson();
}
