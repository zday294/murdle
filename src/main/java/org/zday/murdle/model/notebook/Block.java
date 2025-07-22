package org.zday.murdle.model.notebook;

import org.zday.murdle.model.murdercase.suspect.Suspect;

import java.util.ArrayList;
import java.util.List;

public class Block {
    // row, column indexing
    List<List<BoxState>> rowColumnBoxes;

    public Block(List<? extends Suspect> rows, List<? extends Suspect> columns)  {
        if (rows.size() != columns.size()) {
            throw new IllegalArgumentException("Must have the same number of rows and columns! rows.size()=" + rows.size() + ", columns.size()=" + columns.size());
        }

        rowColumnBoxes = new ArrayList<>();

        for (int i = 0; i < rows.size(); i++) {
            rowColumnBoxes.add(new ArrayList<>());
            for (int j = 0; j < columns.size(); j++) {
                rowColumnBoxes.get(i).add(BoxState.UNMARKED);
            }
        }
    }
}
