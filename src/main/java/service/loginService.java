package service;

import request.loginRequest;
import result.LoginResult;

/**
 * This serves the login request.
 */
public class loginService {
    /**
     * login service object constructor
     */
    public loginService(){};

    /**
     * Logs in the user
     * @param lr gets passed in a login request
     * @return returns the result of the login, if it was successful or not.
     */
    public LoginResult login(loginRequest lr){
        LoginResult lR = new LoginResult();

        //FIXME


        return lR;
    }
}
