package com.example.news;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class  NewWindowController {

    @FXML
    private Button createButton;

    @FXML
    private TextArea text;

    @FXML
    private TextField titleTextField;

    @FXML
    void createButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {


//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User name or Password is empty!☺\n", ButtonType.YES);
        String title =  titleTextField.getText();
        String body = text.getText();
        Client.addNews(title, body);
        if(Objects.equals(Client.getResponse(), "SUCCESS"))
            createButton.getScene().getWindow().hide();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        text.setWrapText(true);
    }
}
