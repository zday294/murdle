package org.zday.murdle.model.murdercase.resolution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Motive;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resolution {
    private Map<String, String> correctSolution;

    private String resolutionText;

    public Map<String, Boolean> checkGuess(Map<String, String> guess) {
        Map<String, Boolean> correctnessMap = new HashMap<>();
        guess.keySet().forEach(key -> correctnessMap.put(key, guess.get(key).equalsIgnoreCase(correctSolution.get(key))));
        return correctnessMap;
    }
}
