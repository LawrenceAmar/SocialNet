module com.example.socialnet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.socialnet to javafx.fxml;
    exports com.example.socialnet;
}