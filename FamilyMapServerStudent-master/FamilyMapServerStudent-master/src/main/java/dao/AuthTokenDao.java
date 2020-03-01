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
    private void insertAuthToken(AuthToken au) throws SQLException {
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


    /**
     * gets a list of all the AuthTokens
     * @return returns a list of authentication tokens
     * @throws SQLException input output SQL exception
     */
    private List<AuthToken> getAuthTokens() throws SQLException {

        List<AuthToken> auths = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select token, Username from AuthToken";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String token = rs.getString(1);
                String Username = rs.getString(2);

                auths.add(new AuthToken(token, Username
                ));

            }
        } finally {
            if(rs != null) {
                rs.close();
            }

            if(stmt != null) {
                stmt.close();
            }
        }

        return auths;
    }

    /**
     * updates the info of an AuthToken
     * @param au authentication token
     * @param tokenToUpdate token that should be updated
     * @throws SQLException input output SQL exception
     */
    private void updateAuthToken(AuthToken au,String tokenToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update AuthToken " +
                    "set token = ?, Username = ?"+
                    "where token = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, au.getToken());
            stmt.setString(2, au.getUsername());
            stmt.setString(3, au.getToken());

            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated AuthToken " + au.getToken());
            } else {
                System.out.println("Failed to update AuthToken " + tokenToUpdate);
            }
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

