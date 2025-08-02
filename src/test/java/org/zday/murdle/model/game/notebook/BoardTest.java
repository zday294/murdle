package org.zday.murdle.model.game.notebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zday.murdle.model.game.murdercase.suspect.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {
    Board board;
    Board boardWithMotive;
    List<Person> personList;
    List<Weapon> weaponList;
    List<Location>  locationList;
    List<Motive> motiveList;

    @BeforeEach
    void setup() {
        // Persons
        Person person1 = new Person(
                "John Doe",
                "Tall man with glasses",
                "person_icon.png",
                "Brown",
                "Blue",
                "Right",
                "Gemini",
                "6ft",
                "I was in the kitchen at the time."
        );
        Person person2 = new Person(
                "Jane Smith",
                "Short woman with red hair",
                "person2_icon.png",
                "Red",
                "Green",
                "Left",
                "Leo",
                "5ft 4in",
                "I heard a loud noise from the hallway."
        );
        Person person3 = new Person(
                "Alex Johnson",
                "Middle-aged with a beard",
                "person3_icon.png",
                "Black",
                "Brown",
                "Right",
                "Virgo",
                "5ft 10in",
                "I was reading in the lounge."
        );

        // Locations
        Location location1 = new Location(
                "Library",
                "A quiet room full of books",
                "library_icon.png",
                Location.Doorness.INDOORS
        );
        Location location2 = new Location(
                "Garden",
                "A lush outdoor garden",
                "garden_icon.png",
                Location.Doorness.OUTDOORS
        );
        Location location3 = new Location(
                "Kitchen",
                "A modern kitchen with stainless steel appliances",
                "kitchen_icon.png",
                Location.Doorness.INDOORS
        );

        // Weapons
        Weapon weapon1 = new Weapon(
                "Candlestick",
                "A heavy brass candlestick",
                "candlestick_icon.png",
                Weapon.Weight.HEAVY,
                "Brass"
        );
        Weapon weapon2 = new Weapon(
                "Knife",
                "A sharp kitchen knife",
                "knife_icon.png",
                Weapon.Weight.LIGHT,
                "Steel"
        );
        Weapon weapon3 = new Weapon(
                "Rope",
                "A long, sturdy rope",
                "rope_icon.png",
                Weapon.Weight.MEDIUM,
                "Hemp"
        );

        // Motives
        Motive motive1 = new Motive(
                "Jealousy",
                "Driven by envy",
                "motive_icon.png"
        );

        Motive motive2 = new Motive(
                "Revenge",
                "Seeking payback for a past wrong",
                "revenge_icon.png"
        );

        Motive motive3 = new Motive(
                "Greed",
                "Desire for wealth or possessions",
                "greed_icon.png"
        );

        personList = List.of(person1, person2, person3);
        weaponList = List.of(weapon1, weapon2, weapon3);
        locationList = List.of(location1, location2, location3);
        motiveList = List.of(motive1, motive2, motive3);

        board = new Board(personList, weaponList, locationList);
        boardWithMotive = new Board(personList, weaponList, locationList, motiveList);
    }


    @Test
    void findBlockByRowAndColumnSuspectTypes() {
    }

    @Test
    void testClone_WithoutMotive() {
        Board cloneBoard = board.clone();
        assertEquals(3, cloneBoard.getBlocks().size());

        for (Block block : board.getBlocks()) {
            for (int i = 0; i < 3; i++) {
                block.getRowsList().get(i).getBoxes().get(i).update();
                block.getRowsList().get(i).getBoxes().get(i).update();
            }
        }

        for (int i = 0; i < cloneBoard.getBlocks().size(); i++) {
            Block testBlock = cloneBoard.getBlocks().get(i);
            for (int j = 0; j < testBlock.getRowsList().size(); j++) {
                RowColumn testRow = testBlock.getRowsList().get(j);
                for (int k = 0; k < testRow.getBoxes().size(); k++ ) {
                    assertNotEquals(board.getBlocks().get(i).getRowsList().get(j).getBoxes().get(k).getState(), testRow.getBoxes().get(k).getState());
                }
            }
        }
    }

    @Test
    void testClone_WithMotive() {
        Board cloneBoard = boardWithMotive.clone();
        assertEquals(6, cloneBoard.getBlocks().size());

        for (Block block : boardWithMotive.getBlocks()) {
            for (int i = 0; i < 3; i++) {
                block.getRowsList().get(i).getBoxes().get(i).update();
                block.getRowsList().get(i).getBoxes().get(i).update();
            }
        }

        for (int i = 0; i < cloneBoard.getBlocks().size(); i++) {
            Block testBlock = cloneBoard.getBlocks().get(i);
            for (int j = 0; j < testBlock.getRowsList().size(); j++) {
                RowColumn testRow = testBlock.getRowsList().get(j);
                for (int k = 0; k < testRow.getBoxes().size(); k++ ) {
                    assertNotEquals(boardWithMotive.getBlocks().get(i).getRowsList().get(j).getBoxes().get(k).getState(), testRow.getBoxes().get(k).getState());
                }
            }
        }
    }

    @Test
    void getRowBySuspectType_testSize() {
        List<Optional<Block>> row = board.getRowBySuspectType(SuspectType.PERSON);
        List<Optional<Block>> rowWithMotive = boardWithMotive.getRowBySuspectType(SuspectType.PERSON);

        assertEquals(3, row.size());
        assertEquals(3, rowWithMotive.size());
    }

    @Test
    void getRowBySuspectType_Person() {
        List<Optional<Block>> row = board.getRowBySuspectType(SuspectType.PERSON);
        List<Optional<Block>> rowWithMotive = boardWithMotive.getRowBySuspectType(SuspectType.PERSON);

        assertTrue(row.stream().allMatch(Optional::isEmpty));
        assertTrue(rowWithMotive.stream().allMatch(Optional::isEmpty));
    }

    @Test
    void getRowBySuspectType_Weapon() {
        List<Optional<Block>> row = board.getRowBySuspectType(SuspectType.WEAPON);

        assertTrue(row.get(0).isPresent());
        assertEquals(SuspectType.WEAPON, row.get(0).get().getRowType());
        assertEquals(SuspectType.PERSON, row.get(0).get().getColumnType());

        assertTrue(row.get(1).isEmpty());

        assertTrue(row.get(2).isPresent());
        assertEquals(SuspectType.WEAPON, row.get(2).get().getRowType());
        assertEquals(SuspectType.LOCATION, row.get(2).get().getColumnType());


    }

    @Test
    void getRowBySuspectType_Weapon_WithMotive() {
        List<Optional<Block>> rowWithMotive = boardWithMotive.getRowBySuspectType(SuspectType.WEAPON);

        assertTrue(rowWithMotive.get(0).isPresent());
        assertEquals(SuspectType.WEAPON, rowWithMotive.get(0).get().getRowType());
        assertEquals(SuspectType.PERSON, rowWithMotive.get(0).get().getColumnType());

        assertTrue(rowWithMotive.get(1).isPresent());
        assertEquals(SuspectType.WEAPON, rowWithMotive.get(1).get().getRowType());
        assertEquals(SuspectType.MOTIVE, rowWithMotive.get(1).get().getColumnType());

        assertTrue(rowWithMotive.get(2).isPresent());
        assertEquals(SuspectType.WEAPON, rowWithMotive.get(2).get().getRowType());
        assertEquals(SuspectType.LOCATION, rowWithMotive.get(2).get().getColumnType());
    }

    @Test
    void getRowBySuspectType_LocationRowType_WithoutMotive() {
        List<Optional<Block>> row = board.getRowBySuspectType(SuspectType.LOCATION);

        assertTrue(row.get(0).isPresent());
        assertEquals(SuspectType.LOCATION, row.get(0).get().getRowType());
        assertEquals(SuspectType.PERSON, row.get(0).get().getColumnType());

        row.remove(0);
        assertTrue(row.stream().allMatch(Optional::isEmpty));
    }

    @Test
    void getRowBySuspectType_LocationRowType_WithMotive() {
        List<Optional<Block>> rowWithMotive = boardWithMotive.getRowBySuspectType(SuspectType.LOCATION);

        assertTrue(rowWithMotive.get(0).isPresent());
        assertEquals(SuspectType.LOCATION, rowWithMotive.get(0).get().getRowType());
        assertEquals(SuspectType.PERSON, rowWithMotive.get(0).get().getColumnType());

        assertTrue(rowWithMotive.get(1).isEmpty());

        assertTrue(rowWithMotive.get(2).isPresent());
        assertEquals(SuspectType.LOCATION, rowWithMotive.get(2).get().getRowType());
        assertEquals(SuspectType.MOTIVE, rowWithMotive.get(2).get().getColumnType());
    }

    @Test
    void getRowBySuspectType_MotiveRowType_WithoutMotive() {
        // person, row size = 0 because there is no person row
        List<Optional<Block>> row = board.getRowBySuspectType(SuspectType.MOTIVE);

        assertTrue(row.stream().allMatch(Optional::isEmpty));

    }

    @Test
    void getRowBySuspectType_MotiveRowType_WithMotive(){
        List<Optional<Block>> rowWithMotive = boardWithMotive.getRowBySuspectType(SuspectType.MOTIVE);

        assertTrue(rowWithMotive.get(0).isPresent());
        assertEquals(SuspectType.MOTIVE, rowWithMotive.get(0).get().getRowType());
        assertEquals(SuspectType.PERSON, rowWithMotive.get(0).get().getColumnType());

        rowWithMotive.remove(0);
        assertTrue(rowWithMotive.stream().allMatch(Optional::isEmpty));

    }



    @Test
    void populateFromBoard() {
        //create blank board
        Board blankBoard = new Board(personList, weaponList, locationList, motiveList);

        for (Block block : boardWithMotive.getBlocks()) {
            for (int i = 0; i < 3; i++) {
                block.getRowsList().get(i).getBoxes().get(i).update();
                block.getRowsList().get(i).getBoxes().get(i).update();
            }
        }

        blankBoard.populateFromBoard(boardWithMotive);

        for (int i = 0; i < blankBoard.getBlocks().size(); i++) {
            Block testBlock = blankBoard.getBlocks().get(i);
            for (int j = 0; j < testBlock.getRowsList().size(); j++) {
                RowColumn testRow = testBlock.getRowsList().get(j);
                for (int k = 0; k < testRow.getBoxes().size(); k++ ) {
                    assertEquals(boardWithMotive.getBlocks().get(i).getRowsList().get(j).getBoxes().get(k).getState(), testRow.getBoxes().get(k).getState());
                }
            }
        }
    }
}