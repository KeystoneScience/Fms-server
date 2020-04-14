package com.example.familymapclient;

import com.google.android.gms.maps.model.Marker;

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



public class ClientInfo {
    private String authToken,userPersonID,serverHost,serverPort;
    private LoginRequest loginRequest;
    private LoginResult loginResult;
    private RegisterRequest registerRequest;
    private RegisterResult registerResult;
    private PersonResult personResult;
    private EventResult eventResult;
    private Map<String, Person> personIDtoPerson = new HashMap<>();
    private Map<String, Person> associatedPeople;
    public Map<Event, Boolean> filteredEvents = new HashMap<>();
    private Map<Marker,Event>  waypointToEvent = new HashMap<>();
    private Map<Person, String> personToSideOfFamily = new HashMap<>();
    private boolean mapFragmentMainView = true;
    private Event passedEvent;

    public Event getPassedEvent() {
        return passedEvent;
    }

    public void setPassedEvent(Event passedEvent) {
        this.passedEvent = passedEvent;
    }

    public boolean isMapFragmentMainView() {
        return mapFragmentMainView;
    }

    public void setMapFragmentMainView(boolean mapFragmentMainView) {
        this.mapFragmentMainView = mapFragmentMainView;
    }

    private boolean lifeStoryLines = true;
    private boolean familyTreeLines = true;
    private boolean spouseLine = true;
    private boolean fatherSide = true;
    private boolean motherSide = true;
    private boolean maleEvents = true;
    private boolean femaleEvents = true;

    private static final ClientInfo ourInstance = new ClientInfo();

    static ClientInfo getInstance() {
        return ourInstance;
    }

    private ClientInfo() {
    }

    public void filterDefaults(){
        lifeStoryLines=true;
        familyTreeLines=true;
        spouseLine=true;
        fatherSide=true;
        motherSide=true;
        maleEvents=true;
        femaleEvents=true;
    }

    public void clearAll(){
        authToken=null;
        userPersonID = null;
        serverHost=null;
        serverPort = null;
        loginRequest=null;
        loginResult=null;
        registerRequest=null;
        registerResult=null;
        eventResult=null;
        personResult=null;
        personIDtoPerson=null;
        filteredEvents=null;
        associatedPeople=null;
        waypointToEvent=null;

    }

    public Map<String, Person> getPersonIDtoPerson() {
        return personIDtoPerson;
    }

    public Map<Marker, Event> getWaypointToEvent() {
        return waypointToEvent;
    }

    public Map<Person, String> getPersonToSideOfFamily() {
        return personToSideOfFamily;
    }

    public void fillPersonToSide(){
        Person root = personIDtoPerson.get(loginResult.getpersonID());
        personToSideOfFamily.put(root,"root");
        Person mom = personIDtoPerson.get(root.getMother_id());
        Person dad = personIDtoPerson.get(root.getFather_id());
        personToSideOfFamily.put(mom, "mom");
        personToSideOfFamily.put(dad, "dad");
        fillPersonToSide(mom,"mom");
        fillPersonToSide(dad,"dad");
    }

    public void fillPersonToSide(Person root, String side){
        if(!(root.getMother_id() == null)){
            Person mom = personIDtoPerson.get(root.getMother_id());
            personToSideOfFamily.put(mom, side);
            fillPersonToSide(mom,side);
        }
        if(!(root.getFather_id() == null)){
            Person dad = personIDtoPerson.get(root.getFather_id());
            personToSideOfFamily.put(dad, side);
            fillPersonToSide(dad,side);
        }
    }

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
        fillPersonToSide();
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

    public Map<Event, Boolean> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(Map<Event, Boolean> associatedEvents) {
        this.filteredEvents = associatedEvents;
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

    private Person findChild(String rootID){
        for (Person pr: personResult.getPeople()) {
            if(pr.getFather_id()!=null) {
                if (pr.getFather_id().equals(rootID)) {
                    return pr;
                }
            }
            if (pr.getMother_id()!=null) {
                if (pr.getMother_id().equals(rootID)) {
                    return pr;
                }
            }

        }
        return null;
    }


    public List<Family> getFamily(Person root){
        List<Family> fam = new ArrayList<>();
        if(root.getFather_id()!=null){
            fam.add(new Family(getPersonFromID(root.getFather_id()),"Father"));
        }
        if(root.getMother_id()!=null){
            fam.add(new Family(getPersonFromID(root.getMother_id()),"Mother"));
        }
        if(root.getSpouse_id()!=null){
            fam.add(new Family(getPersonFromID(root.getSpouse_id()),"Spouse"));
        }
        Person child = findChild(root.getPerson_id());
        if(child != null){
            fam.add(new Family(child,"Child"));
        }
        return fam;
    }
}
