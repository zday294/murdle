package org.zday.murdle.model.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class Board {
    List<Block> blocks;

    public Board(List<Person> persons, List<Weapon> weapons, List<Location> locations) {
        blocks = List.of(new Block(weapons, persons), new Block(weapons, locations), new Block(locations, persons));
    }

    public Optional<Block> findBlockByRowAndColumnTypes(Block.RowColumnType rowType, Block.RowColumnType columnType) {
        return blocks.stream().filter(block -> block.getRowType() == rowType && block.getColumnType() == columnType)
                .findFirst();
//                .orElseThrow(() -> new IllegalArgumentException("No block found with row type " + rowType + " and column type " + columnType));
    }

    public Board clone() {
        List<Block> cloneBlocks = new ArrayList<>();

        for (Block block : blocks) {
            cloneBlocks.add(block.clone());
        }

        return new Board(cloneBlocks);
    }

    public List<Optional<Block>> getRowBySuspect(Block.RowColumnType rowType){
        List<Optional<Block>> blockList = new ArrayList<>();
        for (Block.RowColumnType columnType : Arrays.stream(Block.RowColumnType.values()).filter(e -> !e.equals(rowType)).toList()) {
            blockList.add(findBlockByRowAndColumnTypes(rowType, columnType));
        }
        return blockList;
    }

    public void populateFromBoard(Board fromBoard) {
        for (int i = 0; i < fromBoard.getBlocks().size(); i++) {
            this.getBlocks().get(i).populatefromBlock(fromBoard.getBlocks().get(i));
        }
    }
}
