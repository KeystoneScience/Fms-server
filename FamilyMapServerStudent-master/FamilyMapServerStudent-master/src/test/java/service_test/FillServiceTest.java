package service_test;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.FillRequest;
import requests.RegisterRequest;
import results.EventResult;
import results.FillResult;
import results.RegisterResult;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FillServiceTest{

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
    private FillService fs = new FillService();
    private FillRequest fillRequest;
    private FillResult fillResult;


    @BeforeEach
    public void setUp() throws Exception {
        cs.clear();
        rs = new RegisterService();
        registerRequest = new RegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");

        registerResult=rs.register(registerRequest);
    }


    @AfterEach
    public void tearDown() throws Exception {
        cs.clear();

    }



    @Test // tests empty response and bad auth
    public void defaultFill() throws Exception {
        fillRequest = new FillRequest(registerResult.getuserName());
        fillResult = fs.fill(fillRequest);
        assertTrue(fillResult.isSuccess());


    }

    @Test // tests empty response and bad auth
    public void fillMoreGenerations() throws Exception {
        fillRequest = new FillRequest(registerResult.getuserName(),5);
        fillResult = fs.fill(fillRequest);
        assertTrue(fillResult.isSuccess());

        assertEquals(fillResult.getNumPeople(),63 );


    }

    @Test // tests empty response and bad auth
    public void invalidGenerationNumber() throws Exception {
        fillRequest = new FillRequest(registerResult.getuserName(),-1);
        fillResult = fs.fill(fillRequest);
        assertFalse(fillResult.isSuccess());
        assertTrue(fillResult.getMessage().toLowerCase().contains("error"));
    }

}