package org.zday.murdle.model.murdercase.clues;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clue { // potentially extendable for clues like fingerprints and autobiography quotes
    private String description;
}
