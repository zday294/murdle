package org.zday.murdle.model.murdercase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zday.murdle.model.murdercase.clues.Clue;
import org.zday.murdle.model.murdercase.resolution.Resolution;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Case {
    private String title;
    private String description;
    private List<Person> personList;
    private List<Location> locationList;
    private List<Weapon> weaponList;
    private List<Clue> cluesList;
    private Resolution resolution;
}
