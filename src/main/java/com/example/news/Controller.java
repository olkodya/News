package com.example.news;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
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
        //OpenNewScene();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MyApplication.class.getResource("signUp.fxml"));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
       // MainWindowController controller = loader.getController();
        //controller.showInfo(role);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    void guestButtonClick(ActionEvent event){
        guestButton.getScene().getWindow().hide();
        OpenNewScene("mainWindow.fxml", "Guest");
    }

    @FXML
    void loginButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        String loginText = loginTextField.getText().trim();
        String loginPassword = passwordField.getText().trim();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User name or Password is empty!☺\n", ButtonType.YES);
        if(!loginText.equals("")&&!loginPassword.equals("")){
            if(loginUser(loginText, loginPassword)) {
                loginButton.getScene().getWindow().hide();
                OpenNewScene("mainWindow.fxml", getRole(loginText));
            }
        }
        else{
            alert.showAndWait();
        }
    }

    private boolean loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        Client.loginUser(loginText, loginPassword);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User with this login and password not found!☺\n", ButtonType.YES);
        String response = Client.getResponse();
        if (Objects.equals(response, "correct")) {
            return true;
        } else {
            alert.showAndWait();
            return false;
        }
    }

    public void OpenNewScene(String window, String role) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MyApplication.class.getResource(window));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        MainWindowController controller = loader.getController();
        controller.showInfo(role, loginTextField.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    String getRole(String login){
        Client.Role(login);
        return Client.getResponse();
    }
//    @FXML
//    void initialize() {
//        SignUpButton.setOnAction(event->{
//
//        });

//    }

}
