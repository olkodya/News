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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User name or Password is empty!\n", ButtonType.YES);
            String role = null;
            if(AdminRadioButton.isSelected()){
                role = "admin";
            }
            if(RadioButton.isSelected()){
                role = "user";
            }
            if(!signUpLogin.getText().trim().equals("")&&!SignUpPassword.getText().trim().equals("")){
                if(checkUser(signUpLogin.getText().trim())) {
                    Client.SignUp(signUpLogin.getText(), SignUpPassword.getText(), role);

                    if (Objects.equals(Client.getResponse(), "SUCCESS")) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(MyApplication.class.getResource("mainWindow.fxml"));
                        try{
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        MainWindowController controller = loader.getController();
                        controller.showInfo(role, signUpLogin.getText());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                    }

                }
            }
            else{
                alert.showAndWait();
            }
    }

    private boolean checkUser(String loginText) throws SQLException, ClassNotFoundException {
        Client.checkUser(loginText);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User with this login already exist!\n", ButtonType.YES);
        String resp = Client.getResponse();
        System.out.println(resp);
        if(Objects.equals(resp, "0")){
            alert.showAndWait();
            return false;
        }
       return true;
    }

    @FXML
    void initialize() {


    }

}
