package org.zday.murdle.model.murdercase.resolution;

import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.List;

public class Resolution {
    private Solution correctSolution;

    private String resolutionText;

    public List<Boolean> checkGuess(Person person, Location location, Weapon weapon) {
        return List.of(person.equals(correctSolution.getPerson()), location.equals(correctSolution.getLocation()), weapon.equals(correctSolution.getWeapon()));
    }
}
