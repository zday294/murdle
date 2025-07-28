package org.zday.murdle.model.murdercase.suspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Suspect {
    private String name;
    private String description;
    private String icon;

    public String getDetails() {
        return "";
    }
}
