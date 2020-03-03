package dao;

import model.Person;
import model.User;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
We need to:
create, get User names,
clear Users,

crud
create
remove
update
destroy


 */

/**
 * the User data access class. This class is responsible for
 * performing operations on the User sql table.
 */
public class UserDao {

    private Connection connection;

    public UserDao(Connection conn) {
        connection = conn;
    }

    public UserDao() {

    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    /**
     * clear the data table
     * @throws SQLException
     */
    public void clearTable() throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql;
            sql = "delete from User";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted User table");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

    }

    /**
     * inserts a new User into the User sql data base
     * @param us User model
     * @throws SQLException input/output SQL exception
     */
    public void insertUser(User us) throws SQLException, DataAccessException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into User (id, password, email, first_name, last_name, gender, Person_id) values (?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, us.getId());
            stmt.setString(2, us.getPassword());
            stmt.setString(3, us.getEmail());
            stmt.setString(4, us.getFirst_name());
            stmt.setString(5, us.getLast_name());
            stmt.setString(6, us.getGender());
            stmt.setString(7, us.getPerson_id());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted User failed" + us.getId());
            }
            System.out.println("Inserted User " + us.getId());


        }catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Username is already taken");
        }

        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * prints every User's ID in the data base.
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    public void printUsers(String prefix, List<User> books) {
        for(User book : books) {
            System.out.println(prefix + book.getId());
        }

    }


    public void removeUser(User user) throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                String sql = "delete from user where id = '" + user.getId() + "'";
                stmt.executeUpdate(sql);


            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("delete user from database failed");
        }
    }

//    public void removeUser(String ID) throws DataAccessException {
//        String sql = "DELETE FROM user WHERE id = ?;";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, ID);
//            stmt.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while deleting user");
//        }
//    }

    /**
     * removes a User specified
     * @param us model User
     * @throws SQLException input output SQL exception
     */
//    public void removeUser(User us) throws SQLException {
//        PreparedStatement stmt = null;
//        try {
//            String sql; //= "delete from User";
//            //stmt = connection.prepareStatement(sql);
//
//            //int count = stmt.executeUpdate();
//
//            // Reset the auto-increment counter so new books start over with an id of 1
//            sql = "delete from User where name = '"+ us.getId()+"'";
//            stmt = connection.prepareStatement(sql);
//            stmt.executeUpdate();
//            System.out.printf("Deleted User: %s\n", us.getId());
//        } finally {
//            if (stmt != null) {
//                stmt.close();
//            }
//        }
//    }


    /**
     * returns a list of the Users in the database
     * @return returns a list of the Users
     * @throws SQLException exception for read/write
     */
    public List<User> getUsers() throws SQLException {

        List<User> Users = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select id, password, email, first_name, last_name, gender, Person_id from User";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String id = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String first_name = rs.getString(4);
                String last_name = rs.getString(5);
                String gender = rs.getString(6);
                String Person_id = rs.getString(7);

                Users.add(new User(id, password, email, first_name, last_name, gender, Person_id
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

        return Users;
    }

    /**
     * updates a User's information
     * @param User new User information
     * @param UserIdToUpdate User to make changes to
     * @throws SQLException Exception for SQL read/write
     */
    public void updateUser(User User,String UserIdToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update User " +
                    "set id = ?, password = ?, email = ?, first_name = ?, last_name = ?," +
                    " gender = ?, Person_id = ?"+
                    "where id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, User.getId());
            stmt.setString(2, User.getPassword());
            stmt.setString(3, User.getEmail());
            stmt.setString(4, User.getFirst_name());
            stmt.setString(5, User.getLast_name());
            stmt.setString(6, User.getGender());
            stmt.setString(7, User.getPerson_id());
            stmt.setString(8,UserIdToUpdate);
            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated User " + User.getId());
            } else {
                System.out.println("Failed to update User " + UserIdToUpdate);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * Finds a object in the database with a matching ID, returns it.
     * @param ID
     * @return
     * @throws DataAccessException
     */
    public User find(String ID) throws DataAccessException {
        User User;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                User = new User(rs.getString("id"), rs.getString("password"),rs.getString("email"),
                        rs.getString("first_name"), rs.getString("last_name"),rs.getString("gender"), rs.getString("Person_id"));
                return User;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding Person");
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


    /**
     * Finds a object in the database with a matching ID, returns it.
     * @param ID
     * @return
     * @throws DataAccessException
     */
    public User findUser(String ID) throws DataAccessException {
        User User;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                User = new User(rs.getString("id"), rs.getString("password"),rs.getString("email"),
                        rs.getString("first_name"), rs.getString("last_name"),rs.getString("gender"), rs.getString("Person_id"));
                return User;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding Person");
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
