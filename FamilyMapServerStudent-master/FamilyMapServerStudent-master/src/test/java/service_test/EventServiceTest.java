package service_test;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.EventResult;
import results.RegisterResult;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EventServiceTest{

    private Person bestPerson;
    private EventService es;
    private RegisterService rs;
    private results.RegisterResult registerResult;
    private requests.RegisterRequest registerRequest;
    private PersonService ps;
    private LoginService ls;
    private results.LoginResult loginResult;
    private results.PersonResult personResult;
    private results.EventResult eventResult;
    private ClearService cs = new ClearService();


    @BeforeEach
    public void setUp() {
        cs.clear();
        registerRequest = new RegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");
        rs = new RegisterService();
        registerResult=rs.register(registerRequest);
        eventResult = new EventResult();
        es = new EventService();

    }


    @AfterEach
    public void tearDown() {
        cs.clear();

    }



    @Test
    public void eventListValid() {

        eventResult = es.getAllUserEvents(registerResult.getauthToken());
        assertNotNull(eventResult.getEvents());
        assertEquals(eventResult.getEvents().size(),91 );
        assertTrue(eventResult.isSuccess());

    }


    @Test
    public void eventListInvalid() {

        eventResult = es.getAllUserEvents("fake Auth");
        assertNull(eventResult.getEvents());
        assertTrue(eventResult.getMessage().toLowerCase().contains("error"));
        assertFalse(eventResult.isSuccess());
    }

    @Test
    public void eventSearchValid() {

        eventResult = es.getAllUserEvents(registerResult.getauthToken());
        assertNotNull(eventResult.getEvents());
        Event ev = eventResult.getEvents().get(1);

        eventResult = es.getEvent(ev.getEvent_id(),registerResult.getauthToken());

        assertNotNull(eventResult);
        assertEquals(eventResult.getCity(),ev.getCity());
        assertTrue(eventResult.isSuccess());

    }

    @Test
    public void eventSearchInvalid() {

        eventResult = es.getEvent("fakeResult",registerResult.getauthToken());


        assertNull(eventResult.getCity());
        assertTrue(eventResult.getMessage().toLowerCase().contains("error"));
        assertFalse(eventResult.isSuccess());

    }

}

