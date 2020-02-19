package model;

/**
 * Model class for the user data
 */
public class user {
    private String id;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String gender;
    private String person_id;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * constructor
     * @param id identifier for the user
     * @param password user password
     * @param email email of the user
     * @param first_name person's first name
     * @param last_name person's last name
     * @param gender gander (m or f)
     * @param person_id unique identifier for the person
     */
    public user(String id, String password, String email, String first_name, String last_name, String gender, String person_id) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.person_id = person_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }
}
