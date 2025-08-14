package org.zday.murdle.model.game.murdercase.suspect;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Motive extends Suspect{
    public Motive(String name, String description, String icon) {
        super(name, description, icon);
    }
}
