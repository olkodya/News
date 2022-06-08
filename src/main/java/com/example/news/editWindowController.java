package com.example.news;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Timer;

public class editWindowController {

    @FXML
    private Button createButton;

    @FXML
    private TextArea text;

    @FXML
    private TextField titleTextField;

    @FXML
    void createButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

    }

    public void showNew(String text1, String text2) {
        text.setText(text2);
        titleTextField.setText(text1);



    }





}
