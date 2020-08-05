package com.example.restaurantlist.Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Reads url with data
public class requestHttp {

    private java.net.URL URL;
    private final int timeOutLimitInMS = 5000;

    public requestHttp(String URL) {
        try {
            this.URL = new URL(URL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public StringBuffer getRequest() {
        try {
            HttpURLConnection connection = (HttpURLConnection) this.URL.openConnection();
            connection.setRequestMethod("GET");

            connection.setConnectTimeout(timeOutLimitInMS);
            connection.setReadTimeout(timeOutLimitInMS);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }

            reader.close();
            return content;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
