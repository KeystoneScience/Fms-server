package service;

import result.PersonResult;

/**
 * This class serves the Person request.
 */
public class PersonService {
    private boolean success;

    /**
     * object constructor
     */
    public PersonService(){};

    /**
     * returns Person from specified PersonID and UserID
     * @param PersonID Unique identifier for the Person
     * @param UserID unique string specifying the User
     * @return success or error results of finding the Person
     */
    public PersonResult findPerson(String PersonID, String UserID){

        return null;
    }

    /**
     * returns a list of people linked to a given User.
     * @param AuthToken used to verify
     * @return list of people
     */
    public PersonResult Persons(String AuthToken){
        return null;
    }

}
