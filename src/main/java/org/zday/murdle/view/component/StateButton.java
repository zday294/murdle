package org.zday.murdle.view.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import org.zday.murdle.model.notebook.BoxState;

public class StateButton extends Button {

    private BoxState boxState = BoxState.UNMARKED;


    private StringProperty stateName = new SimpleStringProperty(BoxState.UNMARKED.name());


    public StringProperty stateNameProperty() {
        return stateName;
    }

    public final String getStateName() {
        return stateName.getValue();
    }

    public final void setStateName(String name){
        stateName.setValue(name);
    }


    public void updateState() {
        boxState = boxState.update();
        setStateName(boxState.name());

    }

}
