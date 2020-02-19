package service;


import model.person;
import model.user;
import request.registerRequest;
import result.RegisterResult;

/**
 * This class takes in a RegisterRequest object and uses it to create a new
 * user and the linked person object in the data base,
 *
 * after this, we log the user in, and returns the authentication token for the user.
 *
 * Eventually, this will also generate 4 generation of ancestors for the user
 */
public class registerService {
    private boolean wasSuccesful;

    public boolean isWasSuccesful() {
        return wasSuccesful;
    }

    public void setWasSuccesful(boolean wasSuccesful) {
        this.wasSuccesful = wasSuccesful;
    }

    /**
     * This function will register the user, generate a linked person object
     * and return the parameters needed for authentication.
     * @param r is the register request object that contains the data for registration
     * @return the register result is returned  with the auth token, username and pas
     */
    public RegisterResult register(registerRequest r){
        wasSuccesful = true;
        RegisterResult result = new RegisterResult();

        //check if user with username already exists?

        //Generate some unique personId
        String newPersonId = "FIX-ME";


        //Create new user
        user us = new user(r.getUserName(),r.getPassword(),r.getEmail(),r.getFirstName(),r.getLastName(),r.getGender(),newPersonId);
        return result;
    }

    //Requests are from a jason string
    // if we have a json string comming in, we use gson, thats what the request is.

}
