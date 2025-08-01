package org.zday.murdle;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.zday.murdle.model.GameStateManager;
import org.zday.murdle.util.ResourceDirectoryLoader;

import java.io.IOException;
import java.io.InputStream;

public class MurdleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameStateManager.getInstance().loadMurderCase("/org/zday/murdle/data/cases/zach-test-case.json");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/case-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1800, 1200);
        scene.getStylesheets().add(getClass().getResource("style/application.css").toExternalForm());
        try (InputStream is = this.getClass().getResourceAsStream("/org/zday/murdle/data/config/gameboard-resources.json")) {
            ResourceDirectoryLoader resourceDirectoryLoader;
            resourceDirectoryLoader = (new ObjectMapper()).readValue(is, ResourceDirectoryLoader.class);
            scene.getStylesheets().addAll(resourceDirectoryLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.setTitle("Murdle: digivolution");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}