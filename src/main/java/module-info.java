module com.example.knightstour {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.knightstour2023 to javafx.fxml;
    exports com.example.knightstour2023;
}