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
import results.PersonResult;
import results.RegisterResult;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PersonServiceTest{

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
        personResult = new PersonResult();
        ps = new PersonService();

    }


    @AfterEach
    public void tearDown()  {
        cs.clear();

    }



    @Test // tests empty response and bad auth
    public void personListValid() {

        personResult = ps.Persons(registerResult.getauthToken());
        assertNotNull(personResult.getPeople());
        assertEquals(personResult.getPeople().size(),31 );


    }

    @Test // tests empty response and bad auth
    public void personListInvalid() {


        personResult = ps.Persons("BAD AUTH");
        assertNull(personResult.getPeople());
        assertTrue(personResult.getMessage().toLowerCase().contains("error"));

    }

    @Test // tests empty response and bad auth
    public void personSearchValid() {

        personResult = ps.Persons(registerResult.getauthToken());
        assertNotNull(personResult.getPeople());
        Person person = personResult.getPeople().get(1);

        personResult = ps.findPerson(person.getPerson_id(),registerResult.getauthToken());

        assertNotNull(personResult);
        assertEquals(personResult.getFatherID(),person.getFather_id());


        personResult = ps.findPerson(person.getPerson_id(),"oof, bad");

        assertNull(personResult.getpersonID());
        assertTrue(personResult.getMessage().toLowerCase().contains("error"));

    }

    @Test // tests empty response and bad auth
    public void personSearchInvalid() {

        personResult = ps.Persons(registerResult.getauthToken());
        assertNotNull(personResult.getPeople());
        Person person = personResult.getPeople().get(1);


        personResult = ps.findPerson(person.getPerson_id(),"oof, bad");

        assertNull(personResult.getpersonID());
        assertTrue(personResult.getMessage().toLowerCase().contains("error"));

    }

}

