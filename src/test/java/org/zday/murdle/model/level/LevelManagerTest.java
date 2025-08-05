package org.zday.murdle.model.level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelManagerTest {

    LevelManager levelManager;

    @BeforeEach
    void setUp() {
        levelManager = LevelManager.getInstance();
        // Inject mock levels (14 levels, so 3 pages: 6, 6, 2)
        List<Level> mockLevels = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            Level level = new Level();
            // Optionally set properties on level
            mockLevels.add(level);
        }
        levelManager.levelList = mockLevels;
        levelManager.currentLevelPageNumber = 1;
    }

    @Test
    void getLevelPage() {
        levelManager.currentLevelPageNumber = -1;

        List<Level> page1 = levelManager.getLevelPage(1);
        assertEquals(6, page1.size());
        assertEquals(-1, levelManager.currentLevelPageNumber);

        List<Level> page2 = levelManager.getLevelPage(2);
        assertEquals(6, page2.size());
        assertEquals(-1, levelManager.currentLevelPageNumber);

        List<Level> page3 = levelManager.getLevelPage(3);
        assertEquals(2, page3.size());
        assertEquals(-1, levelManager.currentLevelPageNumber);
    }

    @Test
    void getCurrentLevelPage() {
        List<Level> current = levelManager.getCurrentLevelPage();
        assertEquals(6, current.size());
        assertEquals(1, levelManager.currentLevelPageNumber);
    }

    @Test
    void getNextLevelPage() {
        List<Level> next = levelManager.getNextLevelPage();
        assertEquals(6, next.size());
        assertEquals(2, levelManager.currentLevelPageNumber);

        List<Level> next2 = levelManager.getNextLevelPage();
        assertEquals(2, next2.size());
        assertEquals(3, levelManager.currentLevelPageNumber);

        // Try to go past last page
        List<Level> next3 = levelManager.getNextLevelPage();
        assertEquals(2, next3.size());
        assertEquals(3, levelManager.currentLevelPageNumber);
    }

    @Test
    void getPreviousLevelPage() {
        levelManager.currentLevelPageNumber = 3;
        List<Level> prev = levelManager.getPreviousLevelPage();
        assertEquals(6, prev.size());
        assertEquals(2, levelManager.currentLevelPageNumber);

        List<Level> prev2 = levelManager.getPreviousLevelPage();
        assertEquals(6, prev2.size());
        assertEquals(1, levelManager.currentLevelPageNumber);

        // Try to go before first page
        List<Level> prev3 = levelManager.getPreviousLevelPage();
        assertEquals(6, prev3.size());
        assertEquals(1, levelManager.currentLevelPageNumber); // Should increment back
    }
}