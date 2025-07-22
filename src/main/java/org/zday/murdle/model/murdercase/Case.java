package org.zday.murdle.model.murdercase;

import lombok.Data;
import org.zday.murdle.model.murdercase.clues.Clue;
import org.zday.murdle.model.murdercase.resolution.Resolution;
import org.zday.murdle.model.murdercase.resolution.Solution;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.List;

@Data
public class Case {
    private String description;

    //suspects
    private List<Person> personList;
    private List<Location> locationList;
    private List<Weapon> weaponList;

    //clues
    private List<Clue> cluesList;

    //solution
    private Resolution resolution;
}
