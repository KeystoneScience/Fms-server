package results;


/**
 * data structure to return the login result string
 */
public class LoginResult extends ParentResult {
    private String authToken;
    private String userName;
    private String personID;

    public LoginResult(){};

    /**
     * constructor for the login response object
     * @param authToken string specifying the authentication token
     * @param userName unique string specifying the User
     * @param personID Unique identifier for the Person
     */
    public LoginResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }


    public String getAutherizationToken() {
        return authToken;
    }

    public void setAutherizationToken(String authToken) {
        this.authToken = authToken;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getpersonID() {
        return personID;
    }

    public void setpersonID(String personID) {
        this.personID = personID;
    }
}
