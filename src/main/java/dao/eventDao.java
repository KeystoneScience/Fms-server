package dao;

import model.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * the event data access class. This class is responsible for
 * performing operations on the event sql table.
 */
public class eventDao {

    private Connection connection;


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * inserts an event into the database
     * @param ev event model
     * @throws SQLException input output SQL exception
     */
    private void insertEvent(event ev) throws SQLException, DataAccessException {
        PreparedStatement stmt = null;
        try {
            String sql = "insert into event (event_id, associated_username, person_id, latitude, longitude, country, city, event_type, year) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);


            stmt.setString(1, ev.getEvent_id());
            stmt.setString(2, ev.getAssociated_username());
            stmt.setString(3, ev.getPerson_id());
            stmt.setDouble(4, ev.getLatitude());
            stmt.setDouble(5, ev.getLongitude());
            stmt.setString(6, ev.getCountry());
            stmt.setString(7, ev.getCity());
            stmt.setString(8,ev.getEvent_type());
            stmt.setInt(9,ev.getYear());

            if(stmt.executeUpdate() != 1){
                System.out.println("Inserted event failed" + ev.getEvent_id());
            }
            System.out.println("Inserted event " + ev.getEvent_id());


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
     * prints all events in the database
     * @param prefix specify a prefix for printing out everything in the database
     * @param books list of model data class
     */
    private void printEvents(String prefix, List<event> books) {
        for(event book : books) {
            System.out.println(prefix + book.getEvent_id());
        }

    }

    /**
     * removes an event from the database
     * @param ev event model
     * @throws SQLException input output SQL exception
     */
    private void removeEvent(event ev) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql; //= "delete from user";
            //stmt = connection.prepareStatement(sql);

            //int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from event where name = '"+ ev.getEvent_id() + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.printf("Deleted event: %s\n", ev.getEvent_id());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    /**
     * gets a list of all events in the database
     * @return return list of events
     * @throws SQLException input output SQL exception
     */
    private List<event> getEvents() throws SQLException {

        List<event> events = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select event_id, associated_username, person_id, latitude, longitude, country, city, event_type, year from event";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {
                String event_id = rs.getString(1);
                String associated_username = rs.getString(2);
                String person_id = rs.getString(3);
                Double latitude  = rs.getDouble(4);
                Double longitude = rs.getDouble(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String event_type = rs.getString(8);
                int year = rs.getInt(9);


                events.add(new event(event_id,associated_username,person_id,latitude,longitude,country,city,event_type,year
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

        return events;
    }

    /**
     * updates  the info of an event in the database
     * @param ev event object with new info
     * @param eventIdToUpdate id of the event that will be updated.
     * @throws SQLException input output SQL exception
     */
    private void updateEvent(event ev,String eventIdToUpdate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "update event " +
                    "set event_id = ?, associated_username = ?, person_id = ?, latitude = ?," +
                    " longitude = ?, country = ?, city = ?, event_type = ?, year = ?"+
                    "where event_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ev.getEvent_id());
            stmt.setString(2, ev.getAssociated_username());
            stmt.setString(3, ev.getPerson_id());
            stmt.setDouble(4, ev.getLatitude());
            stmt.setDouble(5, ev.getLongitude());
            stmt.setString(6, ev.getCountry());
            stmt.setString(7, ev.getCity());
            stmt.setString(8, ev.getEvent_type());
            stmt.setInt(9,ev.getYear());
            stmt.setString(10,eventIdToUpdate);
            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated event " + ev.getEvent_id());
            } else {
                System.out.println("Failed to update event " + eventIdToUpdate);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Finds and returns the event data object corresponding to the given event ID
     * @param eventID specifies Event information to return
     * @return event data object with values drawn from event ID.
     * @throws DataAccessException error finding the event.
     */
    public event find(String eventID) throws DataAccessException {
        event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new event(rs.getString("event_id"), rs.getString("associated_username"),
                        rs.getString("person_id"), rs.getDouble("latitude"), rs.getDouble("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("event_type"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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
