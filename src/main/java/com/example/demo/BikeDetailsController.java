package com.example.demo;

import com.example.demo.Bike;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class BikeDetailsController {

    @FXML
    private Button backButton;

    @FXML
    private Text textDisplay;
    private Image submitImage;

    @FXML
    private ImageView bikeImageView;
    @FXML
    protected void initialize() {
        backButton.setOnAction(this::handleBackButtonClick);
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        try {
            // Load the FXML file for the startApplication
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 651, 511);

            // Get the stage from the current action event's source
            Stage currentStage = (Stage) backButton.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(scene);
            currentStage.setTitle("MotoAPI");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set the selected bike details
    public void setSelectedBike(Bike selectedBike) {
        if (selectedBike != null) {
            // Set the text display to show the selected bike details
            textDisplay.setText( "MAKE: "+selectedBike.getMake().toUpperCase()+"\nMODEL: "+selectedBike.getModel()+"\nTYPE: "+selectedBike.getType()+"\nDESCRIPTION: "+selectedBike.getBriefDescription());
        }
    }
    public void setSubmitImage(Image submitImage) {
        this.submitImage = submitImage;
        // Set the image to the submit button
        bikeImageView.setImage(submitImage);
    }
}
