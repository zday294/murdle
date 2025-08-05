package org.zday.murdle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.zday.murdle.model.game.GameStateManager;
import org.zday.murdle.util.ResourceDirectoryLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    ImageView titleImage;

    @FXML
    VBox mainMenuColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleImage.setImage(new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/murdle-title.png")));
        titleImage.fitWidthProperty().bind(mainMenuColumn.widthProperty());
        titleImage.maxWidth(mainMenuColumn.getMaxWidth());
        titleImage.minWidth(mainMenuColumn.getMinWidth());

        titleImage.setPreserveRatio(true);
    }

    @FXML
    private void quitGame() {
        Platform.exit();
    }

    @FXML
    private void playGame() {
        try(InputStream is = this.getClass().getResourceAsStream("/org/zday/murdle/data/config/level-select-resources.json")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/zday/murdle/view/level-select-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), titleImage.getScene().getWidth(), titleImage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("/org/zday/murdle/style/application.css").toExternalForm());

            ResourceDirectoryLoader resourceDirectoryLoader = (new ObjectMapper()).readValue(is, ResourceDirectoryLoader.class);
            scene.getStylesheets().addAll(resourceDirectoryLoader.load());

            Stage stage =  (Stage) titleImage.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to load level select");
            alert.showAndWait();
        }
    }
}
