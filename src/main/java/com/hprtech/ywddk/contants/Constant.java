package com.hprtech.ywddk.contants;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static Map<String, String> typePathMap = new HashMap<>();
    public static Map<String, String> typePathMap2 = new HashMap<>();
    public static String mediaFolderPath = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\media";
    public static String myMediaUrl = "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\mymedia.json";
    static {
        typePathMap.put("media", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\media.json");
        typePathMap.put("posts", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\posts.json");
        typePathMap.put("pages", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\pages.json");
        typePathMap.put("categories", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\categories.json");
        typePathMap.put("tags", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\tags.json");

        typePathMap2.put("mediaCsv", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\media.csv");
        typePathMap2.put("postsCsv", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\posts.csv");
        typePathMap2.put("pagesCsv", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\pages.csv");
        typePathMap2.put("categoriesCsv", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\categories.csv");
        typePathMap2.put("tagsCsv", "C:\\Users\\Prakash\\Documents\\Automation\\WP-Data\\httpsfreecourselabs.com\\tags.csv");
    }
}
