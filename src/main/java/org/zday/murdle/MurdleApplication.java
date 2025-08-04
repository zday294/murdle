package org.zday.murdle;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.zday.murdle.util.ResourceDirectoryLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MurdleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/main-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1800, 1200);
        scene.getStylesheets().add(getClass().getResource("style/application.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("style/mainmenu/main-menu.css").toExternalForm());
//        try (InputStream is = this.getClass().getResourceAsStream("/org/zday/murdle/data/config/level-select-resources.json")) {
//            ResourceDirectoryLoader resourceDirectoryLoader = (new ObjectMapper()).readValue(is, ResourceDirectoryLoader.class);
//            scene.getStylesheets().addAll(resourceDirectoryLoader.load());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        URL iconURL = getClass().getResource("data/images/murdle-icon.png");
        stage.getIcons().add(new Image(iconURL.openStream()));

        stage.setTitle("Murdle: digivolution");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}