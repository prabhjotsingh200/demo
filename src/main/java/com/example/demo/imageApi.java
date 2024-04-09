package com.example.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class imageApi {

    private static final String API_KEY = "96479c0d4emshbbbf8b619e7bb06p186299jsn3e1fa8852529";
    private static final String HOST = "duckduckgo10.p.rapidapi.com";

    public String searchImageByModel(String model) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://duckduckgo10.p.rapidapi.com/search/images?term=" + model.replace(" ", "%20") + "&region=wt-wt&safeSearch=off"))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", HOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
