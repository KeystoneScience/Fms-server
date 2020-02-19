package dao;

import model.authToken;
import model.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * the authToken data access class. This class is responsible for
 * performing operations on the authToken sql table.
 */
public class authTokenDao {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * inserts an authToken into the database associated to a user
     * @param au authentication token
     * @throws SQLException input output SQL exception
     */
    private void insertAuthToken(authToken au) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into authToken (token, username) values (?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, au.getToken());
            stmt.setString(2, au.getUsername());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted authToken failed" + au.getToken());
            }
            System.out.println("Inserted authToken " + au.getToken());


        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * prints all authTokens in the database
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    private void printAuthTokens(String prefix, List<authToken> books) {
        for(authToken book : books) {
            System.out.println(prefix + book.getToken());
        }

    }

    /**
     * removes an authToken from the database
     * @param au authentication token
     * @throws SQLException input output SQL exception
     */
    private void removeAuthToken(authToken au) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql; //= "delete from user";
            //stmt = connection.prepareStatement(sql);

            //int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from authToken where name = '"+ au.getToken() + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted authToken: %s\n", au.getToken());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * gets a list of all the authTokens
     * @return returns a list of authentication tokens
     * @throws SQLException input output SQL exception
     */
    private List<authToken> getAuthTokens() throws SQLException {

        List<authToken> auths = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select token, username from authToken";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String token = rs.getString(1);
                String username = rs.getString(2);

                auths.add(new authToken(token, username
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
     * updates the info of an authToken
     * @param au authentication token
     * @param tokenToUpdate token that should be updated
     * @throws SQLException input output SQL exception
     */
    private void updateAuthToken(authToken au,String tokenToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update authToken " +
                    "set token = ?, username = ?"+
                    "where token = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, au.getToken());
            stmt.setString(2, au.getUsername());
            stmt.setString(3, au.getToken());

            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated authToken " + au.getToken());
            } else {
                System.out.println("Failed to update authToken " + tokenToUpdate);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


}
