package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    ImageView titleImage;

    @FXML
    ImageView titleIcon;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleImage.setImage(new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/murdle-title.png")));
        titleImage.setFitWidth(1000);
        titleImage.setPreserveRatio(true);

        titleIcon.setImage(new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/murdle-icon.png")));
        titleIcon.setFitWidth(400);
        titleIcon.setPreserveRatio(true);
    }
}
