package com.example.news;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;

public class editWindowController {

    @FXML
    private Button createButton;

    @FXML
    private TextArea text;

    @FXML
    private TextField titleTextField;

    private String startTitle;

    @FXML
    void createButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {

       Client.editNews(startTitle,titleTextField.getText(), text.getText() );
       Client.getResponse();
        createButton.getScene().getWindow().hide();



    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        text.setWrapText(true);

    }

    public void showNew(String text1, String text2) {
        text.setText(text2);
        startTitle = text1;
        titleTextField.setText(text1);
    }

}
