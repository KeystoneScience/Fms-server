package dao;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * the Event data access class. This class is responsible for
 * performing operations on the Event sql table.
 */
public class EventDao {

    private Connection connection;

    public EventDao(Connection conn) {
        connection = conn;
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
    public List<Event> getEvents() throws SQLException {

        List<Event> Events = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select Event_id, associated_Username, Person_id, latitude, longitude, country, city, Event_type, year from Event";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String Event_id = rs.getString(1);
                String associated_Username = rs.getString(2);
                String Person_id = rs.getString(3);
                float latitude  = rs.getFloat(4);
                float longitude = rs.getFloat(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String Event_type = rs.getString(8);
                int year = rs.getInt(9);


                Events.add(new Event(Event_id,associated_Username,Person_id,latitude,longitude,country,city,Event_type,year
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

        return Events;
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

    /**
     * Finds and returns the Event data object corresponding to the given Event ID
     * @param EventID specifies Event information to return
     * @return Event data object with values drawn from Event ID.
     * @throws DataAccessException error finding the Event.
     */
    public Event find1(String EventID) throws DataAccessException {
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


}
