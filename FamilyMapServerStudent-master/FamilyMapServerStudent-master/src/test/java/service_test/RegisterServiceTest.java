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

public class RegisterServiceTest{

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
    public void setUp() throws Exception {
        cs.clear();
        rs = new RegisterService();
    }


    @AfterEach
    public void tearDown() throws Exception {
        cs.clear();

    }



    @Test // tests empty response and bad auth
    public void registerTestSuccess() throws Exception {
        registerRequest = new RegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");

        registerResult=rs.register(registerRequest);

        assertNotNull(registerResult.getauthToken());
        assertNotNull(registerResult.getpersonID());
        assertNotNull(registerResult.getuserName());

        eventResult = new EventResult();
        es = new EventService();

        eventResult = es.getAllUserEvents(registerResult.getauthToken());
        assertNotNull(eventResult.getEvents());
        assertEquals(eventResult.getEvents().size(),91 );


    }

    @Test // tests empty response and bad auth
    public void registerInvalid() throws Exception {
        registerRequest = new RegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m/f");

        registerResult=rs.register(registerRequest);

        assertNull(registerResult.getauthToken());
        assertNull(registerResult.getpersonID());
        assertNull(registerResult.getuserName());

        assertTrue(registerResult.getMessage().contains("valid"));


    }

}

