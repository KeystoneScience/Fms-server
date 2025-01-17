package request;

/**
 * This class contains the information submitted from the client for queuing a registration
 */
public class RegisterRequest {
    /** the User's ID    */
    private String UserName;
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


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
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
