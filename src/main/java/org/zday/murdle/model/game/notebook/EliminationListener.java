package org.zday.murdle.model.game.notebook;

public interface EliminationListener {
    void eliminate();
    void uneliminate();
    boolean checkEliminable();
}
