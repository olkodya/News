package com.example.news;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton AdminRadioButton;

    @FXML
    private RadioButton RadioButton;

    @FXML
    private Button SignUpLoginButton;

    @FXML
    private PasswordField SignUpPassword;

    @FXML
    private TextField signUpLogin;

    @FXML
    private ToggleGroup tgl1;

    @FXML
    void SignUpLoginButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
            DatabaseHandler dbHandler = new DatabaseHandler();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User name or Password is empty!☺\n", ButtonType.YES);
            String role = null;
            if(AdminRadioButton.isSelected()){
                role = "admin";
            }
            if(RadioButton.isSelected()){
                role = "user";
            }
            if(!signUpLogin.getText().trim().equals("")&&!SignUpPassword.getText().trim().equals("")){
                if(checkUser(signUpLogin.getText().trim())) {
                    dbHandler.signUpUser(signUpLogin.getText().trim(), SignUpPassword.getText().trim(), role);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MyApplication.class.getResource("mainWindow.fxml"));
                    try{
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                }
            }
            else{
                alert.showAndWait();
            }
    }

    private boolean checkUser(String loginText) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getLogin(loginText);
        int counter = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User with this login already exist!☺\n", ButtonType.YES);
        while(result.next()){
            counter++;
        }
        if(counter > 0){
            alert.showAndWait();
            return false;
        }
       return true;
    }

    @FXML
    void initialize() {


    }

}
