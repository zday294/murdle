package org.zday.murdle.model.game.murdercase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zday.murdle.model.game.murdercase.suspect.*;

import java.util.*;

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

    public List<SuspectType> getSuspectTypes() {
        return Arrays.stream(SuspectType.values())
                .filter(suspectType -> suspectType != SuspectType.MOTIVE || ( motiveList != null && !motiveList.isEmpty()))
                .toList();
    }

    public String askInspectorIrratino(Map<String, Boolean> solutionMap) {
        final String IS_CORRECT = "Inspector Irratino believes that you're right about the ";
        final String IS_WRONG = "Inspector Irratino is confident that you're wrong about everything.";

        List<String> correctAnswers = getSuspectTypes().stream()
                .map(Enum::name)
                .filter(solutionMap::get)
                .toList();

        int numCorrectAnswers = correctAnswers.size();
        switch (numCorrectAnswers) {
            case 1 -> {
                return IS_CORRECT + correctAnswers.get(0).toLowerCase();
            }
            case 2 -> {
                return IS_CORRECT + correctAnswers.get(0).toLowerCase() + " and the " + correctAnswers.get(1).toLowerCase() + ".";
            }
            case 3 -> {
                return IS_CORRECT + correctAnswers.get(0).toLowerCase() + ", the " + correctAnswers.get(1).toLowerCase() + ", and the " + correctAnswers.get(2).toLowerCase() + ".";
            }
            default -> {
                return IS_WRONG;
            }
        }
    }

}
