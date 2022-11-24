package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbUtil.dbConnection;

public class SingleBookModel {
    Connection conn = null;
    SingleBookModel singleBookModel = null;

    public SingleBookModel() {
        this.conn = dbConnection.getConnection();

        if(this.conn == null){
            System.exit(0);
        }
    }

    //delete
    public void delete(String bookTitle) {

        String query = "DELETE FROM book_tbl WHERE title = ?";
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, bookTitle);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //edit
    public void edit(String origTitle, String origImage, String bookTitle, String bookAuthor, String bookYear, String bookDescription, String bookImage) {
        String sql = "UPDATE book_tbl SET title = ?, author = ?, year = ?, description = ?, image = ? WHERE title = ? AND image = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, bookTitle);
            statement.setString(2, bookAuthor);
            statement.setInt(3, Integer.parseInt(bookYear));
            statement.setString(4, bookDescription);
            statement.setString(5, bookImage);
            statement.setString(6, origTitle);
            statement.setString(7, origImage);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //request
    public void request(String user, String title, String image){
        String sql = "UPDATE book_tbl SET availability = ?, status = ?, student = ? WHERE title = ? AND image = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, "unavailable");
            statement.setString(2, "pending");
            statement.setString(3, user);
            statement.setString(4, title);
            statement.setString(5, image);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //approve
    public void approve(String title, String image) {
        String sql = "UPDATE book_tbl SET status = ? WHERE title = ? AND image = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, "pickup");
            statement.setString(2, title);
            statement.setString(3, image);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //deny
    public void deny(String title, String image) {
        String sql = "UPDATE book_tbl SET availability = ?, status = ?, student = ? WHERE title = ? AND image = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, "available");
            statement.setString(2, "request");
            statement.setString(3, null);
            statement.setString(4, title);
            statement.setString(5, image);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //taken
    public void taken(String title, String image) {
        String sql = "UPDATE book_tbl SET status = ? WHERE title = ? AND image = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, "return");
            statement.setString(2, title);
            statement.setString(3, image);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //available
    public void available(String title, String image) {
        String sql = "UPDATE book_tbl SET availability = ?, status = ?, student = ? WHERE title = ? AND image = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, "available");
            statement.setString(2, "request");
            statement.setString(3, null);
            statement.setString(4, title);
            statement.setString(5, image);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
