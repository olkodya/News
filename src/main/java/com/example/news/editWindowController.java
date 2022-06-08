package com.example.news;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        System.out.println(startTitle);
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resultSet2 = null;
        try {
            resultSet2 = dbHandler.getNewsBody(startTitle);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        int id = 0;
        try {
            assert resultSet2 != null;
            if (resultSet2.next()) {
                id = resultSet2.getInt("idnews");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbHandler.updateNews(id, titleTextField.getText(), text.getText());
        createButton.getScene().getWindow().hide();



    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {


    }

    public void showNew(String text1, String text2) {
        text.setText(text2);
        startTitle = text1;
        titleTextField.setText(text1);
    }

}
