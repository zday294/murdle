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
    ImageView imageView1;

    @FXML
    ImageView imageView2;

    @FXML
    ImageView imageView3;

    @FXML
    ImageView imageView4;

    @FXML
    ImageView imageView5;

    @FXML
    ImageView imageView6;

    public void goToPreviousPage() {

    }

    public void goToNextPage() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image testImage = new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/testImage.png"));
        Image lockedImage = new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/magnifying-glass.png"));

        imageView1.setImage(testImage);
        imageView2.setImage(testImage);
        imageView3.setImage(testImage);
        imageView4.setImage(testImage);
        imageView5.setImage(lockedImage);
        imageView6.setImage(lockedImage);

        //load levels

        //create level presentation

    }
}
