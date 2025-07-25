package org.zday.murdle.view.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;
import org.zday.murdle.model.notebook.Box;
import org.zday.murdle.model.notebook.Box.BoxState;

@Setter
@Getter
public class StateButton extends Button {

    private Box box;

//    private StringProperty stateName = new SimpleStringProperty(BoxState.UNMARKED.getIcon());
//
//
//    public StringProperty stateNameProperty() {
//        return stateName;
//    }
//
//    public final String getStateName() {
//        return stateName.getValue();
//    }
//
//    public final void setStateName(String name){
//        stateName.setValue(name);
//    }


    public void updateState() {
        box.update();
//        setStateName(box.getState().getIcon());
    }

}
