package org.zday.murdle.model.game.notebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RowColumnTest {
    RowColumn rowColumn;

    @BeforeEach
    void setup() {
        rowColumn = new RowColumn();
    }

    void createListOfBlankBoxes() {
        rowColumn.getBoxes().clear();
        List.of(new Box(), new Box(), new Box()).forEach(rowColumn::add);
    }

    void blankRowUpdateOneBox(int updatedBoxIndex, int otherIndex1, int otherIndex2, Box.BoxState inputBoxInitialState, Box.BoxState inputBoxUpdatedState, Box.BoxState affectedBoxesNewState){
        rowColumn.getBoxes().clear();
        createListOfBlankBoxes();
        rowColumn.getBoxes().get(updatedBoxIndex).setState(inputBoxInitialState);
        rowColumn.getBoxes().get(updatedBoxIndex).update();
        assertEquals(inputBoxUpdatedState, rowColumn.getBoxes().get(updatedBoxIndex).getState());
        assertEquals(affectedBoxesNewState, rowColumn.getBoxes().get(otherIndex1).getState());
        assertEquals(affectedBoxesNewState, rowColumn.getBoxes().get(otherIndex2).getState());
    }

    @Test
    void eliminate() {
        blankRowUpdateOneBox(0,1,2, Box.BoxState.FALSE, Box.BoxState.TRUE, Box.BoxState.FALSE_BY_ELIMINATION);
        blankRowUpdateOneBox(1,0,2, Box.BoxState.FALSE, Box.BoxState.TRUE, Box.BoxState.FALSE_BY_ELIMINATION);
        blankRowUpdateOneBox(2,0,1, Box.BoxState.FALSE, Box.BoxState.TRUE, Box.BoxState.FALSE_BY_ELIMINATION);
    }

    @Test
    void uneliminate() {
        blankRowUpdateOneBox(0,1,2, Box.BoxState.TRUE, Box.BoxState.UNSURE, Box.BoxState.UNMARKED);
        blankRowUpdateOneBox(1,0,2, Box.BoxState.TRUE, Box.BoxState.UNSURE, Box.BoxState.UNMARKED);
        blankRowUpdateOneBox(2,0,1, Box.BoxState.TRUE, Box.BoxState.UNSURE, Box.BoxState.UNMARKED);
    }

    @Test
    void checkEliminable() {
        createListOfBlankBoxes();
        assertFalse(rowColumn.checkEliminable());

        rowColumn.getBoxes().get(0).setState(Box.BoxState.TRUE);
        assertTrue(rowColumn.checkEliminable());

    }

    @Test
    void testClone() {
        rowColumn.setBoxes(List.of(new Box(Box.BoxState.FALSE), new Box(), new Box()));
        RowColumn rowColumn2 = rowColumn.clone();

        rowColumn.getBoxes().get(0).update();

        assertNotEquals(rowColumn.getBoxes().get(0), rowColumn2.getBoxes().get(0));
        assertNotEquals(rowColumn.getBoxes().get(1), rowColumn2.getBoxes().get(1));
        assertNotEquals(rowColumn.getBoxes().get(2), rowColumn2.getBoxes().get(2));

    }
}