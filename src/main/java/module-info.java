module com.example.abboud_tikinas {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.abboud_tikinas to javafx.fxml;
    exports com.example.abboud_tikinas;
}