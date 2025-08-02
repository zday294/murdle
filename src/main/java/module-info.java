module org.zday.murdle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires static lombok;
    requires com.fasterxml.jackson.databind;

    opens org.zday.murdle to javafx.fxml;
    exports org.zday.murdle;
    exports org.zday.murdle.view.component;
    opens org.zday.murdle.view.component to javafx.fxml;
    exports org.zday.murdle.controller;
    opens org.zday.murdle.controller to javafx.fxml;
    exports org.zday.murdle.model.game.murdercase;
    opens org.zday.murdle.model.game.murdercase to com.fasterxml.jackson.databind;
    exports org.zday.murdle.model.game.murdercase.suspect;
    opens org.zday.murdle.model.game.murdercase.suspect to com.fasterxml.jackson.databind;
    exports org.zday.murdle.model.game.murdercase.clues;
    opens org.zday.murdle.model.game.murdercase.clues to com.fasterxml.jackson.databind;
    exports org.zday.murdle.util;
    opens org.zday.murdle.util to com.fasterxml.jackson.databind;
    exports org.zday.murdle.model.level;
    opens org.zday.murdle.model.level to com.fasterxml.jackson.databind;

}