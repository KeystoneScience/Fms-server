package service;

import dao.*;
import model.AuthToken;
import model.Event;
import results.EventResult;

import java.util.List;

/**
 * Serves the Event requests for specific Event ID lookup, and
 */
public class EventService {
    private boolean success;
    private Database db = new Database();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * This is for return a single Event
     * @param eventId String specifying the Event
     * @param authToken String specifying the User
     * @return the fail or success error message
     */
    public EventResult getEvent(String eventId, String authToken){
        EventResult er = new EventResult();
        try {


            db.openConnection();


            AuthTokenDao atd = new AuthTokenDao(db.getConnection());

            EventDao ed = new EventDao(db.getConnection());


            AuthToken at = atd.find(authToken);
            if(at == null){
                throw new DataAccessException("Error: Auth Token Incorrect.");
            }

            Event event = ed.find(eventId,at.getUsername());

            if(event == null){
                throw new DataAccessException("Error: No Event Found.");
            }


            er.setCity(event.getCity());
            er.setCountry(event.getCountry());
            er.setDecendant(event.getAssociated_Username());
            er.setEventID(event.getEvent_id());
            er.setLatitude(event.getLatitude());
            er.setLongitude(event.getLongitude());
            er.setEventType(event.getEvent_type());
            er.setYear(event.getYear());
            er.setPersonID(event.getPerson_id());

            er.setSuccess(true);

            db.closeConnection(true);

        } catch (DataAccessException e) {
            er.setSuccess(false);
            er.setMessage(e.getMessage());
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                er.setMessage(e.getMessage());
            }
        }

        return er;
    }
    /**
     * This will return a list of all Events.
     * @param authenticationToken the authentication token string
     * @return the fail or success error message
     */
    public EventResult getAllUserEvents(String authenticationToken){
        EventResult er = new EventResult();
        try {


        db.openConnection();


        AuthTokenDao atd = new AuthTokenDao(db.getConnection());

        EventDao ed = new EventDao(db.getConnection());


        AuthToken at = atd.find(authenticationToken);
        if(at == null){
            throw new DataAccessException("Error: Auth Token Incorrect.");
        }
        List<Event> events = ed.getUserEvents(at.getUsername());

        er.setEvents(events);

        er.setSuccess(true);
        db.closeConnection(true);

    } catch (DataAccessException e) {
        er.setSuccess(false);
        er.setMessage(e.getMessage());
        try {
            db.closeConnection(false);
        } catch (DataAccessException ex) {
            er.setMessage(e.getMessage());
        }
    }

        return er;
}

}
