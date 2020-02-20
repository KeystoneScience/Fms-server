package service;


import model.User;
import request.RegisterRequest;
import result.RegisterResult;

/**
 * This class takes in a RegisterRequest object and uses it to create a new
 * User and the linked Person object in the data base,
 *
 * after this, we log the User in, and returns the authentication token for the User.
 *
 * Eventually, this will also generate 4 generation of ancestors for the User
 */
public class RegisterService {
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

        //check if User with Username already exists?

        //Generate some unique PersonId
        String newPersonId = "FIX-ME";


        //Create new User
        User us = new User(r.getUserName(),r.getPassword(),r.getEmail(),r.getFirstName(),r.getLastName(),r.getGender(),newPersonId);
        return result;
    }

    //Requests are from a jason string
    // if we have a json string comming in, we use gson, thats what the request is.

}
