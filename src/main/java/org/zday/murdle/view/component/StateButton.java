package org.zday.murdle.view.component;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;
import org.zday.murdle.model.game.notebook.Box;

@Setter
@Getter
public class StateButton extends Button {

    private Box box;

    public void updateState() {
        box.update();
    }

    public StateButton() {
        super();
        getStyleClass().add("state-button");
    }

}
