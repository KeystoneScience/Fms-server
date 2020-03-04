package requests;

/**
 * This class contains the information submitted from the client for queuing a registration
 */
public class RegisterRequest {
    //FIXME these variables, and in all requests, need to match what is given on the server info datasheet so that they may work with JSON
    /** the User's ID    */
    private String userName;
    /** the User's password    */
    private String password;
    /** the User's email*/
    private String email;
    /**  the User's first name   */
    private String firstName;
    /** the User's last name    */
    private String lastName;
    /** the User's gender*/
    private String gender;

    /**
     * Class Constructor
     */
    public RegisterRequest(){};

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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
