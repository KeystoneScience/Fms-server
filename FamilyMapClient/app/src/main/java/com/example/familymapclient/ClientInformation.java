package com.example.familymapclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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



    private boolean lifeStoryLines;
    private boolean familyTreeLines;
    private boolean spouseLine;
    private boolean fatherSide;
    private boolean motherSide;
    private boolean maleEvents;

    public boolean isLifeStoryLines() {
        return lifeStoryLines;
    }

    public void setLifeStoryLines(boolean lifeStoryLines) {
        this.lifeStoryLines = lifeStoryLines;
    }

    public boolean isFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(boolean familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public boolean isSpouseLine() {
        return spouseLine;
    }

    public void setSpouseLine(boolean spouseLine) {
        this.spouseLine = spouseLine;
    }

    public boolean isFatherSide() {
        return fatherSide;
    }

    public void setFatherSide(boolean fatherSide) {
        this.fatherSide = fatherSide;
    }

    public boolean isMotherSide() {
        return motherSide;
    }

    public void setMotherSide(boolean motherSide) {
        this.motherSide = motherSide;
    }

    public boolean isMaleEvents() {
        return maleEvents;
    }

    public void setMaleEvents(boolean maleEvents) {
        this.maleEvents = maleEvents;
    }

    public boolean isFemaleEvents() {
        return femaleEvents;
    }

    public void setFemaleEvents(boolean femaleEvents) {
        this.femaleEvents = femaleEvents;
    }

    private boolean femaleEvents;

    public void clearWaypointToEvent(){
        waypointToEvent.clear();
    }

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

    public List<Event> chronologicalEvents(String personID){
        List<Event> events = new ArrayList<>();
        for (Event ev: eventResult.getEvents()) {
            if(ev.getPerson_id().equals(personID)){
                events.add(ev);
            }
        }
        if(events.size()==1){
            return events;
        }
        while(true){
            boolean sorted = false;
            for (int i = 0; i < events.size()-1; i++) {
                if(events.get(i).getYear() > events.get(i+1).getYear()){
                    Event ev = events.get(i);
                    events.set(i,events.get(i+1));
                    events.set(i+1,ev);
                    sorted = false;
                }
                else{
                    sorted = true;
                }
            }
            if(sorted){
                break;
            }
        }
        return events;

    }
}
