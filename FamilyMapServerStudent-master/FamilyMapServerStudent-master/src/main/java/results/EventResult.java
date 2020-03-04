package results;

import model.Event;

import java.util.List;

/**
 * data structure containing the responses for the Event api
 */
public class EventResult extends ParentResult {
    private List<Event> data;
    private String associatedUsername;
    private String eventID;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;



    /**
     * object constructor
     */
    public EventResult(){};

    /**
     * EventID request was successful
     * @return success string message
     */
    public String successResponse(){
        return null;
    }

    public String getDecendant() {
        return associatedUsername;
    }

    public void setDecendant(String decendant) {
        this.associatedUsername = decendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String EventID) {
        this.eventID = EventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String PersonID) {
        this.personID = PersonID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String EventType) {
        this.eventType = EventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Event> getEvents() {
        return data;
    }

    public void setEvents(List<Event> Events) {
        this.data = Events;
    }
}
