package org.zday.murdle.model.level;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelManager {
    List<Level> levelList;

    int currentLevelPageNumber = 1;
    // need a save file at some point to determine which levels are visible. or only load levels that are unlocked

    private final static LevelManager INSTANCE = new LevelManager();

    public static LevelManager getInstance() { return INSTANCE; }

    public void loadLevels() {
        try (InputStream is = getClass().getResourceAsStream("/org/zday/murdle/data/levels.json")) {
            levelList = (new ObjectMapper()).readValue(is, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Level> getLevelPage(int pageNumber) {
        int startingLevelIndex = (pageNumber-1) * 6;
        List<Level> levelPage = new ArrayList<>();
        for (int i = startingLevelIndex; i < Math.min(levelList.size(), startingLevelIndex+6); i++ ){
            levelPage.add(levelList.get(i));
        }
        return levelPage;
    }

    public List<Level> getCurrentLevelPage() {
        return getLevelPage(currentLevelPageNumber);
    }

    public List<Level> getNextLevelPage() {
        return getLevelPage(++currentLevelPageNumber);
    }

    public List<Level> getPreviousLevelPage() {
        return getLevelPage(--currentLevelPageNumber);
    }
}
