package org.zday.murdle.model.game.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.zday.murdle.model.game.murdercase.suspect.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Block {
    private List<RowColumn> rowsList;
    private List<RowColumn> columnsList;
    private SuspectType rowType;
    private SuspectType columnType;

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
            RowColumn row = new RowColumn();
            for (int j = 0; j < columnSuspects.size(); j++){
                row.add(new Box());
            }
            rowsList.add(row);
        }

        for (int i = 0; i < columnSuspects.size(); i++) {
            RowColumn column = new RowColumn();
            for (RowColumn row : rowsList) {
                column.add(row.getBoxes().get(i));
            }
            columnsList.add(column);
        }
    }

    private SuspectType determineType(Suspect suspect) {
        if (suspect instanceof Person) {
            return SuspectType.PERSON;
        } else if (suspect instanceof Location) {
            return SuspectType.LOCATION;
        } else if (suspect instanceof Weapon) {
            return SuspectType.WEAPON;
        } else if (suspect instanceof Motive) {
            return SuspectType.MOTIVE;
        } else {
            throw new IllegalArgumentException("Unknown suspect type: " + suspect.getClass().getName());
        }

    }

    public void populateFromBlock(Block fromBlock) {
        for (int i = 0; i < rowsList.size(); i++) {
            for (int j = 0; j < rowsList.get(i).getBoxes().size(); j++) {
                rowsList.get(i).getBoxes().get(j).populateFromBox(fromBlock.getRowsList().get(i).getBoxes().get(j));
            }
        }
    }

    public Block clone() {
        List<RowColumn> newRowList = new ArrayList<>();
        for (RowColumn row : rowsList) {
            newRowList.add(row.clone());
        }

        List<RowColumn> newColumnList = new ArrayList<>();
        for (int i = 0; i < columnsList.size(); i++) {
            RowColumn newColumn = new RowColumn();
            for (RowColumn row : rowsList) {
                newColumn.add(row.boxes.get(i));
            }
            newColumnList.add(newColumn);
        }

        return new Block(newRowList, newColumnList, this.rowType, this.columnType);
    }
}
