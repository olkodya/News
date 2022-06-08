package com.example.news;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class DatabaseHandler extends Configs{
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpUser (String UserName, String password, String role) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_LOGIN + "," +Const.USER_PASSWORD + "," +Const.USER_ROLE + ")" + "VALUES(?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, UserName);
        prSt.setString(2, password);
        prSt.setString(3, role);
        prSt.executeUpdate();
    }

    public ResultSet getUser(String UserName, String password) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD +"=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, UserName);
        prSt.setString(2, password);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getLogin(String UserName) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, UserName);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public void createNews(News news) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE2 + "(" + Const.NEWS_TITLE + "," +Const.NEWS_BODY  + ")" + "VALUES(?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, news.getTitle());
        prSt.setString(2, news.getText());
        prSt.executeUpdate();
    }

    public ResultSet getNews() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE2;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
//        prSt.setString(1, news.getTitle());
//        prSt.setString(2, news.getText());
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getNewsBody(String title) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE2 + " WHERE " + Const.NEWS_TITLE + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, title);
//        prSt.setString(2, news.getText());
        resSet = prSt.executeQuery();
        return resSet;
    }

    public void DeleteNews(String title) throws SQLException, ClassNotFoundException {
        String select = "DELETE FROM " + Const.USER_TABLE2 + " WHERE " + Const.NEWS_TITLE + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, title);
//        prSt.setString(2, news.getText());
        prSt.executeUpdate();
    }

    public void updateNews(int id, String title, String body) throws SQLException, ClassNotFoundException {
        String select = "UPDATE " + Const.USER_TABLE2 +  " SET " +  Const.NEWS_TITLE + "=?, "+  Const.NEWS_BODY + "=?"  + " WHERE " + Const.NEWS_ID + "=?"  ;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        /*prSt.setString(1, title);*/
        prSt.setString(1, title);
        prSt.setString(2, body);
        prSt.setInt(3, id);
        prSt.executeUpdate();
    }

    public void addComment (int id, String login, String comment) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE3 + "(" + Const.NEWS_ID + "," +Const.COMM_LOG + "," +Const.COMM_TEXT + ")" + "VALUES(?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setInt(1, id);
        prSt.setString(2, login);
        prSt.setString(3, comment);
        prSt.executeUpdate();
    }

    public ResultSet getComment(int id) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE3 + " WHERE " + Const.NEWS_ID + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, id);
//        prSt.setString(2, news.getText());
        resSet = prSt.executeQuery();
        return resSet;
    }
}



