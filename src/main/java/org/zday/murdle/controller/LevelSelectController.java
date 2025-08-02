package org.zday.murdle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    }

    public void goToNextPage() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //load levels
        LevelManager.getInstance().loadLevels();

        //create level presentation
        createLevelCards();

        //add level data to level cards
    }

    private void createLevelCards() {
        List<Level> levelPage = LevelManager.getInstance().getCurrentLevelPage();
        for (int i = 0; i < 3; i++) {
            levelRow1.getChildren().add(createLevelCard(levelPage.get(i)));
        }
        for (int i = 3; i < 6; i++) {
            levelRow2.getChildren().add(createLevelCard(levelPage.get(i)));
        }

    }

    private VBox createLevelCard(Level level) {
        VBox levelCard = new VBox();
        levelCard.getStyleClass().addAll("card","level-card");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        imageView.setImage(level.getImage());

        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("level-title-label");
        titleLabel.setText(level.getName());

        Label difficultyLabel = new Label();
        difficultyLabel.getStyleClass().add("level-difficulty-label");
        difficultyLabel.setText("Difficulty: " + AssortedUtils.stringMultiply("🔎", level.getDifficulty()));

        levelCard.getChildren().addAll(imageView, titleLabel, difficultyLabel);

        return levelCard;
    }
}
