package org.zday.murdle.model.murdercase.suspect;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Location extends Suspect{

    private Doorness doorness;

    protected enum Doorness {
        INDOORS,
        OUTDOORS
    }
}
