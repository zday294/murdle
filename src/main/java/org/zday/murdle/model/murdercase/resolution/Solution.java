package org.zday.murdle.model.murdercase.resolution;

import lombok.Data;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

@Data
public class Solution {
    private Person person;
    private Location location;
    private Weapon weapon;
}
