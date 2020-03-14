package dao;


/*
● For your test cases, create a positive and a negative test case for the insertion and
retrieval methods of each DAO class and a single positive test case for the clear
method of each DAO class. This means we are looking for a minimum of 10 good
quality test cases, 5 for each of the 2 DAO classes.
● For a positive test case, we want the test case to try using a DAO class method where
the SQL operation works successfully. For a negative test case, we want the test case to
try using a DAO class method where the SQL operation does not work successfully and
check to see that the method handles the problem correctly
 */
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDAOTest {
    private Database db;
    private Person bestPerson;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new Event with random data
        bestPerson = new Person("01123581321","CoolCoderGuy","Elon","Musk","m","420","69","null");
    }

    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.insertPerson(bestPerson);
            //So lets use a find method to get the Event that we just put in back out
            compareTest = pDao.find(bestPerson.getPerson_id());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(bestPerson, compareTest);

    }

    @Test
    public void insertFail() throws Exception {
        //lets do this test again but this time lets try to make it fail

        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //if we call the method the first time it will insert it successfully
            pDao.insertPerson(bestPerson);

            //but our sql table is set up so that "EventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            pDao.insertPerson(bestPerson);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);

        //Since we know our database encountered an error, both instances of insert should have been
        //rolled back. So for added security lets make one more quick check using our find function
        //to make sure that our Event is not in the database
        //Set our compareTest to an actual Event
        Person compareTest = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //and then get something back from our find. If the Event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = pDao.find(bestPerson.getPerson_id());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    @Test
    public void clearPass()throws DataAccessException {
        Person compareTest = null;
        Person firstCompare = null;

        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(bestPerson);
            firstCompare = pDao.find(bestPerson.getPerson_id());

            pDao.clearTable();

            compareTest = pDao.find(bestPerson.getPerson_id());
            db.closeConnection(true);
        } catch (DataAccessException | SQLException e) {
            db.closeConnection(false);
        }

        assertNull(compareTest);
        assertNotEquals(bestPerson, compareTest);
        assertEquals(bestPerson,firstCompare);
    }

    @Test
    public void retrevalPass() throws DataAccessException {
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.insertPerson(bestPerson);
            //So lets use a find method to get the Event that we just put in back out
            compareTest = pDao.find(bestPerson.getPerson_id());
            db.closeConnection(true);
        } catch (DataAccessException | SQLException e) {
            db.closeConnection(false);
        }

        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void retrevalFail()throws DataAccessException {
        Person compareTest = null;

        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(bestPerson);

            compareTest = pDao.find("non-existent ID");
            db.closeConnection(true);
        } catch (DataAccessException | SQLException e) {
            db.closeConnection(false);
        }

        assertNull(compareTest);
        assertNotEquals(bestPerson, compareTest);
    }

}
