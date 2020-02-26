package result;


/**
 * data structure to return the login result string
 */
public class LoginResult extends ParentResult {
    private String autherizationToken;
    private String UserID;
    private String PersonID;

    public LoginResult(){};

    /**
     * constructor for the login response object
     * @param autherizationToken string specifying the authentication token
     * @param UserID unique string specifying the User
     * @param PersonID Unique identifier for the Person
     */
    public LoginResult(String autherizationToken, String UserID, String PersonID) {
        this.autherizationToken = autherizationToken;
        this.UserID = UserID;
        this.PersonID = PersonID;
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
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String PersonID) {
        this.PersonID = PersonID;
    }
}
