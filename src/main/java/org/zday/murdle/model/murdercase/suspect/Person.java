package org.zday.murdle.model.murdercase.suspect;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends Suspect{
    private String hairColor;
    private String eyeColor;
    private String handedness;
    private String astrologicalSign;
    private String height;

}
