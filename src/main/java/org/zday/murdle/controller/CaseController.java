package org.zday.murdle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.zday.murdle.model.murdercase.Case;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;
import org.zday.murdle.model.notebook.Block;
import org.zday.murdle.model.notebook.Board;
import org.zday.murdle.view.component.StateButton;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CaseController implements Initializable {

    private String caseFileName;

    @Getter
    private Case caseInstance;

    private Board board;

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
        ObjectMapper mapper = new ObjectMapper();

        //get current working directory
//        caseFileName = System.getProperty("user.dir") + "/data/cases/zach-test-case.json";
        //get classpath


        caseFileName = "/org/zday/murdle/data/cases/zach-test-case.json";

        try {
            InputStream is = this.getClass().getResourceAsStream(caseFileName);
            String json = new String(is.readAllBytes());
            caseInstance = mapper.readValue(json, Case.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        createNotebook();
    }

    private void createNotebook() {
        //create board and add to notebook pane


        createBoardControls();
    }

    private void createBoard() {
        board = new Board(caseInstance.getPersonList(), caseInstance.getWeaponList(), caseInstance.getLocationList());

        TableView<List<StateButton>> tableView = new TableView<>();
        tableView.getColumns().add(new TableColumn<>("")); // column for row labels
        for(Person person : caseInstance.getPersonList()) {
            tableView.getColumns().add(new TableColumn<>(person.getIcon()));
        }

        for (Location location : caseInstance.getLocationList()){
            tableView.getColumns().add(new TableColumn<>(location.getIcon()));
        }

        Block weaponPersonBlock = board.findBlockByRowAndColumnTypes(Block.RowColumnType.WEAPON, Block.RowColumnType.PERSON);
        Block weaponLocationBlock = board.findBlockByRowAndColumnTypes(Block.RowColumnType.WEAPON, Block.RowColumnType.LOCATION);
        Block locationPersonBlock = board.findBlockByRowAndColumnTypes(Block.RowColumnType.LOCATION, Block.RowColumnType.PERSON);
        for (Weapon weapon : caseInstance.getWeaponList()) {


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

    private GridPane createBlock(Block block) {
        //create grid
        GridPane gridPane = new GridPane();

        for (int i = 0; i < block.getRowsList().size(); i++) {
            for (int j = 0; j < block.getRowsList().get(i).size(); j++) {
                StateButton stateButton = new StateButton();
            }
        }

        return gridPane;
    }


}
