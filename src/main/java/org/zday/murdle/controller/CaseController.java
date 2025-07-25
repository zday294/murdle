package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.zday.murdle.model.GameStateManager;
import org.zday.murdle.model.notebook.Block;
import org.zday.murdle.view.component.StateButton;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CaseController implements Initializable {

//    private MurderCase caseInstance;

//    private Board gameBoard;
//    private Board savedBoard;
    private final double boxSize = 35.0;

    @FXML
    private VBox notebookPane;

    @FXML
    private VBox boardPane;

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
        GameStateManager.getInstance().createBoard();
        drawBoard();
        notebookPane.getChildren().add(boardPane);
        createBoardControls();
    }

    private void drawBoard() {
        VBox newBoardPane = new VBox();

        drawBoardHeader();

        drawBoardRow(
                GameStateManager.getInstance().getGameBoard().getRowBySuspect(Block.RowColumnType.WEAPON))
                .ifPresentOrElse(
                        row -> newBoardPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the first row of the board");});

        drawBoardRow(
                GameStateManager.getInstance().getGameBoard().getRowBySuspect(Block.RowColumnType.LOCATION))
                .ifPresentOrElse(
                        row -> newBoardPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the second row of the board");});

        drawBoardRow(
                GameStateManager.getInstance().getGameBoard().getRowBySuspect(Block.RowColumnType.MOTIVE))
                .ifPresent(row -> newBoardPane.getChildren().add(row));

        boardPane = newBoardPane;

    }

    private void drawBoardHeader() {
        Label bufferLabel = new Label();
        bufferLabel.setMinHeight(boxSize);
        bufferLabel.setMinWidth(boxSize);
    }

    private void createBoardControls() {
        clearBoardButton = new Button();
        clearBoardButton.setText("🗑");
        clearBoardButton.setOnAction(e -> clearBoard());
        clearBoardButton.setTooltip(new Tooltip("Clear board"));

        loadBoardButton = new Button();
        loadBoardButton.setText("♻️");
        loadBoardButton.setOnAction(e -> loadSavedBoard());
        loadBoardButton.setTooltip(new Tooltip("Load board"));

        saveBoardButton = new Button();
        saveBoardButton.setText("💾");
        saveBoardButton.setOnAction(e -> saveBoard());
        saveBoardButton.setTooltip(new Tooltip("Save board"));

        HBox hBox = new HBox(clearBoardButton, loadBoardButton, saveBoardButton);
        hBox.setPadding(new Insets(10));

        notebookPane.getChildren().add(hBox);
    }

    public void saveBoard() {
        GameStateManager.getInstance().saveBoard();
        drawBoard();
    }

    public void loadSavedBoard() {
        GameStateManager.getInstance().loadSavedBoard();
        drawBoard();
    }

    public void clearBoard() {
        GameStateManager.getInstance().createBoard();
        drawBoard();
    }

    private GridPane drawBlock(Block block) {
        //create grid
        GridPane gridPane = new GridPane();

        for (int i = 0; i < block.getRowsList().size(); i++) {
            for (int j = 0; j < block.getRowsList().get(i).size(); j++) {
                StateButton stateButton = new StateButton();
                stateButton.setBox(block.getRowsList().get(i).get(j));
                stateButton.setOnAction(e -> stateButton.updateState());
                stateButton.textProperty().bind(stateButton.stateNameProperty());
                stateButton.setMinWidth(boxSize);
                stateButton.setMinHeight(boxSize);
                gridPane.add(stateButton, i, j);
            }
        }

        return gridPane;
    }

    private Optional<HBox> drawBoardRow(List<Optional<Block>> blockOpts ) {
        HBox row = new HBox();
        boolean rowIsNecessary = false;
        for (Optional<Block> blockOpt : blockOpts) {
            if (blockOpt.isPresent()){
                rowIsNecessary = true;
                row.getChildren().add(drawBlock(blockOpt.get()));
            }
        }
        return rowIsNecessary ? Optional.of(row) : Optional.empty();
    }


}
