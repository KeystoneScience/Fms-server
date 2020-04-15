package Client_Tests;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import Activitys.SearchActivity;
import Client_Information.ClientInfo;
import model.Event;
import model.Person;
import results.EventResult;
import results.LoginResult;
import results.PersonResult;
import org.junit.After;
import org.junit.Test;



import static org.junit.Assert.*;

/**
 * Created by yo on 6/19/17.
 */
public class SearchTest {


    Event ev1 = new Event("MichalBirth","MichalAnnGibson","Mikey<3",10f,10f,"fake","fake Ville","birth",1888);
    Event ev2 = new Event("MichalDeath","MichalAnnGibson","Mikey<3",10f,10f,"fake","fake Ville","death",1988);

    Event ev3 = new Event("NateBirth","KeystoneScience","n8theGreat",10f,10f,"fake","fake Ville","birth",1898);


    Person father = new Person("01123581321","CoolCoderGuy","Dad","person","m",null,null,"mother999");
    Person child = new Person("Mikey<3","MichalAnnGibson","Michal","Gibson","f","01123581321","mother999","n8theGreat");
    Person mother = new Person("mother999","motherDear","mama","Gibson","f",null,null,"01123581321");
    Person spouse = new Person("n8theGreat","KeystoneScience","Nate","Stone","m",null,null,"Mikey<3");


    PersonResult pr = new PersonResult();
    EventResult evRes = new EventResult();
    LoginResult lr = new LoginResult();
    @Before
    public void setUp() {
        List<Person> people = new ArrayList<>();
        people.add(father);
        people.add(mother);
        people.add(child);
        people.add(spouse);

        List<Event> events = new ArrayList<Event>();
        events.add(ev1);
        events.add(ev2);
        events.add(ev3);


        lr.setpersonID(child.getPerson_id());
        pr.setPeople(people);
        evRes.setEvents(events);

        ClientInfo.getInstance().setEventResult(evRes);
        ClientInfo.getInstance().setLoginResult(lr);
        ClientInfo.getInstance().setPersonResult(pr);
        ClientInfo.getInstance().filterEvents();
    }

    @After
    public void tearDown() {
        ClientInfo.getInstance().clearAll();
        ClientInfo.getInstance().filterDefaults();
        return;
    }

    @Test
    public void eventSearch() {
        SearchActivity searchActivity =new SearchActivity();
        assertTrue(searchActivity.searchEventTestResults("birth").contains(ev1));
        assertTrue(searchActivity.searchEventTestResults("biRTh").contains(ev1));
        assertFalse(searchActivity.searchEventTestResults("biRTh").contains(ev2)); //Test Case sensitivity
        assertTrue(searchActivity.searchEventTestResults("birth").contains(ev3));
        assertTrue(searchActivity.searchEventTestResults("death").contains(ev2));
    }

    @Test
    public void personSearch() {
        SearchActivity searchActivity =new SearchActivity();

        //The empty search should contain everything
        assertTrue(searchActivity.searchPersonTestResults("").contains(child));
        assertTrue(searchActivity.searchPersonTestResults("").contains(spouse));
        assertTrue(searchActivity.searchPersonTestResults("").contains(mother));
        assertTrue(searchActivity.searchPersonTestResults("").contains(father));




        assertTrue(searchActivity.searchPersonTestResults("m").contains(child));
        assertFalse(searchActivity.searchPersonTestResults("m").contains(spouse));
        assertTrue(searchActivity.searchPersonTestResults("mIcHaL").contains(child));

    }







}

