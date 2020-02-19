package model;


/**
 * Model class for the authToken data
 */
public class authToken {
    private String token;
    private String username;

    /**
     * constructor
     * @param token authentication token
     * @param username unique user identifier
     */
    public authToken(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
