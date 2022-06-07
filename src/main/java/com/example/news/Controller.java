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

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignUpButton;

    @FXML
    private Button guestButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void SignUpButtonClick(ActionEvent event){
        SignUpButton.getScene().getWindow().hide();
        OpenNewScene("signUp.fxml");
    }

    @FXML
    void guestButtonClick(ActionEvent event){
        guestButton.getScene().getWindow().hide();
        OpenNewScene("mainWindow.fxml");
    }

    @FXML
    void loginButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        String loginText = loginTextField.getText().trim();
        String loginPassword = passwordField.getText().trim();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User name or Password is empty!☺\n", ButtonType.YES);
        if(!loginText.equals("")&&!loginPassword.equals("")){
            if(loginUser(loginText, loginPassword)) {
                loginButton.getScene().getWindow().hide();
                OpenNewScene("mainWindow.fxml");
            }
        }
        else{
            alert.showAndWait();
        }
    }

    private boolean loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getUser(loginText, loginPassword);
        int counter = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User with this login and password not found!☺\n", ButtonType.YES);
        while(result.next()){
            counter++;
        }
        if(counter < 1){
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void OpenNewScene(String window) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MyApplication.class.getResource(window));
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

//    @FXML
//    void initialize() {
//        SignUpButton.setOnAction(event->{
//
//        });

//    }

}
