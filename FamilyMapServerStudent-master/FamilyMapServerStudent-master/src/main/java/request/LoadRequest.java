package request;

import model.Event;
import model.Person;
import model.User;

import java.util.ArrayList;

/**
 * This class has the data structure for information regarding any
 * fill Event. It will pass in array lists for Users, people, and Events.
 */
public class LoadRequest {
    private ArrayList<User> Users;
    private ArrayList<Person> people;
    private ArrayList<Event> Events;

    /**
     * load request constructor
     */
    public LoadRequest(){};

    public ArrayList<User> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<User> Users) {
        this.Users = Users;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Event> getEvents() {
        return Events;
    }

    public void setEvents(ArrayList<Event> Events) {
        this.Events = Events;
    }
}
