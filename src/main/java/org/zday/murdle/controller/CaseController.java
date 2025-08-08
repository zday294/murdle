package org.zday.murdle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;
import org.zday.murdle.model.game.GameStateManager;
import org.zday.murdle.model.game.murdercase.suspect.Person;
import org.zday.murdle.model.game.murdercase.suspect.Suspect;
import org.zday.murdle.model.game.murdercase.suspect.SuspectType;
import org.zday.murdle.model.game.notebook.Block;
import org.zday.murdle.model.game.notebook.Box;
import org.zday.murdle.util.ResourceDirectoryLoader;
import org.zday.murdle.view.component.StateButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class CaseController implements Initializable {
    @FXML
    private VBox cluePane;

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

    @FXML
    private HBox suspectTypeSelectionPane;

    @FXML
    private VBox suspectStatementsPane;

    @FXML
    private Label resolutionLabel;

    @FXML
    private ScrollPane clueScrollPane;

    @FXML
    private Label clueListPaneTitleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createNotebook();
        createClueDisplay();
    }

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
                GameStateManager.getInstance().getGameBoard().getRowBySuspectType(SuspectType.WEAPON))
                .ifPresentOrElse(
                        row -> newBoardPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the first row of the board");});

        drawBoardRow(
                GameStateManager.getInstance().getGameBoard().getRowBySuspectType(SuspectType.LOCATION))
                .ifPresentOrElse(
                        row -> newBoardPane.getChildren().add(row),
                        () -> {throw new IllegalArgumentException("An error has occurred while creating the second row of the board");});

        drawBoardRow(
                GameStateManager.getInstance().getGameBoard().getRowBySuspectType(SuspectType.MOTIVE))
                .ifPresent(row -> newBoardPane.getChildren().add(row));

        boardPane = newBoardPane;
    }

    private void drawBoardHeader() {
        boardHeaderPane = new GridPane();

        Label bufferLabel = new Label();
        bufferLabel.getStyleClass().add("suspect-header-icon");

        boardHeaderPane.add(bufferLabel, 0, 0);

        List<Suspect> suspects = new ArrayList<>(GameStateManager.getInstance().getMurderCase().getPersonList());
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

    private Optional<HBox> drawBoardRow(List<Optional<Block>> blockOpts) {
        if (!blockOpts.isEmpty() && blockOpts.get(0).isPresent()){
            HBox row = new HBox();
            GridPane sideHeaderPane = new GridPane();

            List<Suspect> suspects = GameStateManager.getInstance().getMurderCase().getSuspectListByType(blockOpts.get(0).get().getRowType());
            for (int i = 0; i < suspects.size(); i++) {
                sideHeaderPane.add(createHeaderLabel(suspects.get(i).getIcon(), suspects.get(i).getName()), 0, i);
            }
            row.getChildren().add(sideHeaderPane);

            blockOpts.forEach(blockOpt -> blockOpt.ifPresent(block -> row.getChildren().add(drawBlock(block))));
            int bufferBlockCount = blockOpts.stream().filter(Optional::isEmpty).toList().size() - (GameStateManager.getInstance().getMurderCase().getMotiveList() == null ? 1 : 0);
            for (int i = 0; i < bufferBlockCount; i++) {
                row.getChildren().add(drawBufferBlock(blockOpts.get(0).get().getRowsList().size()));
            }
            return Optional.of(row);
        } else {
            return Optional.empty();
        }
    }

    private GridPane drawBufferBlock(int size) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Label label = new Label();
                label.getStyleClass().add("suspect-header-icon");
                label.prefHeightProperty().bind(label.widthProperty());
                gridPane.add(label, i, j);
            }
        }
        return gridPane;
    }

    private GridPane drawBlock(Block block) {
        GridPane gridPane = new GridPane();
//        gridPane.getStyleClass().add("board-block");

        for (int i = 0; i < block.getRowsList().size(); i++) {
            for (int j = 0; j < block.getRowsList().get(i).getBoxes().size(); j++) {
                StateButton stateButton = new StateButton();
                stateButton.setBox(block.getRowsList().get(i).getBoxes().get(j));
                stateButton.setOnAction(e -> stateButton.updateState());
                stateButton.textProperty().bind(stateButton.getBox().stateIconProperty());
//                stateButton.maxHeightProperty().bind(stateButton.widthProperty());
//                stateButton.prefHeightProperty().bind(stateButton.prefWidthProperty());
//                stateButton.minHeightProperty().bind(stateButton.widthProperty());
//                stateButton.getBox().setState(Box.BoxState.UNMARKED);
//                System.out.println("StateButton - width: " + stateButton.widthProperty().getValue() + ", height: " + stateButton.heightProperty().getValue() + ", state: " + stateButton.getBox().getState());

                gridPane.add(stateButton, i, j);
            }
        }
        return gridPane;
    }

    private Label createHeaderLabel(String icon, String tooltip) {
        Label headerLabel = new Label(icon);
        headerLabel.setTooltip(new Tooltip(tooltip));
        headerLabel.getStyleClass().add("suspect-header-icon");
        headerLabel.prefHeightProperty().bind(headerLabel.widthProperty());
        return headerLabel;
    }

    private void createClueDisplay() {
        cluePane.maxWidthProperty().bind(clueScrollPane.widthProperty());
        cluePane.prefWidthProperty().bind(clueScrollPane.prefWidthProperty());

        titleLabel.setText(GameStateManager.getInstance().getMurderCase().getTitle());
        caseDescriptionLabel.setText(GameStateManager.getInstance().getMurderCase().getDescription());
        caseDescriptionLabel.getStyleClass().addAll( "clue-pane","case-description");

        clueListPaneTitleLabel.prefWidthProperty().bind(clueListPane.widthProperty());

        createSuspectCards(GameStateManager.getInstance().getMurderCase().getSuspectListByType(SuspectType.PERSON));
        drawSuspectCardControls();

        writeClues();

        addSuspectStatements();

        drawSolutionInputs();
    }

    private void addSuspectStatements() {
        String checkStatement = GameStateManager.getInstance().getMurderCase().getPersonList().get(0).getStatement();
        if (checkStatement != null && !checkStatement.isEmpty()) {
            for (Person person: GameStateManager.getInstance().getMurderCase().getPersonList()) {
                Label statementLabel = new Label(person.getName() + ": " + person.getStatement());
                statementLabel.getStyleClass().add("case-clue");
                suspectStatementsPane.getChildren().add(statementLabel);
            }
        } else {
            suspectStatementsPane.getChildren().clear();
        }
    }

    private void drawSuspectCardControls() {
        ToggleGroup suspectTypeToggleGroup = new ToggleGroup();
        suspectTypeToggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton selectedSuspectTypeRB = (RadioButton) suspectTypeToggleGroup.getSelectedToggle();

            if (selectedSuspectTypeRB != null) {
                createSuspectCards(GameStateManager.getInstance().getMurderCase().getSuspectListByType(SuspectType.valueOf(selectedSuspectTypeRB.getId())));
            }
        });

        for (SuspectType suspectType : List.of(SuspectType.PERSON, SuspectType.WEAPON, SuspectType.MOTIVE, SuspectType.LOCATION)) {
            if (!suspectType.equals(SuspectType.MOTIVE) || GameStateManager.getInstance().getMurderCase().getMotiveList() != null ){
                RadioButton rb = new RadioButton();
                rb.setText(suspectType.name());
                rb.setId(suspectType.name());
                rb.setToggleGroup(suspectTypeToggleGroup);
                if (suspectType.equals(SuspectType.PERSON)) rb.selectedProperty().setValue(true);
                suspectTypeSelectionPane.getChildren().add(rb);
            }
        }
    }

    private void writeClues() {
        GameStateManager.getInstance().getMurderCase().getCluesList().forEach(clue -> {
            Label clueLabel = new Label(" - " + clue);
            clueLabel.getStyleClass().add("case-clue");
            clueListPane.getChildren().add(clueLabel);
        });
        if (GameStateManager.getInstance().getMurderCase().getPersonList().get(0).getStatement() == null) {
            clueListPane.getChildren().get(clueListPane.getChildren().size()-1).getStyleClass().add("last-clue");
            cluePane.getChildren().remove(suspectStatementsPane);
        }
    }

    private void drawSolutionInputs() {
        List<SuspectType> orderedSuspectTypeList = new ArrayList<>(List.of(SuspectType.PERSON, SuspectType.WEAPON, SuspectType.LOCATION));
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
        if (!suspectCardsPane.getChildren().isEmpty()) {
            suspectCardsPane.getChildren().clear();
        }

        for (Suspect suspect : suspectList) {
            VBox suspectCard = new VBox();
            suspectCard.getStyleClass().addAll( "card","suspect-card");

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
        Map<String, String> guess = new HashMap<>();
        for (SuspectType suspectType : GameStateManager.getInstance().getMurderCase().getSuspectTypes()) {
            Optional<Node> guessBoxOpt = solutionInputPane.getChildren().stream().filter(node -> node.getId().equals(suspectType.name())).findFirst();
            guessBoxOpt.ifPresent(node -> guess.put(suspectType.name(), ((ComboBox<String>) node).getValue()));
        }

        Map<String, Boolean> solutionMap = GameStateManager.getInstance().getMurderCase().getResolution().checkGuess(guess);

        if (solutionMap.values().stream().allMatch(e -> e)){
            String deductiveLogicoSays = "Deductive Logico came to the only reasonable conclusion. \"It was " + guess.get(SuspectType.PERSON.name()) + " in the " + guess.get(SuspectType.LOCATION.name()) + " with the " + guess.get(SuspectType.WEAPON.name());
            if (guess.get(SuspectType.MOTIVE.name()) != null) {
                deductiveLogicoSays += "! Why? " + guess.get(SuspectType.MOTIVE.name());
            }
            resolutionLabel.setText(deductiveLogicoSays + "!\" he declared.\n\n" + GameStateManager.getInstance().getMurderCase().getResolution().getResolutionText());
        } else {
            resolutionLabel.setText(GameStateManager.getInstance().getMurderCase().askInspectorIrratino(solutionMap));
        }

    }

    @FXML
    private void returnToLevelSelect() {
        try {
            Stage stage = (Stage) cluePane.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/zday/murdle/view/level-select-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), cluePane.getScene().getWidth(), cluePane.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("/org/zday/murdle/style/application.css").toExternalForm());
            try (InputStream is = this.getClass().getResourceAsStream("/org/zday/murdle/data/config/level-select-resources.json")) {
                ResourceDirectoryLoader resourceDirectoryLoader = (new ObjectMapper()).readValue(is, ResourceDirectoryLoader.class);
                scene.getStylesheets().addAll(resourceDirectoryLoader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }

            stage.setTitle("Murdle: digivolution");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something has gone wrong. Cannot load level select screen");
            alert.showAndWait();
        }

    }
}
