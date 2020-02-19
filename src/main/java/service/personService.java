package service;

import model.person;
import result.personResult;

import java.util.ArrayList;

/**
 * This class serves the person request.
 */
public class personService {
    private boolean success;

    /**
     * object constructor
     */
    public personService(){};

    /**
     * returns person from specified personID and userID
     * @param personID Unique identifier for the person
     * @param userID unique string specifying the user
     * @return success or error results of finding the person
     */
    public personResult findPerson(String personID, String userID){

        return null;
    }

    /**
     * returns a list of people linked to a given user.
     * @param authToken used to verify
     * @return list of people
     */
    public personResult persons(String authToken){
        return null;
    }

}
