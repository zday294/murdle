package org.zday.murdle.model.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Box {

    private BoxState state;

    public Box() {
        state = BoxState.UNMARKED;
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
    }

}


