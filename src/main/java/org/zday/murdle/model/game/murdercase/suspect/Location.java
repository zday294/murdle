package org.zday.murdle.model.game.murdercase.suspect;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Location extends Suspect{

    private Doorness doorness;

    public enum Doorness {
        INDOORS,
        OUTDOORS
    }

    @Override
    public String getDetails() {
        return doorness.toString();
    }

    public Location(String name, String description, String icon, Doorness doorness) {
        super(name, description, icon);
        this.doorness = doorness;
    }
}
