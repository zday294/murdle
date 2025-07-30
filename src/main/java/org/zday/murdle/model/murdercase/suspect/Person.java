package org.zday.murdle.model.murdercase.suspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Person extends Suspect{
    private String hairColor;
    private String eyeColor;
    private String handedness;
    private String astrologicalSign;
    private String height;
    private String statement;

    @Override
    public String getDetails() {
        return height + " | " + handedness + "-handed | " + eyeColor + " eyes | " + hairColor + " hair | " +   astrologicalSign;
    }

    public Person(String name, String description, String icon, String hairColor, String eyeColor, String handedness, String astrologicalSign, String height, String statement) {
        super(name, description, icon);
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.handedness = handedness;
        this.astrologicalSign = astrologicalSign;
        this.height = height;
        this.statement = statement;
    }


}
