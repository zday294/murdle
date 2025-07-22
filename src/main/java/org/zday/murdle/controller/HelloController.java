package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.zday.murdle.model.notebook.Box;
import org.zday.murdle.view.component.StateButton;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private StateButton stateButton1;

    @FXML
    private StateButton stateButton2;

    @FXML
    private Button clickedButton;

    @FXML
    private Box.BoxState state = Box.BoxState.UNMARKED;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stateButton1.setOnAction(e -> stateButton1.updateState());
        stateButton2.setOnAction(e -> stateButton2.updateState());
        stateButton1.textProperty().bind(stateButton1.stateNameProperty());
        stateButton2.textProperty().bind(stateButton2.stateNameProperty());
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}