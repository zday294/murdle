package org.zday.murdle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.zday.murdle.controller.CaseController;

import java.io.IOException;
import java.io.InputStream;

public class MurdleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/case-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        CaseController caseController = fxmlLoader.getController();
        caseController.initData("zach-test-case.json");

        stage.setTitle("Murdle: digivolution");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}