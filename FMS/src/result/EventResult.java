package result;

import model.Event;

import java.util.ArrayList;

/**
 * data structure containing the responses for the Event api
 */
public class EventResult extends ParentResult {
    private ArrayList<Event> Events;
    private String decendant;
    private String EventID;
    private String PersonID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String EventType;
    private int year;



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
        return decendant;
    }

    public void setDecendant(String decendant) {
        this.decendant = decendant;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String EventID) {
        this.EventID = EventID;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String PersonID) {
        this.PersonID = PersonID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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
        return EventType;
    }

    public void setEventType(String EventType) {
        this.EventType = EventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<Event> getEvents() {
        return Events;
    }

    public void setEvents(ArrayList<Event> Events) {
        this.Events = Events;
    }
}
