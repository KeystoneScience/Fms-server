package dao;

import model.user;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
We need to:
create, get user names,
clear users,

crud
create
remove
update
destroy


 */

/**
 * the user data access class. This class is responsible for
 * performing operations on the user sql table.
 */
public class userDao {

    private Connection connection;


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * inserts a new user into the user sql data base
     * @param us user model
     * @throws SQLException input/output SQL exception
     */
    private void insertUser(user us) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into user (id, password, email, first_name, last_name, gender, person_id) values (?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, us.getId());
            stmt.setString(2, us.getPassword());
            stmt.setString(3, us.getEmail());
            stmt.setString(4, us.getFirst_name());
            stmt.setString(5, us.getLast_name());
            stmt.setString(6, us.getGender());
            stmt.setString(7, us.getPerson_id());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted user failed" + us.getId());
            }
            System.out.println("Inserted user " + us.getId());


        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * prints every user's ID in the data base.
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    private void printUsers(String prefix, List<user> books) {
        for(user book : books) {
            System.out.println(prefix + book.getId());
        }

    }

//    public void resetTable()  { //Also clears tables
//        try {
//            Statement stmt = null;
//            try {
//                stmt = connection.createStatement();
//
//                stmt.executeUpdate("drop table if exists user");
//                stmt.executeUpdate("create table user (id VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, first_name VARCHAR(255) NOT NULL, " +
//                        "last_name VARCHAR(255) NOT NULL, gender VARCHAR (1) NOT NULL, person_id VARCHAR(255) NOT NULL, CONSTRAINT user_info UNIQUE (username))");
//            }
//            finally {
//                if (stmt != null) {
//                    stmt.close();
//                    stmt = null;
//                }
//            }
//        }
//        catch (SQLException e) {
//
//        }
//    }


    /**
     * removes a user specified
     * @param us model user
     * @throws SQLException input output SQL exception
     */
    private void removeUser(user us) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql; //= "delete from user";
            //stmt = connection.prepareStatement(sql);

            //int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from user where name = '"+ us.getId()+"'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted user: %s\n", us.getId());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * returns a list of the users in the database
     * @return returns a list of the users
     * @throws SQLException exception for read/write
     */
    private List<user> getUsers() throws SQLException {

        List<user> users = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select id, password, email, first_name, last_name, gender, person_id from user";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String id = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String first_name = rs.getString(4);
                String last_name = rs.getString(5);
                String gender = rs.getString(6);
                String person_id = rs.getString(7);

                users.add(new user(id, password, email, first_name, last_name, gender, person_id
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

        return users;
    }

    /**
     * updates a user's information
     * @param user new user information
     * @param userIdToUpdate user to make changes to
     * @throws SQLException Exception for SQL read/write
     */
    private void updateUser(user user,String userIdToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update user " +
                    "set id = ?, password = ?, email = ?, first_name = ?, last_name = ?," +
                    " gender = ?, person_id = ?"+
                    "where id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirst_name());
            stmt.setString(5, user.getLast_name());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPerson_id());
            stmt.setString(8,userIdToUpdate);
            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated user " + user.getId());
            } else {
                System.out.println("Failed to update user " + userIdToUpdate);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
}
