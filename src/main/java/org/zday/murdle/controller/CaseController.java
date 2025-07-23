package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import org.zday.murdle.model.GameStateManager;
import org.zday.murdle.model.murdercase.MurderCase;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;
import org.zday.murdle.model.notebook.Block;
import org.zday.murdle.model.notebook.Board;
import org.zday.murdle.view.component.StateButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CaseController implements Initializable {

//    private MurderCase caseInstance;

    private Board gameBoard;
    private Board savedBoard;

    @FXML
    private VBox notebookPane;

    @FXML
    private VBox cluesPane;

    @FXML
    private Button clearBoardButton;

    @FXML
    private Button loadBoardButton;

    @FXML
    private Button saveBoardButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        createNotebook();
    }

    private void createNotebook() {
        //create board and add to notebook pane


        createBoardControls();
    }

    private void createBoard() {
        gameBoard = new Board(GameStateManager.getInstance().getMurderCase().getPersonList(), GameStateManager.getInstance().getMurderCase().getWeaponList(), GameStateManager.getInstance().getMurderCase().getLocationList());

        TableView<List<StateButton>> tableView = new TableView<>();
        tableView.getColumns().add(new TableColumn<>("")); // column for row labels
        for(Person person : GameStateManager.getInstance().getMurderCase().getPersonList()) {
            tableView.getColumns().add(new TableColumn<>(person.getIcon()));
        }

        for (Location location : GameStateManager.getInstance().getMurderCase().getLocationList()){
            tableView.getColumns().add(new TableColumn<>(location.getIcon()));
        }

        Block weaponPersonBlock = gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.WEAPON, Block.RowColumnType.PERSON);
        Block weaponLocationBlock = gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.WEAPON, Block.RowColumnType.LOCATION);
        for (Weapon weapon : GameStateManager.getInstance().getMurderCase().getWeaponList()) {


            //bind the cells in that row to the boxes in
        }
    }


    private void createBoardControls(){
        clearBoardButton = new Button();
        clearBoardButton.setText("🗑️");
        Tooltip clearTooltip = new Tooltip("Clear board");
        clearBoardButton.setTooltip(clearTooltip);

        loadBoardButton = new Button();
        loadBoardButton.setText("♻️");
        Tooltip loadTooltip = new Tooltip("Load board");
        loadBoardButton.setTooltip(loadTooltip);

        saveBoardButton = new Button();
        saveBoardButton.setText("💾");
        Tooltip saveTooltip = new Tooltip("Save board");
        saveBoardButton.setTooltip(saveTooltip);

        notebookPane.getChildren().addAll(clearBoardButton, loadBoardButton, saveBoardButton);
    }

    public void saveBoard() {
        savedBoard = gameBoard.clone();
    }

    public void loadSavedBoard() {
        gameBoard = savedBoard.clone();
    }


}
