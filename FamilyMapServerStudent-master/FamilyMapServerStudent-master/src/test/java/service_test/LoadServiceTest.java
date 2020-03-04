package service_test;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoadRequest;
import requests.RegisterRequest;
import results.EventResult;
import results.LoadResult;
import results.PersonResult;
import results.RegisterResult;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoadServiceTest{

    private Person bestPerson;
    private EventService es;
    private RegisterService rs;
    private results.RegisterResult registerResult;
    private requests.RegisterRequest registerRequest;
    private PersonService ps;
    private LoginService ls;
    private LoadRequest loadRequest;
    private LoadResult loadResult;
    private LoadService loadService;
    private results.LoginResult loginResult;
    private results.PersonResult personResult;
    private results.EventResult eventResult;
    private ClearService cs = new ClearService();
    private ArrayList<Person> persons;
    private ArrayList<Event> events;
    private ArrayList<User> users;




    @BeforeEach
    public void setUp() {
        cs.clear();
        loadService = new LoadService();
        for (int i =0; i<10; ++i) {
            User newUser = new User("u"+i,"p"+i,1+"@gmail.com","first"+1,"last"+i,"m","person"+i);

            users.add(newUser);
        }
        for (int i =0; i<10; ++i) {
            Event newEvent = new Event("event"+i,"u"+i,"person"+i,i,i,"country","city","felt"+i,i);
            events.add(newEvent);
        }
        for (int i =0; i<10; ++i) {
            Person newPerson = new Person("person"+i,"u"+i,"first"+i,"last"+i,"m");
            persons.add(newPerson);
        }
    }


    @AfterEach
    public void tearDown()  {
        cs.clear();

    }



    @Test // tests empty response and bad auth
    public void loadValid() {
        loadRequest = new LoadRequest();
        loadRequest.setEvents(events);
        loadRequest.setPeople(persons);
        loadRequest.setUsers(users);

        loadResult = loadService.load(loadRequest);

        assertTrue(loadResult.isSuccess());
    }


}

