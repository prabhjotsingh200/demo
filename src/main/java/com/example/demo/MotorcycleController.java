package com.example.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MotorcycleController {

    @FXML
    private TextField makeSearch;

    @FXML
    private ListView<String> modelList;

    @FXML
    private ImageView imageBox;

    @FXML
    private Button search;

    @FXML
    private Button reset;

    private final motorcycleApiUtility motorcycleApiUtility = new motorcycleApiUtility();
    private final imageApi imageApi = new imageApi();
    public String imageUrl;
    @FXML
    protected void initialize() {
        search.setOnAction(event -> searchBikeModels());
        reset.setOnAction(event -> {
            makeSearch.clear();
            modelList.getItems().clear();
            imageBox.setImage(null);
        });

        // Add event listener for mouse click on the list
        modelList.setOnMouseClicked(this::handleModelSelection);
    }

    private void searchBikeModels() {
        String make = makeSearch.getText();
        List<String> bikeModels = motorcycleApiUtility.getBikeModelsByMake(make);
        modelList.getItems().setAll(bikeModels);

        // Fetch and display image for the first model in the list
        if (!bikeModels.isEmpty()) {
            String selectedModel = bikeModels.get(0);
            String responseBody = imageApi.searchImageByModel(selectedModel);
            if (responseBody != null) {
                String imageUrl = getImageUrlFromResponseBody(responseBody);
                if (imageUrl != null) {
                    loadImage(imageUrl);
                }
            }
        }
    }

    public void handleModelSelection(MouseEvent event) {
        String selectedModel = modelList.getSelectionModel().getSelectedItem();
        if (selectedModel != null) {
            String responseBody = imageApi.searchImageByModel(selectedModel);
            if (responseBody != null) {
                 imageUrl = getImageUrlFromResponseBody(responseBody);
                if (imageUrl != null) {
                    loadImage(imageUrl);
                }
            }
        }
    }

    public String getImageUrlFromResponseBody(String responseBody) {
        String imageUrl = null;
        try {
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            if (json.get("status").getAsInt() == 200 && json.get("message").getAsString().equals("OK")) {
                JsonArray dataArray = json.getAsJsonArray("data");
                if (dataArray.size() > 0) {
                    JsonObject data = dataArray.get(0).getAsJsonObject();
                    imageUrl = data.get("image").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    private void loadImage(String imageUrl) {
        try {
            Image image = new Image(imageUrl);
            imageBox.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    public void handleSubmit(ActionEvent actionEvent) {
        try {
            // Load the new FXML file for the second scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BikeDetailsView.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller for the second scene
            BikeDetailsController bikeDetailsController = fxmlLoader.getController();

            // Pass the selected bike to the BikeDetailsController
            String selectedModel = modelList.getSelectionModel().getSelectedItem();
            Bike selectedBike = motorcycleApiUtility.getBikeDetailsByModel(selectedModel);
            bikeDetailsController.setSelectedBike(selectedBike);

            // Create an Image object from the imageUrl
            Image image = new Image(imageUrl);

            // Pass the Image object to the BikeDetailsController
            bikeDetailsController.setSubmitImage(image);

            // Get the stage from the action event's source
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            // Create a new scene using the new root
            Scene scene = new Scene(root);

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
