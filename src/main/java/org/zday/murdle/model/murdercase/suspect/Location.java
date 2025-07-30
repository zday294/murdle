package org.zday.murdle.model.murdercase.suspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Location extends Suspect{

    private Doorness doorness;

    protected enum Doorness {
        INDOORS,
        OUTDOORS
    }

    @Override
    public String getDetails() {
        return doorness.toString();
    }
}
