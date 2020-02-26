package dao;

import model.ListStructure;
import model.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
/**
 * the Person data access class. This class is responsible for
 * performing operations on the Person sql table.
 */
public class PersonDao {



    private Connection connection;
    private ListStructure fnames = new ListStructure();
    private ListStructure mnames = new ListStructure();
    private ListStructure snames = new ListStructure();
    private boolean hasBeenRead = false;



    public PersonDao(Connection conn) {
        connection=conn;
    }

    public String randomFemaleName(){
        generateLists();
        return fnames.getRandom();
    }
    public String randomMaleName(){
        generateLists();
        return mnames.getRandom();
    }
    public String randomLastName(){
        generateLists();
        return snames.getRandom();
    }

    private void generateLists(){
        Gson gson = new Gson();
        if(hasBeenRead){
            return;
        }
        try {

            System.out.println("Reading JSONs");
            System.out.println("----------------------------");


            //IF BUG, CHECK PATH STUFF.

            BufferedReader br = new BufferedReader(new FileReader(PersonDao.class.getClassLoader()
                    .getResource("fnames.json").getPath()
                    .replaceAll("%20", " ")));

            //convert the json string back to object
            ListStructure fnames = gson.fromJson(br, ListStructure.class);

            br = new BufferedReader(new FileReader(PersonDao.class.getClassLoader()
                    .getResource("mnames.json").getPath()
                    .replaceAll("%20", " ")));

            //convert the json string back to object
            ListStructure mnames = gson.fromJson(br, ListStructure.class);

            br = new BufferedReader(new FileReader(PersonDao.class.getClassLoader()
                    .getResource("snames.json").getPath()
                    .replaceAll("%20", " ")));

            //convert the json string back to object
            ListStructure snames = gson.fromJson(br, ListStructure.class);

            hasBeenRead = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    /**
     * Clears the data table
     * @throws SQLException
     */
    public void clearTable() throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql;
            sql = "delete from person";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("cleared Person table");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

    }



    /**
     * inserts a Person into the data base
     * @param pe Person model
     * @throws SQLException input output SQL exception
     */
    public void insertPerson(Person pe) throws SQLException, DataAccessException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into person (person_id, associated_Username, first_name, last_name, gender, father_id, mother_id, spouse_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, pe.getPerson_id());
            stmt.setString(2, pe.getAssociated_Username());
            stmt.setString(3, pe.getFirst_name());
            stmt.setString(4, pe.getLast_name());
            stmt.setString(5, pe.getGender());
            stmt.setString(6, pe.getFather_id());
            stmt.setString(7, pe.getMother_id());
            stmt.setString(8,pe.getSpouse_id());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted User failed" + pe.getPerson_id());
            }
            System.out.println("Inserted User " + pe.getPerson_id());


        }catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * prints all Persons in the database
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    public void printPersons(String prefix, List<Person> books) {
        for(Person book : books) {
            System.out.println(prefix + book.getPerson_id());
        }

    }

    /**
     * removes a Person from the database
     * @param ps Person model
     * @throws SQLException input output SQL exception
     */
    public void removePerson(Person ps) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql; //= "delete from User";
            //stmt = connection.prepareStatement(sql);

            //int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from person where name = '"+ ps.getPerson_id() + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted Person: %s\n", ps.getPerson_id());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * returns a list of all people in the database
     * @return return list of Persons
     * @throws SQLException input output SQL exception
     */
    public List<Person> getPersons() throws SQLException {

        List<Person> Persons = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select person_id, associated_Username, first_name, last_name, gender, father_id, mother_id, spouse_id from person";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String Person_id = rs.getString(1);
                String associated_Username = rs.getString(2);
                String first_name = rs.getString(3);
                String last_name = rs.getString(4);
                String gender = rs.getString(5);
                String father_id = rs.getString(6);
                String mother_id = rs.getString(7);
                String spouse_id = rs.getString(8);

                Persons.add(new Person(Person_id,associated_Username,first_name,last_name,gender,father_id,mother_id,spouse_id
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

        return Persons;
    }

    /**
     * updates the information of a Person in the database.
     * @param ps Person model object
     * @param PersonIdToUpdate Unique identifier for the PersonToUpdate
     * @throws SQLException input output SQL exception
     */
    public void updatePerson(Person ps,String PersonIdToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update person " +
                    "set person_id = ?, associated_Username = ?, first_name = ?, last_name = ?," +
                    " gender = ?, father_id = ?, mother_id = ?, spouse_id = ?"+
                    "where person_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ps.getPerson_id());
            stmt.setString(2, ps.getAssociated_Username());
            stmt.setString(3, ps.getFirst_name());
            stmt.setString(4, ps.getLast_name());
            stmt.setString(5, ps.getGender());
            stmt.setString(6, ps.getFather_id());
            stmt.setString(7, ps.getMother_id());
            stmt.setString(8, ps.getSpouse_id());
            stmt.setString(9,PersonIdToUpdate);
            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated Person " + ps.getPerson_id());
            } else {
                System.out.println("Failed to update Person " + PersonIdToUpdate);
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
    public Person find(String ID) throws DataAccessException {
        Person Person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE person_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            //Person_id, associated_Username, first_name, last_name, gender, father_id, mother_id, spouse_id
            if (rs.next()) {

                Person = new Person(rs.getString("Person_id"),
                        rs.getString("associated_Username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("father_id"),
                        rs.getString("mother_id"),
                        rs.getString("spouse_id"));
                return Person;
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
