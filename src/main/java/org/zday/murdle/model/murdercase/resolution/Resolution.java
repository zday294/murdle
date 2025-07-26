package org.zday.murdle.model.murdercase.resolution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Motive;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resolution {
    private Solution correctSolution;

    private String resolutionText;

    public List<Boolean> checkGuess(Person person, Location location, Weapon weapon, Motive motive) {
        return List.of(person.getName().equals(correctSolution.getPerson()), location.getName().equals(correctSolution.getLocation()), weapon.getName().equals(correctSolution.getWeapon()), motive.getName().equals(correctSolution.getMotive()));
    }
}
