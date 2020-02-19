package service;

import model.event;
import result.eventResult;

import java.util.ArrayList;

/**
 * Serves the event requests for specific event ID lookup, and
 */
public class eventService {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * This is for return a single event
     * @param eventId String specifying the event
     * @param userID String specifying the user
     * @return the fail or success error message
     */
    public eventResult obtainEvent(String eventId, String userID){
        return null;
    }

    /**
     * This will return a list of all events.
     * @param authenticationToken the authentication token string
     * @return the fail or success error message
     */
    public eventResult getAllUserEvents(String authenticationToken){
        return null;
    }

}
