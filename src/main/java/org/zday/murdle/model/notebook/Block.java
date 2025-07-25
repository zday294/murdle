package org.zday.murdle.model.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Suspect;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Block {
    private List<List<Box>> rowsList;
    private List<List<Box>> columnsList;
    private RowColumnType rowType;
    private RowColumnType columnType;

    public Block(List<? extends Suspect> rowSuspects, List<? extends Suspect> columnSuspects)  {
        if (rowSuspects.isEmpty() || columnSuspects.isEmpty()){
            throw new IllegalArgumentException("rowSuspects and columnSuspects must not be empty");
        } else if (rowSuspects.size() != columnSuspects.size()) {
            throw new IllegalArgumentException("Must have the same number of rowSuspects and columnSuspects! rowSuspects.size()=" + rowSuspects.size() + ", columnSuspects.size()=" + columnSuspects.size());
        }

        rowType = determineType(rowSuspects.get(0));
        columnType = determineType(columnSuspects.get(0));

        rowsList = new ArrayList<>();
        columnsList = new ArrayList<>();

        for (int i = 0; i < rowSuspects.size(); i++) {
            List<Box> row = new ArrayList<>();
            for (int j = 0; j < columnSuspects.size(); j++){
                row.add(new Box());
            }
            rowsList.add(row);
        }

        for (int i = 0; i < columnSuspects.size(); i++) {
            List<Box> column = new ArrayList<>();
            for (List<Box> row : rowsList) {
                column.add(row.get(i));
            }
            columnsList.add(column);
        }
    }

    private RowColumnType determineType(Suspect suspect) {
        if (suspect instanceof Person) {
            return RowColumnType.PERSON;
        } else if (suspect instanceof Location) {
            return RowColumnType.LOCATION;
        } else if (suspect instanceof Weapon) {
            return RowColumnType.WEAPON;
        } else {
            throw new IllegalArgumentException("Unknown suspect type: " + suspect.getClass().getName());
        }

    }

    public enum RowColumnType {
        PERSON,
        WEAPON,
        MOTIVE,
        LOCATION
    }

    public Block clone() {
        List<List<Box>> newRowList = new ArrayList<>();
        for (List<Box> row : rowsList) {
            List<Box> newRow = new ArrayList<>();
            for (Box box : row) {
                newRow.add(box.clone());
            }
            newRowList.add(newRow);
        }

        List<List<Box>> newColumnList = new ArrayList<>();
        for (int i = 0; i < columnsList.size(); i++) {
            List<Box> newColumn = new ArrayList<>();
            for (List<Box> row : rowsList) {
                newColumn.add(row.get(i));
            }
            newColumnList.add(newColumn);
        }

        return new Block(newRowList, newColumnList, this.rowType, this.columnType);
    }
}
