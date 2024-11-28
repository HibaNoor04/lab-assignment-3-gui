module com.example.assignment3lab {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment3lab to javafx.fxml;
    exports com.example.assignment3lab;
}