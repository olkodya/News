package com.example.news;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private Label label;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea textArea;


    @FXML
    void newButtonClick(ActionEvent event){
       // newButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MyApplication.class.getResource("newWindow.fxml"));
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

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbH = new DatabaseHandler();
        ResultSet resultSet = dbH.getNews();
        while (resultSet.next()) {
            String name = resultSet.getString("title");
            listView.getItems().add(name);
        }

        listView.setOnMouseClicked(e->{
            label.setText(listView.getSelectionModel().getSelectedItem().trim());
            ResultSet resultSet2 = null;
            try {
                resultSet2 = dbH.getNewsBody(listView.getSelectionModel().getSelectedItem().trim());
                System.out.println(listView.getSelectionModel().getSelectedItem());
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            String body;
            try {
                assert resultSet2 != null;
                if(resultSet2.next()) {
                    System.out.println( resultSet2);
                    body = resultSet2.getString("body");
                    System.out.println(body);
                    textArea.setText(body);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            // System.out.println(body);

        });

    }

}
