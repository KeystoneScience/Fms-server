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
import results.LoginResult;
import results.RegisterResult;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ClearServiceTest{

    private Person bestPerson;
    private EventService es;
    private RegisterService rs;
    private results.RegisterResult registerResult;
    private requests.RegisterRequest registerRequest;
    private requests.LoginRequest loginRequest;
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



    @Test //Register service and login service must be working.
    public void clearSuccess() throws Exception {
        registerRequest = new RegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");

        registerResult=rs.register(registerRequest);

        assertNotNull(registerResult.getauthToken());
        assertNotNull(registerResult.getpersonID());
        assertNotNull(registerResult.getuserName());

        loginRequest = new LoginRequest(registerResult.getuserName(),registerRequest.getPassword());
        loginResult = ls.login(loginRequest);

        assertTrue(loginResult.isSuccess());

        cs.clear();


        loginResult = ls.login(loginRequest);

        assertFalse(loginResult.isSuccess());

    }


}