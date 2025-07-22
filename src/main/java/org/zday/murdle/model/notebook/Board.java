package org.zday.murdle.model.notebook;

import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.List;

public class Board {
    List<Block> blocks;

    public Board(List<Person> persons, List<Weapon> weapons, List<Location> locations) {
        blocks = List.of(new Block(weapons, persons), new Block(weapons, locations), new Block(locations, persons));
    }

    // could add constructor with motives later? easy enough to do: just have it call previous constructor then append blocks for
}
