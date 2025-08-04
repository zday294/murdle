package org.zday.murdle.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    ImageView titleImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleImage.setImage(new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/murdle-title.png")));
        titleImage.setFitWidth(1000);
        titleImage.setPreserveRatio(true);
    }

    @FXML
    private void quitGame() {
        Platform.exit();
    }
}
