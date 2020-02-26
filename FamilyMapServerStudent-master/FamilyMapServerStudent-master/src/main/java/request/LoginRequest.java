package request;

/**
 * Data object for the log in request
 */
public class LoginRequest {
    private String UserID;
    private String password;

    public LoginRequest(){};

    /**
     * constructor
     * @param UserID unique string specifying the User
     * @param password User password
     */
    public LoginRequest(String UserID, String password) {
        this.UserID = UserID;
        this.password = password;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
