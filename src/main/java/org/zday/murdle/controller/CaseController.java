package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.zday.murdle.model.GameStateManager;
import org.zday.murdle.model.murdercase.suspect.Suspect;
import org.zday.murdle.model.notebook.Block;
import org.zday.murdle.view.component.StateButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CaseController implements Initializable {
    private final double BOX_SIZE = 35;

    @FXML
    private VBox notebookPane;

    @FXML
    private VBox boardPane;

    @FXML
    private GridPane boardHeaderPane;

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
        newBoardPane.getChildren().add(boardHeaderPane);

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
        boardHeaderPane = new GridPane();

        Label bufferLabel = new Label();
        bufferLabel.setMinHeight(BOX_SIZE);
        bufferLabel.setMinWidth(BOX_SIZE);

        boardHeaderPane.add(bufferLabel, 0, 0);

        List<Suspect> suspects = new ArrayList<>();

        suspects.addAll(GameStateManager.getInstance().getMurderCase().getPersonList());
        if (GameStateManager.getInstance().getMurderCase().getMotiveList() != null) suspects.addAll(GameStateManager.getInstance().getMurderCase().getMotiveList());
        suspects.addAll(GameStateManager.getInstance().getMurderCase().getLocationList());

        for(int i = 0; i < suspects.size(); i++) {
            boardHeaderPane.add(createHeaderLabel(suspects.get(i).getIcon(), suspects.get(i).getName()), i+1, 0);
        }
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
    }

    public void loadSavedBoard() {
        GameStateManager.getInstance().loadSavedBoard();
    }

    public void clearBoard() {
        GameStateManager.getInstance().clearBoard();
    }

    private Optional<HBox> drawBoardRow(List<Optional<Block>> blockOpts ) {
        if (!blockOpts.isEmpty() && blockOpts.get(0).isPresent()){
            HBox row = new HBox();
            GridPane sideHeaderPane = new GridPane();

            List<Suspect> suspects = GameStateManager.getInstance().getMurderCase().getSuspectListByType(blockOpts.get(0).get().getRowType());
            for (int i = 0; i < suspects.size(); i++) {
                sideHeaderPane.add(createHeaderLabel(suspects.get(i).getIcon(), suspects.get(i).getName()), 0, i);
            }
            row.getChildren().add(sideHeaderPane);

            blockOpts.forEach(blockOpt -> blockOpt.ifPresent(block -> row.getChildren().add(drawBlock(block))));
            return Optional.of(row);
        } else {
            return Optional.empty();
        }
    }

    private GridPane drawBlock(Block block) {
        //create grid
        GridPane gridPane = new GridPane();

        for (int i = 0; i < block.getRowsList().size(); i++) {
            for (int j = 0; j < block.getRowsList().get(i).getBoxes().size(); j++) {
                StateButton stateButton = new StateButton();
                stateButton.setBox(block.getRowsList().get(i).getBoxes().get(j));
                stateButton.setOnAction(e -> stateButton.updateState());
                stateButton.textProperty().bind(stateButton.getBox().stateIconProperty());
                stateButton.setMinWidth(BOX_SIZE);
                stateButton.setMinHeight(BOX_SIZE);
                gridPane.add(stateButton, i, j);
            }
        }

        return gridPane;
    }

    private Label createHeaderLabel(String icon, String tooltip) {
        Label headerLabel = new Label(icon);
        headerLabel.setMinHeight(BOX_SIZE);
        headerLabel.setMinWidth(BOX_SIZE);
        headerLabel.setTooltip(new Tooltip(tooltip));
        headerLabel.setAlignment(Pos.CENTER);
        return headerLabel;
    }

    private void writeClues() {
        //TODO: the clues should be written to their pane here
        //TODO: don't forget to add controls to input the guess, check it, and reveal the answer if correct
    }

}
