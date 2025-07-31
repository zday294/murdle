package org.zday.murdle.model.murdercase.suspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Weapon extends Suspect {

    private Weight weight;
    private String madeOf;

    public enum Weight {
        LIGHT,
        MEDIUM,
        HEAVY
    }

public Weapon(String name, String description, String icon, Weight weight, String madeOf) {
        super(name, description, icon);
        this.weight = weight;
        this.madeOf = madeOf;
    }

    @Override
    public String getDetails() {
        return weight  + " | Made of " + madeOf;
    }
}
