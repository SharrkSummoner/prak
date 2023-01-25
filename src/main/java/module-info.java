module com.example.prak {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.prak to javafx.fxml;
    exports com.example.prak;
}