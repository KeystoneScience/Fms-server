package service_test;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventResult;
import results.PersonResult;
import results.RegisterResult;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoginServiceTest{

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
    private requests.LoginRequest loginRequest;
    private ClearService cs = new ClearService();


    @BeforeEach
    public void setUp() {
        cs.clear();
        registerRequest = new RegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");
        rs = new RegisterService();
        registerResult=rs.register(registerRequest);
        ls = new LoginService();


    }


    @AfterEach
    public void tearDown()  {
        cs.clear();

    }



    @Test // tests empty response and bad auth
    public void loginSuccessful() {

        loginRequest = new LoginRequest("username","password");
       loginResult=ls.login(loginRequest);
       assertNotNull(loginResult.getAutherizationToken());
       assertNotNull(loginResult.getpersonID());
       assertNotNull(loginResult.getuserName());


    }

    @Test // tests empty response and bad auth
    public void incorrectPassword() {
        loginRequest = new LoginRequest("username","theword");
        loginResult=ls.login(loginRequest);
        assertNull(loginResult.getAutherizationToken());
        assertNull(loginResult.getpersonID());
        assertNull(loginResult.getuserName());
        assertTrue(loginResult.getMessage().toLowerCase().contains("password"));


    }

    @Test // tests empty response and bad auth
    public void incorrectUsername() {
        loginRequest = new LoginRequest("420Guy","password");
        loginResult=ls.login(loginRequest);
        assertNull(loginResult.getAutherizationToken());
        assertNull(loginResult.getpersonID());
        assertNull(loginResult.getuserName());
        assertTrue(loginResult.getMessage().toLowerCase().contains("username"));


    }

}

