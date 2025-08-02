package org.zday.murdle.model.game.murdercase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resolution {
    private Map<String, String> correctSolution;

    private String resolutionText;

    public Map<String, Boolean> checkGuess(Map<String, String> guess) {
        return guess.keySet().stream().collect(Collectors.toMap(e -> e, key -> correctSolution.get(key.toLowerCase()).equals(guess.get(key))));
    }
}
