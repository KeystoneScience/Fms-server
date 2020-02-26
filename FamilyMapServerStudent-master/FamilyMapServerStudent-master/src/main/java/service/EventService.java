package service;

import result.EventResult;

/**
 * Serves the Event requests for specific Event ID lookup, and
 */
public class EventService {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * This is for return a single Event
     * @param EventId String specifying the Event
     * @param UserID String specifying the User
     * @return the fail or success error message
     */
    public EventResult obtainEvent(String EventId, String UserID){
        return null;
    }

    /**
     * This will return a list of all Events.
     * @param authenticationToken the authentication token string
     * @return the fail or success error message
     */
    public EventResult getAllUserEvents(String authenticationToken){
        return null;
    }

}
