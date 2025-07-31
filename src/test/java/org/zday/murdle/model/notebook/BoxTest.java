package org.zday.murdle.model.notebook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

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
        Box trueBox = new Box(Box.BoxState.TRUE);
        RowColumn rc = new RowColumn();
        rc.add(trueBox);

        Box falseBox = new Box(Box.BoxState.FALSE);
        falseBox.addEliminationListener(rc);
        falseBox.update();
        assertEquals(Box.BoxState.FALSE, falseBox.getState());

        Box unsureBox = new Box(Box.BoxState.UNSURE);
        unsureBox.addEliminationListener(rc);
        unsureBox.update();
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, unsureBox.getState());
    }

    @Test
    void eliminate() {
        Box trueBox = new Box(Box.BoxState.TRUE);
        RowColumn rc = new RowColumn();
        rc.add(trueBox);

        Box unmarkedBox = new Box(Box.BoxState.UNMARKED);
        unmarkedBox.addEliminationListener(rc);
        unmarkedBox.eliminate();
        assertEquals(Box.BoxState.FALSE_BY_ELIMINATION, unmarkedBox.getState());

        Box falseBox = new Box(Box.BoxState.FALSE);
        falseBox.addEliminationListener(rc);
        falseBox.eliminate();
        assertEquals(Box.BoxState.FALSE, falseBox.getState());

        Box unsureBox = new Box(Box.BoxState.UNSURE);
        unsureBox.addEliminationListener(rc);
        unsureBox.eliminate();
        assertEquals(Box.BoxState.UNSURE, unsureBox.getState());
    }

    @Test
    void uneliminate() {
        Box fbeBox = new Box(Box.BoxState.FALSE_BY_ELIMINATION);
        fbeBox.uneliminate();
        assertEquals(Box.BoxState.UNMARKED, fbeBox.getState());

        Box falseBox = new Box(Box.BoxState.FALSE);
        falseBox.uneliminate();
        assertEquals(Box.BoxState.FALSE, falseBox.getState());

        Box unsureBox = new Box(Box.BoxState.UNSURE);
        unsureBox.uneliminate();
        assertEquals(Box.BoxState.UNSURE, unsureBox.getState());
    }
}