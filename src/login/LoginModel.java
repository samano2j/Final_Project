package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;

public class LoginModel {
    
    Connection conn = null;

    public LoginModel(){
        this.conn = dbConnection.getConnection();

        if(this.conn == null){
            System.exit(1);
        }

    }

    public boolean isDatabaseConnected(){
        return this.conn != null;
    }

    public boolean isLogin(String username, String password){
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM login_tbl WHERE username = ? AND password = ?";

        try {
            statement = this.conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            
            resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String checkStatus(String username, String password) {
        String query = "SELECT status FROM login_tbl WHERE username = ? AND password = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "student";
    }
    
}
