package org.zday.murdle.model.game.notebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zday.murdle.model.game.murdercase.suspect.Location;
import org.zday.murdle.model.game.murdercase.suspect.Motive;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BlockTest {
    Block block;
    List<Location> locationList;
    List<Motive> motiveList;

    @BeforeEach
    void setup() {
        locationList = List.of(
                new Location("Library", "A quiet room full of books", "library_icon.png", Location.Doorness.INDOORS),
                new Location("Garden", "A lush outdoor garden", "garden_icon.png", Location.Doorness.OUTDOORS),
                new Location("Kitchen", "Where meals are prepared", "kitchen_icon.png", Location.Doorness.INDOORS)
        );

        motiveList = List.of(
                new Motive("Revenge", "Seeking payback for a past wrong", "revenge_icon.png"),
                new Motive("Greed", "Desire for wealth or possessions", "greed_icon.png"),
                new Motive("Jealousy", "Driven by envy of another", "jealousy_icon.png")
        );

        block = new Block(locationList, motiveList);
    }

    @Test
    void populateFromBlock() {
        Block block2 = new Block(locationList, motiveList);
        changeBlock1BoxStates();
        block2.populateFromBlock(block);

        assertEquals(Box.BoxState.TRUE, block2.getRowsList().get(0).getBoxes().get(0).getState());
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, block2.getRowsList().get(0).getBoxes().get(1).getState());
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, block2.getRowsList().get(0).getBoxes().get(2).getState());

        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, block2.getRowsList().get(1).getBoxes().get(0).getState());
        assertEquals(Box.BoxState.TRUE, block2.getRowsList().get(1).getBoxes().get(1).getState());
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, block2.getRowsList().get(1).getBoxes().get(2).getState());

        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, block2.getRowsList().get(2).getBoxes().get(0).getState());
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, block2.getRowsList().get(2).getBoxes().get(1).getState());
        assertEquals(Box.BoxState.TRUE, block2.getRowsList().get(2).getBoxes().get(2).getState());


    }

    @Test
    void testClone() {
        Block block2 = block.clone();
        changeBlock1BoxStates();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3; j++) {
                assertNotEquals(block.getRowsList().get(i).getBoxes().get(j).getState(), block2.getRowsList().get(i).getBoxes().get(j).getState());
            }
        }

    }

    void changeBlock1BoxStates() {
        block.getRowsList().get(0).getBoxes().get(0).update();
        block.getRowsList().get(0).getBoxes().get(0).update();
        block.getRowsList().get(1).getBoxes().get(1).update();
        block.getRowsList().get(1).getBoxes().get(1).update();
        block.getRowsList().get(2).getBoxes().get(2).update();
        block.getRowsList().get(2).getBoxes().get(2).update();
    }
}