package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.zday.murdle.model.GameStateManager;
import org.zday.murdle.model.murdercase.suspect.Suspect;
import org.zday.murdle.model.murdercase.suspect.SuspectType;
import org.zday.murdle.model.notebook.Block;
import org.zday.murdle.view.component.StateButton;

import java.net.URL;
import java.util.*;

public class CaseController implements Initializable {
//    private final double BOX_SIZE = 60;

    @FXML
    private HBox suspectCardsPane;

    @FXML
    private VBox clueListPane;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox notebookPane;

    @FXML
    private VBox boardPane;

    @FXML
    private GridPane boardHeaderPane;

    @FXML
    private Label caseDescriptionLabel;

    @FXML
    private VBox solutionInputPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createNotebook();
        createClueDisplay();
    }


    @FXML
    public void showHint() {
        Alert hintAlert = new Alert(Alert.AlertType.INFORMATION);
        hintAlert.setContentText(GameStateManager.getInstance().getMurderCase().getHint());
        hintAlert.setTitle("A hint for you...");
        hintAlert.showAndWait();
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
                GameStateManager.getInstance().getGameBoard().getRowBySuspect(SuspectType.WEAPON))
                .ifPresentOrElse(
                        row -> newBoardPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the first row of the board");});

        drawBoardRow(
                GameStateManager.getInstance().getGameBoard().getRowBySuspect(SuspectType.LOCATION))
                .ifPresentOrElse(
                        row -> newBoardPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the second row of the board");});

        drawBoardRow(
                GameStateManager.getInstance().getGameBoard().getRowBySuspect(SuspectType.MOTIVE))
                .ifPresent(row -> newBoardPane.getChildren().add(row));

        boardPane = newBoardPane;

    }

    private void drawBoardHeader() {
        boardHeaderPane = new GridPane();

        Label bufferLabel = new Label();
        bufferLabel.setMinHeight(60);
        bufferLabel.setMinWidth(60);

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
        Button clearBoardButton = new Button();
        clearBoardButton.setText("🗑");
        clearBoardButton.setOnAction(e -> clearBoard());
        clearBoardButton.setTooltip(new Tooltip("Clear board"));

        Button loadBoardButton = new Button();
        loadBoardButton.setText("♻️");
        loadBoardButton.setOnAction(e -> loadSavedBoard());
        loadBoardButton.setTooltip(new Tooltip("Load board"));

        Button saveBoardButton = new Button();
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
        gridPane.getStyleClass().add("board-block");

        for (int i = 0; i < block.getRowsList().size(); i++) {
            for (int j = 0; j < block.getRowsList().get(i).getBoxes().size(); j++) {
                StateButton stateButton = new StateButton();
                stateButton.setBox(block.getRowsList().get(i).getBoxes().get(j));
                stateButton.setOnAction(e -> stateButton.updateState());
                stateButton.textProperty().bind(stateButton.getBox().stateIconProperty());
                stateButton.getStyleClass().add("state-button");
//                stateButton.setMinWidth(BOX_SIZE);
//                stateButton.setMinHeight(BOX_SIZE);
                gridPane.add(stateButton, i, j);
            }
        }

        return gridPane;
    }

    private Label createHeaderLabel(String icon, String tooltip) {
        Label headerLabel = new Label(icon);
        headerLabel.setTooltip(new Tooltip(tooltip));
        headerLabel.getStyleClass().add("suspect-header-icon");

        return headerLabel;
    }

    private void createClueDisplay() {
        titleLabel.setText(GameStateManager.getInstance().getMurderCase().getTitle());
//        titleLabel.getStyleClass().add("case-title");
        caseDescriptionLabel.setText(GameStateManager.getInstance().getMurderCase().getDescription());
        caseDescriptionLabel.getStyleClass().addAll( "clue-pane","case-description");

        createSuspectCards(GameStateManager.getInstance().getMurderCase().getSuspectListByType(SuspectType.PERSON));


        writeClues();

        //TODO: check if suspect statements are a thing in vol 1 - yes, suspect statements are a thing in the late part of the book
        drawSolutionInputs();
    }



    private void writeClues() {
        GameStateManager.getInstance().getMurderCase().getCluesList().forEach(clue -> {
            Label clueLabel = new Label(" - " + clue);
            clueLabel.setWrapText(true);
            clueLabel.setPadding(new Insets(5));
            clueListPane.getChildren().add(clueLabel);
        });
    }

    private void drawSolutionInputs() {
        List<SuspectType> orderedSuspectTypeList = List.of(SuspectType.PERSON, SuspectType.WEAPON, SuspectType.LOCATION);
        if (GameStateManager.getInstance().getMurderCase().getMotiveList() != null && !GameStateManager.getInstance().getMurderCase().getMotiveList().isEmpty()) {
            orderedSuspectTypeList.add(SuspectType.MOTIVE);
        }

        for (SuspectType suspectType : orderedSuspectTypeList) {
            ComboBox<String> selector = new ComboBox<>();
            selector.setId(suspectType.name());
            selector.setPromptText(suspectType.name());
            GameStateManager.getInstance().getMurderCase().getSuspectListByType(suspectType).forEach(suspect -> selector.getItems().add(suspect.getName()));
            solutionInputPane.getChildren().add(selector);
        }

        Button submitSolutionButton = new Button();
        submitSolutionButton.setOnAction(e -> submitSolution());
        submitSolutionButton.setText("MAKE YOUR ACCUSATION");
        submitSolutionButton.getStyleClass().add("submit-solution-button");
        solutionInputPane.getChildren().add(submitSolutionButton);
    }

    private void createSuspectCards(List<Suspect> suspectList) {
        for (Suspect suspect : suspectList) {
            VBox suspectCard = new VBox();
            suspectCard.getStyleClass().add("suspect-card");

            Label suspectIconLabel = new Label(suspect.getIcon());
            suspectIconLabel.getStyleClass().add("suspect-icon");

            Label suspectNameLabel = new Label(suspect.getName());
            suspectNameLabel.getStyleClass().add("suspect-name");

            Label suspectDescriptionLabel = new Label(suspect.getDescription());
            suspectDescriptionLabel.getStyleClass().add("suspect-label");

            Label suspectDetailsLabel = new Label(suspect.getDetails());
            suspectDetailsLabel.getStyleClass().addAll("suspect-label", "suspect-details");

            suspectCard.getChildren().addAll(suspectIconLabel, suspectNameLabel, suspectDescriptionLabel, suspectDetailsLabel);

            suspectCardsPane.getChildren().add(suspectCard);
        }
    }

    private void submitSolution() {

    }

}
