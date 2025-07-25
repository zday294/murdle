package org.zday.murdle.model.notebook;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Box {

    @Getter
    private BoxState state;

    private StringProperty stateIcon;

    public Box() {
        state = BoxState.UNMARKED;
        stateIcon = new SimpleStringProperty(state.getIcon());
    }

    public Box(BoxState newState) {
        this.state = newState;
        this.stateIcon = new SimpleStringProperty(newState.getIcon());
    }

    public void setState(BoxState state){
        this.state = state;
        stateIcon.setValue(state.getIcon());
    }

    public StringProperty stateIconProperty() { return stateIcon; }

    public final String getSateIcon() { return stateIcon.getValue(); }

    public final void setStateIcon(String icon) {
        stateIcon.setValue(icon);
    }

    public void populateFromBox(Box box) {
        this.setState(box.getState());
    }

    public enum BoxState {
        UNMARKED{

            @Override
            public BoxState update() {
                return BoxState.FALSE;
            }

            @Override
            public BoxState onEliminated() {
                return BoxState.FALSE_BY_ELIMINATION;
            }

            @Override
            public String getIcon() { return "       "; }
        },
        FALSE {

            @Override
            public BoxState update() {
                // TODO: need to send out the eliminations as the state is updated
                return BoxState.TRUE;
            }

            @Override
            public String getIcon() { return "❌";}
        },
        TRUE {

            @Override
            public BoxState update() {
                //TODO need to send out the unelimination event as state is updated
                return BoxState.UNSURE;
            }

            @Override
            public String getIcon() { return "✅"; }
        },
        UNSURE {

            @Override
            public BoxState update() {
                return BoxState.UNMARKED;
            }

            @Override
            public String getIcon() { return "❓"; }
        },
        FALSE_BY_ELIMINATION {

            @Override
            public BoxState onUneliminated() {
                return BoxState.UNMARKED;
            }

            @Override
            public String getIcon() { return "ⅹ"; }
        };

        public BoxState update() {
            return this;
        }
        public BoxState onEliminated() {
            return this;
        }
        public BoxState onUneliminated() {
            return this;
        }
        public abstract String getIcon();
    }

    public Box clone() {
        return new Box(state);
    }

    public void update() {
        state = state.update();
        setStateIcon(state.getIcon());
    }

}


