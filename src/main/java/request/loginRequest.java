package request;

/**
 * Data object for the log in request
 */
public class loginRequest {
    private String userID;
    private String password;

    public loginRequest(){};

    /**
     * constructor
     * @param userID unique string specifying the user
     * @param password user password
     */
    public loginRequest(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
