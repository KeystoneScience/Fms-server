package Client_Tests;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import Client_Information.ClientInfo;
import model.Event;
import model.Person;
import results.EventResult;
import results.LoginResult;
import results.PersonResult;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yo on 6/19/17.
 */
public class FamilyRelationshipTest {


    Event ev1 = new Event("MichalBirth","MichalAnnGibson","Mikey<3",10f,10f,"fake","fake Ville","walked Dog",1888);
    Event ev2 = new Event("MichalDeath","MichalAnnGibson","Mikey<3",10f,10f,"fake","fake Ville","walked Dog",1988);

    Event ev3 = new Event("NateBirth","KeystoneScience","n8theGreat",10f,10f,"fake","fake Ville","walked Dog",1898);


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


    }

    @After
    public void tearDown() {
        ClientInfo.getInstance().clearAll();
        ClientInfo.getInstance().filterDefaults();
        return;
    }

    @Test
    public void testFather() {
        assertEquals(ClientInfo.getInstance().getPersonToSideOfFamily().get(father),"dad");
    }

    @Test
    public void testMother() {
        assertEquals(ClientInfo.getInstance().getPersonToSideOfFamily().get(mother),"mom");
    }

    @Test
    public void testSpouse() {
        assertEquals(ClientInfo.getInstance().getPersonToSideOfFamily().get(spouse),"spouse of root");
    }





}

