package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelSelectController implements Initializable {

    @FXML
    VBox leftPane;

    @FXML
    Button previousButton;

    @FXML
    HBox levelRow1;

    @FXML
    HBox levelRow2;

    @FXML
    VBox rightPane;

    @FXML
    Button nextButton;

    Image testImage = new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/testImage.png"));

    @FXML
    ImageView testImageView;

    @FXML
    ImageView testImageView4;

    public void goToPreviousPage() {

    }

    public void goToNextPage() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testImageView.setImage(testImage);
        testImageView4.setImage(testImage);
    }
}
