package model;

/**
 * Model class for the event data
 */
public class event {
    private String event_id;
    private String associated_username;
    private String person_id;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String event_type;
    private int year;

    /**
     * constructor
     * @param event_id unique event identifier
     * @param associated_username username associated
     * @param person_id unique identifier for the person
     * @param latitude event latitude
     * @param longitude event longitude
     * @param country country event took place
     * @param city city the event took place
     * @param event_type type of event
     * @param year year
     */
    public event(String event_id, String associated_username, String person_id, Double latitude, Double longitude, String country, String city, String event_type, int year) {
        this.event_id = event_id;
        this.associated_username = associated_username;
        this.person_id = person_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.event_type = event_type;
        this.year = year;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getAssociated_username() {
        return associated_username;
    }

    public void setAssociated_username(String associated_username) {
        this.associated_username = associated_username;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
