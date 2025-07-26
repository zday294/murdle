package org.zday.murdle.model.murdercase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zday.murdle.model.murdercase.clues.Clue;
import org.zday.murdle.model.murdercase.resolution.Resolution;
import org.zday.murdle.model.murdercase.suspect.*;
import org.zday.murdle.model.notebook.Block;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MurderCase {
    private String title;
    private String description;
    private List<Person> personList;
    private List<Location> locationList;
    private List<Weapon> weaponList;
    private List<Motive> motiveList;
    private List<String> cluesList;
    private String hint;
    private Resolution resolution;

    public List<Suspect> getSuspectListByType(SuspectType type) {
        List<Suspect> suspectList = new ArrayList<>();
        switch (type) {
            case PERSON -> suspectList.addAll(personList);
            case WEAPON -> suspectList.addAll(weaponList);
            case LOCATION -> suspectList.addAll(locationList);
            case MOTIVE -> suspectList.addAll(motiveList);
        }
        return suspectList;
    }
}
