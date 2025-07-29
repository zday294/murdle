package org.zday.murdle.model.notebook;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Box {

    @Getter
    private BoxState state;

    private StringProperty stateIcon;

    private List<EliminationListener> eliminationListeners = new ArrayList<>();

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

    public void addEliminationListener(EliminationListener listener) {
        eliminationListeners.add(listener);
    }

    public StringProperty stateIconProperty() { return stateIcon; }
    public final String getSateIcon() { return stateIcon.getValue(); }
    public final void setStateIcon(String icon) {
        stateIcon.setValue(icon);
    }

    public void populateFromBox(Box box) {
        this.setState(box.getState());
    }

    public Box clone() {
        return new Box(state);
    }

    public void update() {
        state = state.update(eliminationListeners);

        switch (state) {
            case TRUE -> eliminationListeners.forEach(EliminationListener::eliminate);
            case UNSURE -> eliminationListeners.forEach(EliminationListener::uneliminate);
        }

        setStateIcon(state.getIcon());
    }

    public void eliminate() {
        state = state.onEliminated();
        stateIcon.setValue(state.getIcon());

    }

    public void uneliminate() {
        state = state.onUneliminated(eliminationListeners);
        stateIcon.setValue(state.getIcon());
    }

    public enum BoxState {
        UNMARKED{

            @Override
            public BoxState update(List<EliminationListener> eliminationListeners) {
                return BoxState.FALSE;
            }

            @Override
            public BoxState onEliminated() {
                return BoxState.FALSE_BY_ELIMINATION;
            }

            @Override
            public String getIcon() { return ""; }
        },
        FALSE {
            @Override
            public BoxState update(List<EliminationListener> eliminationListeners) {
                return checkEliminable(eliminationListeners) ? BoxState.FALSE : BoxState.TRUE;
            }

            @Override
            public String getIcon() { return "❌";}
        },
        TRUE {

            @Override
            public BoxState update(List<EliminationListener> eliminationListeners) {
                return BoxState.UNSURE;
            }

            @Override
            public String getIcon() { return "✅"; }
        },
        UNSURE {

            @Override
            public BoxState update(List<EliminationListener> eliminationListeners) {
                return checkEliminable(eliminationListeners) ? BoxState.FALSE_BY_ELIMINATION : BoxState.UNMARKED; //good
            }

            @Override
            public String getIcon() { return "❓"; }
        },
        FALSE_BY_ELIMINATION {

            @Override
            public BoxState onUneliminated(List<EliminationListener> eliminationListeners) {
                return !checkEliminable(eliminationListeners) ? BoxState.UNMARKED : BoxState.FALSE_BY_ELIMINATION;
            }

            @Override
            public String getIcon() { return "ⅹ"; }
        };

        public BoxState update(List<EliminationListener> eliminationListeners) {
            return this;
        }
        public BoxState onEliminated() {
            return this;
        }
        public BoxState onUneliminated(List<EliminationListener> eliminationListeners) {
            return this;
        }
        public abstract String getIcon();

        private static boolean checkEliminable(List<EliminationListener> eliminationListeners) {
            return eliminationListeners.stream().anyMatch(EliminationListener::checkEliminable);
        }
    }

}


