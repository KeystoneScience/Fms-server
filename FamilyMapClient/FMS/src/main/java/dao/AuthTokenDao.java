package dao;

import model.AuthToken;
import model.Event;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * the AuthToken data access class. This class is responsible for
 * performing operations on the AuthToken sql table.
 */
public class AuthTokenDao {

    private Connection connection;

    public AuthTokenDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * inserts an AuthToken into the database associated to a User
     * @param au authentication token
     * @throws SQLException input output SQL exception
     */
    public void insertAuthToken(AuthToken au) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into AuthToken (token, Username) values (?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, au.getToken());
            stmt.setString(2, au.getUsername());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted AuthToken failed" + au.getToken());
            }
            System.out.println("Inserted AuthToken " + au.getToken());


        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * prints all AuthTokens in the database
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    private void printAuthTokens(String prefix, List<AuthToken> books) {
        for(AuthToken book : books) {
            System.out.println(prefix + book.getToken());
        }

    }

    /**
     * removes an AuthToken from the database
     * @param au authentication token
     * @throws SQLException input output SQL exception
     */
    private void removeAuthToken(AuthToken au) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql; //= "delete from User";
            //stmt = connection.prepareStatement(sql);

            //int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from AuthToken where name = '"+ au.getToken() + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted AuthToken: %s\n", au.getToken());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    public AuthToken find(String ID) throws DataAccessException {
        AuthToken at;
        ResultSet rs = null;
        String sql = "SELECT * FROM authToken WHERE token = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                at = new AuthToken(rs.getString("token"),rs.getString("username"));
                return at;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding auth token");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }



}

