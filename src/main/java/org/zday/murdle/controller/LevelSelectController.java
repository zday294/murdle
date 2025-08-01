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

    @FXML
    ImageView testImageView1;

    @FXML
    ImageView testImageView2;

    @FXML
    ImageView testImageView3;

    @FXML
    ImageView testImageView4;

    @FXML
    ImageView testImageView5;

    @FXML
    ImageView testImageView6;

    public void goToPreviousPage() {

    }

    public void goToNextPage() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image testImage = new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/testImage.png"));
        Image lockedImage = new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/magnifying-glass.png"));

        testImageView1.setImage(testImage);
        testImageView2.setImage(testImage);
        testImageView3.setImage(testImage);
        testImageView4.setImage(testImage);
        testImageView5.setImage(lockedImage);
        testImageView6.setImage(lockedImage);

        //load levels

        //create level presentation

    }
}
