package model;

import java.util.Objects;

/**
 * Model class for the User data
 */
public class User {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User User = (User) o;
        return Objects.equals(getId(), User.getId()) &&
                Objects.equals(getPassword(), User.getPassword()) &&
                Objects.equals(getEmail(), User.getEmail()) &&
                Objects.equals(getFirst_name(), User.getFirst_name()) &&
                Objects.equals(getLast_name(), User.getLast_name()) &&
                Objects.equals(getGender(), User.getGender()) &&
                Objects.equals(getPerson_id(), User.getPerson_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassword(), getEmail(), getFirst_name(), getLast_name(), getGender(), getPerson_id());
    }

    /**
     * constructor
     * @param id identifier for the User
     * @param password User password
     * @param email email of the User
     * @param first_name Person's first name
     * @param last_name Person's last name
     * @param gender gander (m or f)
     * @param Person_id unique identifier for the Person
     */
    public User(String id, String password, String email, String first_name, String last_name, String gender, String Person_id) {
        this.userName = id;
        this.password = password;
        this.email = email;
        this.firstName = first_name;
        this.lastName = last_name;
        this.gender = gender;
        this.personID = Person_id;
    }

    public String getId() {
        return userName;
    }

    public void setId(String id) {
        this.userName = id;
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
        return firstName;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public String getLast_name() {
        return lastName;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    public String getPerson_id() {
        return personID;
    }

    public void setPerson_id(String Person_id) {
        this.personID = Person_id;
    }
}
