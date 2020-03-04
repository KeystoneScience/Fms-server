package requests;

import model.Event;
import model.Person;
import model.User;

import java.util.ArrayList;

/**
 * This class has the data structure for information regarding any
 * fill Event. It will pass in array lists for users, persons, and events.
 */
public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    /**
     * load request constructor
     */
    public LoadRequest(){};

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPeople() {
        return persons;
    }

    public void setPeople(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
