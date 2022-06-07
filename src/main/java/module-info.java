module com.example.news {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.news to javafx.fxml;
    exports com.example.news;
}