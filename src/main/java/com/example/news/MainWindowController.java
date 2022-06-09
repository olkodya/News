package com.example.news;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.text.Font;
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
    private Label roleLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private TextArea sendComment;

    @FXML
    private TextArea commentSection;
    @FXML
    private Button SendButton;



    @FXML
    void newButtonClick(ActionEvent event){
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
    void deleteButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        // newButton.getScene().getWindow().hide();
        Client.delNews(label.getText());
        Client.getResponse();
        label.setText("Label");
        textArea.setText("News");
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(MyApplication.class.getResource("newWindow.fxml"));
//        try{
//            loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Parent root = loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.showAndWait();
    }

    @FXML
    void editButtonClick(ActionEvent event){
        // newButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MyApplication.class.getResource("editWindow.fxml"));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editWindowController controller = loader.getController();
        controller.showNew(label.getText(), textArea.getText());
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    void sendButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbH = new DatabaseHandler();
        dbH.addComment(getId(), loginLabel.getText(), sendComment.getText());
        ResultSet rst = dbH.getComment(getId());
        try {
            assert rst != null;
            while(rst.next()) {
                commentSection.appendText("User: " + rst.getString("login")+"\n");
                commentSection.appendText("\t " +rst.getString("comment")+"\n\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        sendComment.clear();

    }

    int getId() throws SQLException, ClassNotFoundException {
        Client.getID(label.getText());
        return Integer.parseInt(Client.getResponse());
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        newButton.setVisible(false);
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        SendButton.setVisible(false);
        sendComment.setVisible(false);
        textArea.setWrapText(true);
        commentSection.setWrapText(true);
        sendComment.setWrapText(true);
        timer = new Timer();
        listView.setStyle("-fx-font: 14pt Arial");
        startCycle();


    }

    private boolean count = false;

    void setInvisible() {
        count = true;
        System.out.println(roleLabel.getText());
        if(Objects.equals(roleLabel.getText(), "admin")){
            newButton.setVisible(true);
            editButton.setVisible(true);
            deleteButton.setVisible(true);
        }
        if(Objects.equals(roleLabel.getText(), "admin")||Objects.equals(roleLabel.getText(), "user")){
            sendComment.setVisible(true);
            SendButton.setVisible(true);
        }
    }


    private static Timer timer;
    private void startCycle() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        update();
                        if (!count) setInvisible();
                    } catch (IOException | SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 0, 1000);
    }

    private void update() throws IOException, SQLException, ClassNotFoundException {

        DatabaseHandler dbH = new DatabaseHandler();
        ResultSet resultSet = dbH.getNews();
        listView.getItems().clear();
        while (resultSet.next()) {
            String name = resultSet.getString("title");
            listView.getItems().add(name);
        }

        listView.setOnMouseClicked(e->{
            label.setText(listView.getSelectionModel().getSelectedItem());
            commentSection.clear();
            ResultSet resultSet2 = null;
            try {
                //listView.setStyle("-fx-control-inner-background: hotpink");
                listView.setStyle("-fx-font: 14pt Arial");
                resultSet2 = dbH.getNewsBody(listView.getSelectionModel().getSelectedItem());
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
                    System.out.println(roleLabel.getText());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //DatabaseHandler dbH = new DatabaseHandler();
            ResultSet rst = null;
            try {
                rst = dbH.getComment(getId());
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                assert rst != null;
                while(rst.next()) {
                    commentSection.appendText("User: " + rst.getString("login")+"\n");
                    commentSection.appendText("\t " +rst.getString("comment")+"\n\n");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        Stage stage = (Stage)label.getScene().getWindow();
        stage.setOnCloseRequest(windowEvent -> {
            timer.cancel();
        });
    }

    public void  showInfo(String role, String name){
        roleLabel.setText(role);
        loginLabel.setText(name);
    }
}
