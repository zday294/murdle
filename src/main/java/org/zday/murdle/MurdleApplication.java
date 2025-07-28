package org.zday.murdle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.zday.murdle.controller.CaseController;
import org.zday.murdle.model.GameStateManager;

import java.io.IOException;
import java.io.InputStream;

public class MurdleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameStateManager.getInstance().loadMurderCase("/org/zday/murdle/data/cases/zach-test-case.json");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/case-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 1000);
        scene.getStylesheets().addAll(
                getClass().getResource("style/application.css").toExternalForm(),
                getClass().getResource("style/gameboard/suspect-card.css").toExternalForm(),
                getClass().getResource("style/gameboard/clue-pane.css").toExternalForm(),
                getClass().getResource("style/gameboard/notebook-pane.css").toExternalForm()
        );
        stage.setTitle("Murdle: digivolution");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}