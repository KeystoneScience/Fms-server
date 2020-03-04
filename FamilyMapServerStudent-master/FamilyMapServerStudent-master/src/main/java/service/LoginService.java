package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.sql.SQLException;
import java.util.UUID;

/**
 * This serves the login request.
 */
public class LoginService {
    private Database db = new Database();
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

        try {
            db.openConnection();
            UserDao ud = new UserDao(db.getConnection());
            User user = ud.find(lr.getuserName());

            if(user != null) {
                if (user.getPassword().equals(lr.getPassword())) { //Username and password check out
                    AuthTokenDao atd = new AuthTokenDao(db.getConnection());
                    String token = UUID.randomUUID().toString();
                    AuthToken at = new AuthToken(token,lr.getuserName());

                    atd.insertAuthToken(at);

                    lR.setuserName(user.getId());
                    lR.setpersonID(user.getPerson_id());
                    lR.setAutherizationToken(at.getToken());
                    lR.setSuccess(true);
                }
                else{
                    lR.setMessage("Error: Password Incorrect.");
                    lR.setSuccess(false);
                }
            }
            else{
                lR.setMessage("Error: Username not found.");
                lR.setSuccess(false);
            }
            db.closeConnection(true);


        } catch (DataAccessException | SQLException e) {
            lR.setMessage(e.getMessage());
            lR.setSuccess(false);
            try{
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                lR.setMessage(ex.getMessage());
            }
        }
        return lR;
    }
}
