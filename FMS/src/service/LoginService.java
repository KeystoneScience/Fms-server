package service;

import request.LoginRequest;
import result.LoginResult;

/**
 * This serves the login request.
 */
public class LoginService {
    /**
     * login service object constructor
     */
    public LoginService(){};

    /**
     * Logs in the User
     * @param lr gets passed in a login request
     * @return returns the result of the login, if it was successful or not.
     */
    public LoginResult login(LoginRequest lr){
        LoginResult lR = new LoginResult();

        //FIXME


        return lR;
    }
}
