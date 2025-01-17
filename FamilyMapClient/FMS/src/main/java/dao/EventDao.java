package dao;

import com.google.gson.Gson;
import model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * the Event data access class. This class is responsible for
 * performing operations on the Event sql table.
 */
public class EventDao {

    private LocationList locations = new LocationList();
    private boolean hasBeenRead = false;
    private Connection connection;

    public EventDao(Connection conn) {
        connection = conn;
    }

    public EventDao() {

    }

    public void removeEvent(User user) throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                String sql = "delete from event where associated_username = '" + user.getId() + "'";
                stmt.executeUpdate(sql);


            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Error:  delete event from database failed");
        }
    }


    public void generateLists(){
        Gson gson = new Gson();
        if(hasBeenRead){
            return;
        }
        try {

            System.out.println("Reading JSONs");
            System.out.println("----------------------------");


            //IF BUG, CHECK PATH STUFF.
            String location = "src\\json\\locations.json" ;
            Path filePath = FileSystems.getDefault().getPath(location);


            BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()));

            //convert the json string back to object
            locations = gson.fromJson(br, LocationList.class);

            hasBeenRead = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventDao)) return false;
        EventDao EventDao = (EventDao) o;
        return Objects.equals(getConnection(), EventDao.getConnection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConnection());
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * inserts an Event into the database
     * @param ev Event model
     * @throws SQLException input output SQL exception
     */
    public void insertEvent(Event ev) throws SQLException, DataAccessException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into Event (Event_id, associated_Username, Person_id, latitude, longitude, country, city, Event_type, year) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, ev.getEvent_id());
            stmt.setString(2, ev.getAssociated_Username());
            stmt.setString(3, ev.getPerson_id());
            stmt.setDouble(4, ev.getLatitude());
            stmt.setDouble(5, ev.getLongitude());
            stmt.setString(6, ev.getCountry());
            stmt.setString(7, ev.getCity());
            stmt.setString(8,ev.getEvent_type());
            stmt.setInt(9,ev.getYear());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted Event failed" + ev.getEvent_id());
            }
            System.out.println("Inserted Event " + ev.getEvent_id());


        } catch (SQLException e) {
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
     * prints all Events in the database
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    public void printEvents(String prefix, List<Event> books) {
        for(Event book : books) {
            System.out.println(prefix + book.getEvent_id());
        }

    }

    /**
     * removes an Event from the database
     * @param ev Event model
     * @throws SQLException input output SQL exception
     */
    public void removeEvent(Event ev) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql; //= "delete from User";
            //stmt = connection.prepareStatement(sql);

            //int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from Event where name = '"+ ev.getEvent_id() + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted Event: %s\n", ev.getEvent_id());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * gets a list of all Events in the database
     * @return return list of Events
     * @throws SQLException input output SQL exception
     */
    public List<Event> getUserEvents(String ID) throws DataAccessException {
        List<Event> events = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE associated_username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            while(rs.next()) {
                String event_id = rs.getString(1);
                String associated_Username = rs.getString(2);
                String person_id = rs.getString(3);
                float latitude = rs.getFloat(4);
                float longitude = rs.getFloat(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String event_type = rs.getString(8);
                int year = rs.getInt(9);

                events.add(new Event(event_id,associated_Username,person_id,latitude,longitude,country,city,event_type,year));

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
        return events;
    }

    /**
     * updates  the info of an Event in the database
     * @param ev Event object with new info
     * @param EventIdToUpdate id of the Event that will be updated.
     * @throws SQLException input output SQL exception
     */
    public void updateEvent(Event ev,String EventIdToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update Event " +
                    "set Event_id = ?, associated_Username = ?, Person_id = ?, latitude = ?," +
                    " longitude = ?, country = ?, city = ?, Event_type = ?, year = ?"+
                    "where Event_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ev.getEvent_id());
            stmt.setString(2, ev.getAssociated_Username());
            stmt.setString(3, ev.getPerson_id());
            stmt.setFloat(4, ev.getLatitude());
            stmt.setFloat(5, ev.getLongitude());
            stmt.setString(6, ev.getCountry());
            stmt.setString(7, ev.getCity());
            stmt.setString(8, ev.getEvent_type());
            stmt.setInt(9,ev.getYear());
            stmt.setString(10,EventIdToUpdate);
            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated Event " + ev.getEvent_id());
            } else {
                System.out.println("Failed to update Event " + EventIdToUpdate);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Event find(String ID, String associatedUsername) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE event_id = ? and associated_Username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ID);
            stmt.setString(2, associatedUsername);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String event_id = rs.getString(1);
                String associated_Username = rs.getString(2);
                String person_id = rs.getString(3);
                float latitude = rs.getFloat(4);
                float longitude = rs.getFloat(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String event_type = rs.getString(8);
                int year = rs.getInt(9);

                event = new Event(event_id,associated_Username,person_id,latitude,longitude,country,city,event_type,year);
                return event;
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


    public Event find(String EventID) throws DataAccessException {
        Event Event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE Event_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, EventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Event = new Event(rs.getString("Event_id"), rs.getString("associated_Username"),
                        rs.getString("Person_id"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("Event_type"),
                        rs.getInt("year"));
                return Event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding Event");
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

    public Event findBirth(String personID) throws DataAccessException {
        Event Event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE Person_id = ? AND Event_type = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.setString(2, "birth");

            rs = stmt.executeQuery();
            if (rs.next()) {
                Event = new Event(rs.getString("Event_id"), rs.getString("associated_Username"),
                        rs.getString("Person_id"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("Event_type"),
                        rs.getInt("year"));
                return Event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding Event");
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


    public Event findDeath(String personID) throws DataAccessException {
        Event Event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE Person_id = ? AND Event_type = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.setString(2, "death");

            rs = stmt.executeQuery();
            if (rs.next()) {
                Event = new Event(rs.getString("Event_id"), rs.getString("associated_Username"),
                        rs.getString("Person_id"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("Event_type"),
                        rs.getInt("year"));
                return Event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding Event");
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

    public boolean generateBirth(Person person, int birthday) throws SQLException, DataAccessException {
        generateLists();
        Event birth = new Event();
        birth.setEvent_id(UUID.randomUUID().toString());
        birth.setAssociated_Username(person.getAssociated_Username());
        birth.setPerson_id(person.getPerson_id());
        Location local = locations.getRandom();
        birth.setLatitude(local.getLatitude());
        birth.setLongitude(local.getLongitude());
        birth.setCountry(local.getCountry());
        birth.setCity(local.getCity());
        birth.setEvent_type("birth");
        birth.setYear(birthday);
        try {
            insertEvent(birth);
        }catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean generateDeath(Person person, int dod) throws SQLException, DataAccessException {
        generateLists();
        Event death = new Event();
        death.setEvent_id(UUID.randomUUID().toString());
        death.setAssociated_Username(person.getAssociated_Username());
        death.setPerson_id(person.getPerson_id());
        Location local = locations.getRandom();
        death.setLatitude(local.getLatitude());
        death.setLongitude(local.getLongitude());
        death.setCountry(local.getCountry());
        death.setCity(local.getCity());
        death.setEvent_type("death");
        death.setYear(dod);
        try {
            insertEvent(death);
        }catch (SQLException e){
            return false;
        }

        return true;
    }


    public Location generateLocation() throws SQLException, DataAccessException {
        generateLists();

        return locations.getRandom();
    }






}
