package com.example.familymapclient;

import java.util.HashMap;
import java.util.Map;

import model.Event;
import model.Person;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventResult;
import results.LoginResult;
import results.PersonResult;
import results.RegisterResult;
import com.google.android.gms.maps.model.Marker;


public class ClientInformation {
    private String authToken,userPersonID,serverHost,serverPort;
    private LoginRequest loginRequest;
    private LoginResult loginResult;
    private RegisterRequest registerRequest;
    private RegisterResult registerResult;
    private PersonResult personResult;
    private EventResult eventResult;
    private Map<String,Person> personIDtoPerson = new HashMap<>();
    private Map<String, Person> associatedPeople;
    private Map<String, Event> associatedEvents;
    private Map<Marker,Event>  waypointToEvent = new HashMap<>();

    public void generatePersonPersonIDMap(){
        for (Person person: personResult.getPeople()) {
            personIDtoPerson.put(person.getPerson_id(),person);
        }
    }
    public Person getPersonFromID(String id){
        return personIDtoPerson.get(id);
    }

    public void addWaypoint(Marker waypoint, Event ev){
        waypointToEvent.put(waypoint,ev);
    }
    public Event getEventFromWaypoint(Marker waypoint){
        return waypointToEvent.get(waypoint);
    }

    public PersonResult getPersonResult() {
        return personResult;
    }

    public void setPersonResult(PersonResult personResult) {
        this.personResult = personResult;
        generatePersonPersonIDMap();
    }

    public EventResult getEventResult() {
        return eventResult;
    }

    public void setEventResult(EventResult eventResult) {
        this.eventResult = eventResult;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }

    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    public RegisterResult getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(RegisterResult registerResult) {
        this.registerResult = registerResult;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserPersonID() {
        return userPersonID;
    }

    public void setUserPersonID(String userPersonID) {
        this.userPersonID = userPersonID;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Map<String, Person> getAssociatedPeople() {
        return associatedPeople;
    }

    public void setAssociatedPeople(Map<String, Person> associatedPeople) {
        this.associatedPeople = associatedPeople;
    }

    public Map<String, Event> getAssociatedEvents() {
        return associatedEvents;
    }

    public void setAssociatedEvents(Map<String, Event> associatedEvents) {
        this.associatedEvents = associatedEvents;
    }
}