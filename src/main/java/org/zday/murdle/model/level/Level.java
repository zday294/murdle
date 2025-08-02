package org.zday.murdle.model.level;

import javafx.scene.image.Image;
import lombok.Data;

@Data
public class Level {
    private String name;
    private String caseFileName;
    private int difficulty;
    private String caseImageFile;

    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/org/zday/murdle/data/images/levels/" + caseImageFile));
    }

}
