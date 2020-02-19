package request;

/**
 * This class contains the information submitted from the client for queuing a registration
 */
public class registerRequest {
    /** the user's ID    */
    private String userName;
    /** the user's password    */
    private String password;
    /** the user's email*/
    private String email;
    /**  the user's first name   */
    private String firstName;
    /** the user's last name    */
    private String lastName;
    /** the user's gender*/
    private String gender;

    /**
     * Class Constructor
     */
    public registerRequest(){};


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
