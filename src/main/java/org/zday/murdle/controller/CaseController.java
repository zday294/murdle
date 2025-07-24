package org.zday.murdle.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.zday.murdle.model.GameStateManager;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;
import org.zday.murdle.model.notebook.Block;
import org.zday.murdle.model.notebook.Board;
import org.zday.murdle.view.component.StateButton;

import java.net.URL;
import java.util.List;
import java.util.Optional;
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
        createBoard();
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


        createBoardRow(
                gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.WEAPON, Block.RowColumnType.PERSON),
                gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.WEAPON, Block.RowColumnType.MOTIVE),
                gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.WEAPON, Block.RowColumnType.LOCATION))
                .ifPresentOrElse(
                        row -> notebookPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the first row of the board");});

        createBoardRow(
                gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.LOCATION, Block.RowColumnType.PERSON),
                gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.LOCATION, Block.RowColumnType.MOTIVE))
                .ifPresentOrElse(
                        row -> notebookPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the second row of the board");});

        createBoardRow(
                gameBoard.findBlockByRowAndColumnTypes(Block.RowColumnType.MOTIVE, Block.RowColumnType.PERSON))
                .ifPresent(row -> notebookPane.getChildren().add(row));
    }

    private void createBoardControls() {
        clearBoardButton = new Button();
        clearBoardButton.setText("🗑");
        Tooltip clearTooltip = new Tooltip("Clear board");
        clearBoardButton.setTooltip(clearTooltip);

        loadBoardButton = new Button();
        loadBoardButton.setText("♻️");
        loadBoardButton.setOnAction(e -> loadSavedBoard());
        Tooltip loadTooltip = new Tooltip("Load board");
        loadBoardButton.setTooltip(loadTooltip);

        saveBoardButton = new Button();
        saveBoardButton.setText("💾");
        saveBoardButton.setOnAction(e -> saveBoard());
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

    private GridPane createBlock(Block block) {
        //create grid
        GridPane gridPane = new GridPane();

        for (int i = 0; i < block.getRowsList().size(); i++) {
            for (int j = 0; j < block.getRowsList().get(i).size(); j++) {
                StateButton stateButton = new StateButton();
                stateButton.setBox(block.getRowsList().get(i).get(j));
                stateButton.setOnAction(e -> stateButton.updateState());
                stateButton.textProperty().bind(stateButton.stateNameProperty());
                gridPane.add(stateButton, i, j);
            }
        }

        return gridPane;
    }

    private Optional<HBox> createBoardRow(Optional<Block>... blockOpts ) {
        HBox row = new HBox();
        boolean rowIsNecessary = false;
        for (Optional<Block> blockOpt : blockOpts) {
            if (blockOpt.isPresent()){
                rowIsNecessary = true;
                row.getChildren().add(createBlock(blockOpt.get()));
            }
        }
        return rowIsNecessary ? Optional.of(row) : Optional.empty();
    }


}
