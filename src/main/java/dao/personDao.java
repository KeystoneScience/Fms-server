package dao;

import model.person;
import model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * the person data access class. This class is responsible for
 * performing operations on the person sql table.
 */
public class personDao {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * inserts a person into the data base
     * @param pe person model
     * @throws SQLException input output SQL exception
     */
    private void insertPerson(person pe) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into person (person_id, associated_username, first_name, last_name, gender, father_id, mother_id, spouse_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, pe.getPerson_id());
            stmt.setString(2, pe.getAssociated_username());
            stmt.setString(3, pe.getFirst_name());
            stmt.setString(4, pe.getLast_name());
            stmt.setString(5, pe.getGender());
            stmt.setString(6, pe.getFather_id());
            stmt.setString(7, pe.getMother_id());
            stmt.setString(8,pe.getSpouse_id());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted user failed" + pe.getPerson_id());
            }
            System.out.println("Inserted user " + pe.getPerson_id());


        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * prints all persons in the database
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    private void printPersons(String prefix, List<person> books) {
        for(person book : books) {
            System.out.println(prefix + book.getPerson_id());
        }

    }

    /**
     * removes a person from the database
     * @param ps person model
     * @throws SQLException input output SQL exception
     */
    private void removePerson(person ps) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql; //= "delete from user";
            //stmt = connection.prepareStatement(sql);

            //int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from person where name = '"+ ps.getPerson_id() + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted person: %s\n", ps.getPerson_id());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * returns a list of all people in the database
     * @return return list of persons
     * @throws SQLException input output SQL exception
     */
    private List<person> getPersons() throws SQLException {

        List<person> persons = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select person_id, associated_username, first_name, last_name, gender, father_id, mother_id, spouse_id from person";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String person_id = rs.getString(1);
                String associated_username = rs.getString(2);
                String first_name = rs.getString(3);
                String last_name = rs.getString(4);
                String gender = rs.getString(5);
                String father_id = rs.getString(6);
                String mother_id = rs.getString(7);
                String spouse_id = rs.getString(8);

                persons.add(new person(person_id,associated_username,first_name,last_name,gender,father_id,mother_id,spouse_id
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

        return persons;
    }

    /**
     * updates the information of a person in the database.
     * @param ps person model object
     * @param personIdToUpdate Unique identifier for the personToUpdate
     * @throws SQLException input output SQL exception
     */
    private void updatePerson(person ps,String personIdToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update person " +
                    "set person_id = ?, associated_username = ?, first_name = ?, last_name = ?," +
                    " gender = ?, father_id = ?, mother_id = ?, spouse_id = ?"+
                    "where person_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ps.getPerson_id());
            stmt.setString(2, ps.getAssociated_username());
            stmt.setString(3, ps.getFirst_name());
            stmt.setString(4, ps.getLast_name());
            stmt.setString(5, ps.getGender());
            stmt.setString(6, ps.getFather_id());
            stmt.setString(7, ps.getMother_id());
            stmt.setString(8, ps.getSpouse_id());
            stmt.setString(9,personIdToUpdate);
            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated person " + ps.getPerson_id());
            } else {
                System.out.println("Failed to update person " + personIdToUpdate);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
