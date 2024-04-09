package com.example.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class motorcycleApiUtility {
    private final String apiKey = "ng1j+laqA7uEKGyqx238XQ==ARl54mP5bWDC88HK";

    private List<Bike> bikes; // List to store Bike objects

    public motorcycleApiUtility() {
        bikes = new ArrayList<>(); // Initialize the list
    }

    private String getStringOrNull(JsonObject jsonObject, String fieldName) {
        JsonElement element = jsonObject.get(fieldName);
        return element != null && !element.isJsonNull() ? element.getAsString() : null;
    }

    public List<String> getBikeModelsByMake(String make) {
        List<String> bikeModels = new ArrayList<>();
        String url = String.format("https://api.api-ninjas.com/v1/motorcycles?make=%s", make);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-API-Key", apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body() != null && !response.body().trim().isEmpty()) {
                JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    JsonObject motorcycle = element.getAsJsonObject();
                    String model = getStringOrNull(motorcycle, "model");
                    String year = getStringOrNull(motorcycle, "year");
                    String type = getStringOrNull(motorcycle, "type");
                    String displacement = getStringOrNull(motorcycle, "displacement");
                    String engine = getStringOrNull(motorcycle, "engine");
                    String power = getStringOrNull(motorcycle, "power");
                    String torque = getStringOrNull(motorcycle, "torque");

                    // Create a new Bike object and add it to the list
                    Bike bike = new Bike(make, model, year, type, displacement, engine, power, torque);
                    bikes.add(bike);
                    if (model != null) {
                        bikeModels.add(model);
                    }
                }
            }
        } catch (Exception e) {
            // Log the error or handle it appropriately
            System.err.println("Error fetching bike models: " + e.getMessage());
        }
        return bikeModels;
    }

    // Method to retrieve all bikes stored in the list
    public List<Bike> getAllBikes() {
        return bikes;
    }
    public Bike getBikeDetailsByModel(String model) {
        for (Bike bike : bikes) {
            if (bike.getModel().equals(model)) {
                return bike;
            }
        }
        return null; // Return null if bike with the specified model is not found
    }
}
