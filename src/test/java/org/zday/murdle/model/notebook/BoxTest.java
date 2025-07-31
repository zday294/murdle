package org.zday.murdle.model.notebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    Box unmarkedBox;
    Box falseBox;
    Box trueBox;
    Box unsureBox;
    Box fbeBox;

    @BeforeEach
    void setup() {
        unmarkedBox = new Box();
        falseBox = new Box(Box.BoxState.FALSE);
        trueBox = new Box(Box.BoxState.TRUE);
        unsureBox = new Box(Box.BoxState.UNSURE);
        fbeBox = new Box(Box.BoxState.FALSE_BY_ELIMINATION);
    }

    @Test
    void testClone() {
        Box box = new Box(Box.BoxState.FALSE);
        Box box2 = box.clone();

        assertEquals(box.getState(), box2.getState());

        box.update();
        assertNotEquals(box.getState(), box2.getState());
    }

    @Test
    void update_blankBoard() {
        Box box = new Box(Box.BoxState.UNMARKED);

        box.update();
        assertEquals(Box.BoxState.FALSE, box.getState());

        box.update();
        assertEquals(Box.BoxState.TRUE, box.getState());

        box.update();
        assertEquals(Box.BoxState.UNSURE, box.getState());

        box.update();
        assertEquals(Box.BoxState.UNMARKED, box.getState());
    }

    @Test
    void update_eliminable() {
        RowColumn rc = new RowColumn();
        rc.add(trueBox);

        falseBox.addEliminationListener(rc);
        falseBox.update();
        assertEquals(Box.BoxState.FALSE, falseBox.getState());

        unsureBox.addEliminationListener(rc);
        unsureBox.update();
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, unsureBox.getState());
    }

    @Test
    void eliminate() {
        RowColumn rc = new RowColumn();
        rc.add(trueBox);

        unmarkedBox.addEliminationListener(rc);
        unmarkedBox.eliminate();
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, unmarkedBox.getState());

        falseBox.addEliminationListener(rc);
        falseBox.eliminate();
        assertEquals(Box.BoxState.FALSE, falseBox.getState());

        unsureBox.addEliminationListener(rc);
        unsureBox.eliminate();
        assertEquals(Box.BoxState.UNSURE, unsureBox.getState());
    }

    @Test
    void uneliminate() {
        fbeBox.uneliminate();
        assertEquals(Box.BoxState.UNMARKED, fbeBox.getState());

        falseBox.uneliminate();
        assertEquals(Box.BoxState.FALSE, falseBox.getState());

        unsureBox.uneliminate();
        assertEquals(Box.BoxState.UNSURE, unsureBox.getState());
    }

    @Test
    void populateFromBox() {
        trueBox.populateFromBox(falseBox);
        assertEquals(Box.BoxState.FALSE, trueBox.getState());
    }
}