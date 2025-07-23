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
}