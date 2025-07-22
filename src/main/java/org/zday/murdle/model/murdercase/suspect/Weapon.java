package org.zday.murdle.model.murdercase.suspect;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Weapon extends Suspect {

    private Weight weight;
    private String madeOf;

    protected enum Weight {
        LIGHT,
        MEDIUM,
        HEAVY
    }
}
