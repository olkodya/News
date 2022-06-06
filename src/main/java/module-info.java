module com.example.news {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.news to javafx.fxml;
    exports com.example.news;
}