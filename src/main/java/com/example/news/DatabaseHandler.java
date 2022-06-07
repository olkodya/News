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


}


