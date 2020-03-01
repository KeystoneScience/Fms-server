package result;

import model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *This class is used for the purpose of generating an array of Persons
 * so that in may be converted into JSON to serve the client.
 */

public class PersonResult extends ParentResult{
    private List<Person> people = new ArrayList<>();
    private String assoUserName;
    private String PersonID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;



    public String getAssoUserName() {
        return assoUserName;
    }

    public void setAssoUserName(String assoUserName) {
        this.assoUserName = assoUserName;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String PersonID) {
        this.PersonID = PersonID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }


    public PersonResult(){};

    /**
     * Constructor for the PersonID api with all the necessary data bins.
     * @param assoUserName Username associated to the Person
     * @param PersonID Unique identifier for the Person
     * @param firstName first name
     * @param lastName last name
     * @param gender gender (m or f)
     * @param fatherID unique identifier for the father
     * @param motherID unique identifier for the mother
     * @param spouseID unique identifier for the spouse
     */
    public PersonResult(String assoUserName, String PersonID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.assoUserName = assoUserName;
        this.PersonID = PersonID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }


    public void fillPerson(Person p){
        assoUserName = p.getAssociated_Username();
        PersonID = p.getPerson_id();
        firstName = p.getFirst_name();
        lastName = p.getLast_name();
        gender = p.getGender();
        fatherID = p.getFather_id();
        motherID = p.getMother_id();
        spouseID = p.getSpouse_id();
    }

    //maybe use a list here?
    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    /**
     * Function to return the successful response for PersonID api.
     * (More to add here later)
     * @return type string, returns response
     */
    public String successfulResponsePersonID(){

        //I will construct this string with a string builder, and return it
        return null;
    }

    /**
     * Function to return the successful response for Person api.
     * (More to add here later)
     * @return type string, returns response
     */
    public String successfulResponsePerson(){

        //I will construct this string with a string builder, and return it
        return null;
    }
}
