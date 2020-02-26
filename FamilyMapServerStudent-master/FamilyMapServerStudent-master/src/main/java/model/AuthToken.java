package model;


import java.util.Objects;

/**
 * Model class for the AuthToken data
 */
public class AuthToken {
    private String token;
    private String Username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthToken)) return false;
        AuthToken AuthToken = (AuthToken) o;
        return Objects.equals(getToken(), AuthToken.getToken()) &&
                Objects.equals(getUsername(), AuthToken.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getUsername());
    }

    /**
     * constructor
     * @param token authentication token
     * @param Username unique User identifier
     */
    public AuthToken(String token, String Username) {
        this.token = token;
        this.Username = Username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }
}
