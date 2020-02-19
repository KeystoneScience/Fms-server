package result;


/**
 * data structure to return the login result string
 */
public class LoginResult extends parentResult {
    private String autherizationToken;
    private  String userID;
    private String personID;

    public LoginResult(){};

    /**
     * constructor for the login response object
     * @param autherizationToken string specifying the authentication token
     * @param userID unique string specifying the user
     * @param personID Unique identifier for the person
     */
    public LoginResult(String autherizationToken, String userID, String personID) {
        this.autherizationToken = autherizationToken;
        this.userID = userID;
        this.personID = personID;
    }

    /**
     * login was successful response string
     * @return success response string
     */
    public String successResponse(){
        return null;
    }

    public String getAutherizationToken() {
        return autherizationToken;
    }

    public void setAutherizationToken(String autherizationToken) {
        this.autherizationToken = autherizationToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
