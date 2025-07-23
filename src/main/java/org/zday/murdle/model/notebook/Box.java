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

    public Box(BoxState boxState) {
        state = boxState;
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
        },
        FALSE {

            @Override
            public BoxState update() {
                // need to send out the eliminations as the state is updated
                return BoxState.TRUE;
            }
        },
        TRUE {

            @Override
            public BoxState update() {
                // need to send out the unelimination event as state is updated
                return BoxState.UNSURE;
            }
        },
        UNSURE {

            @Override
            public BoxState update() {
                return BoxState.UNMARKED;
            }

            //TODO: Check how this responds to elimination
            //The existence of this state might result in needing a "previous state" property
        },
        FALSE_BY_ELIMINATION {

            @Override
            public BoxState onUneliminated() {
                return BoxState.UNMARKED;
            }

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
    }

    public Box clone() {
        return new Box(state);
    }
}


