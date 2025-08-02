package org.zday.murdle.model.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zday.murdle.model.game.murdercase.MurderCase;
import org.zday.murdle.model.game.notebook.Board;

import java.io.InputStream;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameStateManager {
    private MurderCase murderCase;
    private Board gameBoard;
    private Board savedBoard;
    private Board freshBoard;
    private final static GameStateManager INSTANCE = new GameStateManager();

    public static GameStateManager getInstance() {
        return INSTANCE;
    }

    public void loadMurderCase(String caseFileName) {
        try (InputStream is = this.getClass().getResourceAsStream(caseFileName)) {
            murderCase = (new ObjectMapper()).readValue(is, MurderCase.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBoard() {
        gameBoard = murderCase.getMotiveList().isEmpty() ?
                new Board(murderCase.getPersonList(), murderCase.getWeaponList(), murderCase.getLocationList())
                : new Board(murderCase.getPersonList(), murderCase.getWeaponList(), murderCase.getLocationList(), murderCase.getMotiveList());
        freshBoard = gameBoard.clone();
    }

    public void saveBoard() {
        savedBoard = gameBoard.clone();
    }

    public void loadSavedBoard() {
        if (savedBoard != null) {
            gameBoard.populateFromBoard(savedBoard);
        }
    }

    public void clearBoard() {
        gameBoard.populateFromBoard(freshBoard);
    }
}
