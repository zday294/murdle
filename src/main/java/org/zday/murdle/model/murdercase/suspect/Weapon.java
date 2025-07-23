package org.zday.murdle.model.murdercase.suspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Weapon extends Suspect {

    private Weight weight;
    private String madeOf;

    protected enum Weight {
        LIGHT,
        MEDIUM,
        HEAVY
    }
}
