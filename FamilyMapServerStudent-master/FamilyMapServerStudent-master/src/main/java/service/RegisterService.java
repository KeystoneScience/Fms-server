package service;


import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import request.FillRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.FillResult;
import result.LoginResult;
import result.RegisterResult;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

/**
 * This class takes in a RegisterRequest object and uses it to create a new
 * User and the linked Person object in the data base,
 *
 * after this, we log the User in, and returns the authentication token for the User.
 *
 * Eventually, this will also generate 4 generation of ancestors for the User
 */
public class RegisterService {
    private Database db = new Database();
    private boolean wasSuccesful;

    public boolean isWasSuccesful() {
        return wasSuccesful;
    }

    public void setWasSuccesful(boolean wasSuccesful) {
        this.wasSuccesful = wasSuccesful;
    }


    public RegisterService() {}

    /**
     * This function will register the User, generate a linked Person object
     * and return the parameters needed for authentication.
     * @param r is the register request object that contains the data for registration
     * @return the register result is returned  with the auth token, Username and pas
     */
    public RegisterResult register(RegisterRequest r){
        wasSuccesful = true;
        RegisterResult result = new RegisterResult();


        try{
            db.openConnection();
            UserDao udao = new UserDao(db.getConnection());
            PersonDao pdao = new PersonDao(db.getConnection());
            EventDao edao = new EventDao(db.getConnection());
            AuthTokenDao adao = new AuthTokenDao(db.getConnection());
            if(!r.getGender().toLowerCase().equals("m") &&  !r.getGender().toLowerCase().equals("f")){
                throw new DataAccessException("Error: please use a valid gender.");
            }
            String newID = UUID.randomUUID().toString();
            User newUser = new User(r.getuserName(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(),newID);

            udao.insertUser(newUser);
            db.closeConnection(true);
           // udao.find(newUser.getId());
            FillRequest fr = new FillRequest(newUser.getId(),4);

            FillService fs = new FillService();

            FillResult fR = fs.fill(fr);

            LoginService ls = new LoginService();
            LoginRequest lr = new LoginRequest();
            lr.setPassword(newUser.getPassword());
            lr.setuserName(newUser.getId());
            LoginResult lR;
            lR = ls.login(lr);
            result.setauthToken(lR.getAutherizationToken());
            result.setuserName(newUser.getId());
            result.setpersonID(fR.getUserPerson());

            result.setSuccess(true);
            //db.closeConnection(true);



        } catch (DataAccessException | SQLException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                result.setMessage(ex.getMessage());
            }
        }

        return result;
    }



    //Requests are from a jason string
    // if we have a json string comming in, we use gson, thats what the request is.

}
