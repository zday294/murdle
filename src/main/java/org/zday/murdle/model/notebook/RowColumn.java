package org.zday.murdle.model.notebook;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RowColumn implements EliminationListener {
    List<Box> boxes;

    public RowColumn() {
        boxes = new ArrayList<>();
    }

    public void add(Box box) {
        boxes.add(box);
        box.addEliminationListener(this);
    }

    @Override
    public void eliminate() {
        boxes.forEach(Box::eliminate);
    }

    @Override
    public void uneliminate() {
        boxes.forEach(Box::uneliminate);
    }

    @Override
    public boolean checkEliminable() {
        return boxes.stream().anyMatch(box -> box.getState() == Box.BoxState.TRUE);
    }

    public RowColumn clone() {
        RowColumn clone = new RowColumn();
        for (Box box : boxes) {
            clone.add(box.clone());
        }
        return clone;
    }
}
