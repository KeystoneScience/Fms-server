package model;

import java.util.Objects;

/**
 * Model class for the Event data
 */
public class Event {
    private String Event_id;
    private String associated_Username;
    private String Person_id;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String Event_type;
    private int year;

    public Event() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event Event = (Event) o;
        return Float.compare(Event.getLatitude(), getLatitude()) == 0 &&
                Float.compare(Event.getLongitude(), getLongitude()) == 0 &&
                getYear() == Event.getYear() &&
                Objects.equals(getEvent_id(), Event.getEvent_id()) &&
                Objects.equals(getAssociated_Username(), Event.getAssociated_Username()) &&
                Objects.equals(getPerson_id(), Event.getPerson_id()) &&
                Objects.equals(getCountry(), Event.getCountry()) &&
                Objects.equals(getCity(), Event.getCity()) &&
                Objects.equals(getEvent_type(), Event.getEvent_type());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEvent_id(), getAssociated_Username(), getPerson_id(), getLatitude(), getLongitude(), getCountry(), getCity(), getEvent_type(), getYear());
    }

    /**
     * constructor
     * @param Event_id unique Event identifier
     * @param associated_Username Username associated
     * @param Person_id unique identifier for the Person
     * @param latitude Event latitude
     * @param longitude Event longitude
     * @param country country Event took place
     * @param city city the Event took place
     * @param Event_type type of Event
     * @param year year
     */
    public Event(String Event_id, String associated_Username, String Person_id, float latitude, float longitude, String country, String city, String Event_type, int year) {
        this.Event_id = Event_id;
        this.associated_Username = associated_Username;
        this.Person_id = Person_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.Event_type = Event_type;
        this.year = year;
    }

    public String getEvent_id() {
        return Event_id;
    }

    public void setEvent_id(String Event_id) {
        this.Event_id = Event_id;
    }

    public String getAssociated_Username() {
        return associated_Username;
    }

    public void setAssociated_Username(String associated_Username) {
        this.associated_Username = associated_Username;
    }

    public String getPerson_id() {
        return Person_id;
    }

    public void setPerson_id(String Person_id) {
        this.Person_id = Person_id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
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

    public String getEvent_type() {
        return Event_type;
    }

    public void setEvent_type(String Event_type) {
        this.Event_type = Event_type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
