package org.zday.murdle.model.murdercase.suspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Person extends Suspect{
    private String hairColor;
    private String eyeColor;
    private String handedness;
    private String astrologicalSign;
    private String height;

}
