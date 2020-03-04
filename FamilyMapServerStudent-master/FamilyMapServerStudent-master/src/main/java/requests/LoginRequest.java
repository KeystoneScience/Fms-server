package requests;

/**
 * Data object for the log in request
 */
public class LoginRequest {
    private String userName;
    private String password;

    public LoginRequest(){};

    /**
     * constructor
     * @param userName unique string specifying the User
     * @param password User password
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
