package org.zday.murdle.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.HyperlinkLabel;
import org.zday.murdle.model.level.Level;
import org.zday.murdle.model.level.LevelManager;
import org.zday.murdle.util.AssortedUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LevelSelectController implements Initializable {

    @FXML
    VBox leftPane;

    @FXML
    Button previousButton;

    @FXML
    HBox levelRow1;

    @FXML
    HBox levelRow2;

    @FXML
    VBox rightPane;

    @FXML
    Button nextButton;


    public void goToPreviousPage() {
        List<Level> levelPage = LevelManager.getInstance().getPreviousLevelPage();
        createLevelCards(levelPage);
    }

    public void goToNextPage() {
        List<Level> levelPage = LevelManager.getInstance().getNextLevelPage();
        createLevelCards(levelPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //load levels
        LevelManager.getInstance().loadLevels();

        //create level presentation
        createLevelCards(LevelManager.getInstance().getCurrentLevelPage());

        //add level data to level cards
    }

    private void createLevelCards(List<Level> levelPage) {
        levelRow1.getChildren().clear();
        levelRow2.getChildren().clear();

        int i = 0;
        for ( ;  i < 3 ; i++) {
            if (i < levelPage.size()) {
                levelRow1.getChildren().add(createLevelCard(levelPage.get(i)));
            } else {
                levelRow1.getChildren().add(createLockedLevelCard());
            }
        }
        for ( ; i < 6 ; i++) {
            if (i < levelPage.size()) {
                levelRow2.getChildren().add(createLevelCard(levelPage.get(i)));
            } else {
                levelRow2.getChildren().add(createLockedLevelCard());
            }
        }
    }

    private VBox createLockedLevelCard() {
        VBox lockedLevelCard = new VBox();
        lockedLevelCard.getStyleClass().addAll("card", "level-card", "locked-level");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/magnifying-glass.png")));

        lockedLevelCard.getChildren().add(imageView);
        return lockedLevelCard;
    }

    private VBox createLevelCard(Level level) {
        VBox levelCard = new VBox();
        levelCard.getStyleClass().addAll("card","level-card");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        imageView.setImage(level.getImage());

        //try replacing this with a hyperlink to load the level
//        Label titleLabel = new Label();
//        titleLabel.getStyleClass().add("level-title-label");
//        titleLabel.setText(level.getName());

        Hyperlink titleLinkLabel = new Hyperlink();
        titleLinkLabel.getStyleClass().add("level-title-link");
        titleLinkLabel.setText(level.getName());
        titleLinkLabel.setOnAction(e -> loadLevel(level.getCaseFileName()));


        Label difficultyLabel = new Label();
        difficultyLabel.getStyleClass().add("level-difficulty-label");
        difficultyLabel.setText("Difficulty: " + AssortedUtils.stringMultiply("🔎", level.getDifficulty()));

        levelCard.getChildren().addAll(imageView, titleLinkLabel, difficultyLabel);

        return levelCard;
    }

    private void loadLevel(String caseFileName) {

//        Stage stage =  (Stage) leftPane.getScene().getWindow();


    }
}
