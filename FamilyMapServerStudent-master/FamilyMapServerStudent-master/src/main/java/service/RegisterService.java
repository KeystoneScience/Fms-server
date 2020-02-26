package service;


import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import dao.PersonDao;
import model.Event;
import model.Person;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.sql.SQLException;
import java.util.Random;

/**
 * This class takes in a RegisterRequest object and uses it to create a new
 * User and the linked Person object in the data base,
 *
 * after this, we log the User in, and returns the authentication token for the User.
 *
 * Eventually, this will also generate 4 generation of ancestors for the User
 */
public class RegisterService {
    private Database db;
    private boolean wasSuccesful;

    public boolean isWasSuccesful() {
        return wasSuccesful;
    }

    public void setWasSuccesful(boolean wasSuccesful) {
        this.wasSuccesful = wasSuccesful;
    }

    /**
     * This function will register the User, generate a linked Person object
     * and return the parameters needed for authentication.
     * @param r is the register request object that contains the data for registration
     * @return the register result is returned  with the auth token, Username and pas
     */
    public RegisterResult register(RegisterRequest r){
        wasSuccesful = true;
        RegisterResult result = new RegisterResult();


        //Generate some unique PersonId
        String newPersonId = "FIX-ME";


        //Create new User
        User us = new User(r.getUserName(),r.getPassword(),r.getEmail(),r.getFirstName(),r.getLastName(),r.getGender(),newPersonId);
        return result;
    }

    private void GenerateGenerations(){


    }



    private void GenerateCouple(){}

    private void MarriageOfTwo(String spouse1, String spouse2, String childID) throws DataAccessException, SQLException {
        Random r = new Random();
        EventDao edao = new EventDao(db.openConnection());


        Event childBirth = edao.findBirth(childID);

        //Generates reasonable birth and death based off of child birth.
        int marriageYear = childBirth.getYear() - 1 - r.nextInt(5);

        float latitude = 0.0f;
        float longitude = 0.0f;
        String id1 = "";
        String id2 = "";

        String country ="";
        String city = "";


        Event spouse1Event = new Event(id1, childBirth.getAssociated_Username(),spouse1,latitude,longitude,country, city, "marriage", marriageYear);
        Event spouse2Event = new Event(id2, childBirth.getAssociated_Username(),spouse2,latitude,longitude,country, city, "marriage", marriageYear);

        edao.insertEvent(spouse1Event);
        edao.insertEvent(spouse2Event);

    }

    private void GiveBornAndDead(String personID, String childID) throws DataAccessException, SQLException {
        Random r = new Random();

        PersonDao pdao = new PersonDao(db.openConnection());
        EventDao edao = new EventDao(db.openConnection());


        Event childBirth = edao.findBirth(childID);



        Person person = pdao.find(personID);
        Person child = pdao.find(childID);

        //Generates reasonable birth and death based off of child birth.
        int birthYear = childBirth.getYear() - 23 - r.nextInt(10);
        int deathYear = childBirth.getYear() + 10 + r.nextInt(30);

        float birthLatitude = 0.0f;
        float birthLongitude = 0.0f;
        String birthID = "";
        String birthCountry ="";
        String birthCity = "";

        float deathLatitude = 0.0f;
        float deathLongitude = 0.0f;
        String deathID = "";
        String deathCountry ="";
        String deathCity = "";


        Event birth = new Event(birthID, child.getAssociated_Username(),personID,birthLatitude,birthLongitude,birthCountry, birthCity, "birth", birthYear);
        Event death = new Event(deathID, child.getAssociated_Username(),personID,deathLatitude,deathLongitude,deathCountry, deathCity, "death", deathYear);

        edao.insertEvent(birth);
        edao.insertEvent(death);
    }

    //Requests are from a jason string
    // if we have a json string comming in, we use gson, thats what the request is.

}
