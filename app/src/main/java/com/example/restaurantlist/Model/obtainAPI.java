package com.example.restaurantlist.Model;

import android.os.StrictMode;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class obtainAPI {
    private JsonParser parser;
    private requestHttp request;
    private String lastModified;
    private String url;
    private String format;

    public obtainAPI(String sourceUrl) {
        parser = new JsonParser();
        this.request = new requestHttp(sourceUrl);
        fetchData();
    }
    //Returns key information from surrey database
    public void fetchData() {
        //With reference to https://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String content = this.request.getRequest().toString();
        JsonObject json = (JsonObject) this.parser.parse(content);
        json = (JsonObject) json.get("result");
        JsonArray resources = (JsonArray) json.get("resources");
        json = (JsonObject) resources.get(0);

        this.lastModified = json.get("last_modified").getAsString();
        this.url = json.get("url").getAsString();
        this.format = json.get("format").getAsString();
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getUrl() {
        return url;
    }

    public String getFormat() {
        return format;
    }


}
