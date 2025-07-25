package org.zday.murdle.model.notebook;

public interface EliminationListener {
    void eliminate();
    void uneliminate();
    boolean checkUneliminable();
    boolean checkEliminable();
}
