package org.zday.murdle.model.murdercase.suspect;

import lombok.Data;

@Data
public abstract class Suspect {
    private String name;
    private String description;
    private String imageLocation;
}
