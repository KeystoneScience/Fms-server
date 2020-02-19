package request;

import model.event;
import model.person;
import model.user;

import java.util.ArrayList;

/**
 * This class has the data structure for information regarding any
 * fill event. It will pass in array lists for users, people, and events.
 */
public class loadRequest {
    private ArrayList<user> users;
    private ArrayList<person> people;
    private ArrayList<event> events;

    /**
     * load request constructor
     */
    public loadRequest(){};

    public ArrayList<user> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<user> users) {
        this.users = users;
    }

    public ArrayList<person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<person> people) {
        this.people = people;
    }

    public ArrayList<event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<event> events) {
        this.events = events;
    }
}
