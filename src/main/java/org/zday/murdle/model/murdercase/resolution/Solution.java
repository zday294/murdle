package org.zday.murdle.model.murdercase.resolution;

import lombok.Data;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

@Data
public class Solution {
    private String person;
    private String location;
    private String weapon;
}
