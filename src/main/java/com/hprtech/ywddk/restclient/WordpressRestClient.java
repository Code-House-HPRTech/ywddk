package com.hprtech.ywddk.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://wpdownloadfree.com/wp-json/wp/v2")
public interface WordpressRestClient {
    @GET
    @Path("/categories")
    @ClientHeaderParam(name = "User-Agent", value = "Mozilla/5.0")
    public String getCategories(@QueryParam("per_page") int perPage, @QueryParam("page") int page);

    @GET
    @Path("/media")
    @ClientHeaderParam(name = "User-Agent", value = "Mozilla/5.0")
    public String getMedia(@QueryParam("per_page") int perPage, @QueryParam("page") int page);

    @GET
    @Path("/tags")
    @ClientHeaderParam(name = "User-Agent", value = "Mozilla/5.0")
    public String getTags(@QueryParam("per_page") int perPage, @QueryParam("page") int page);

    @GET
    @Path("/posts")
    @ClientHeaderParam(name = "User-Agent", value = "Mozilla/5.0")
    public String getPosts(@QueryParam("per_page") int perPage, @QueryParam("page") int page);

    @GET
    @Path("/pages")
    @ClientHeaderParam(name = "User-Agent", value = "Mozilla/5.0")
    public String getPages(@QueryParam("per_page") int perPage, @QueryParam("page") int page);
}
