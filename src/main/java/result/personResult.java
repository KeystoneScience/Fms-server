package result;

import model.person;

import java.util.ArrayList;

/**
 *
 *This class is used for the purpose of generating an array of persons
 * so that in may be converted into JSON to serve the client.
 */

public class personResult extends parentResult{
    private ArrayList<person> people;
    private String assoUserName;
    private String personID;
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
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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


    public personResult(){};

    /**
     * Constructor for the PersonID api with all the necessary data bins.
     * @param assoUserName Username associated to the person
     * @param personID Unique identifier for the person
     * @param firstName first name
     * @param lastName last name
     * @param gender gender (m or f)
     * @param fatherID unique identifier for the father
     * @param motherID unique identifier for the mother
     * @param spouseID unique identifier for the spouse
     */
    public personResult(String assoUserName, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.assoUserName = assoUserName;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Constructor for the person api path
     * @param people ArrayList
     */
    public personResult(ArrayList<person> people) {
        this.people = people;
    }

    //maybe use a list here?
    public ArrayList<person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<person> people) {
        this.people = people;
    }

    /**
     * Function to return the successful response for personID api.
     * (More to add here later)
     * @return type string, returns response
     */
    public String successfulResponsePersonID(){

        //I will construct this string with a string builder, and return it
        return null;
    }

    /**
     * Function to return the successful response for person api.
     * (More to add here later)
     * @return type string, returns response
     */
    public String successfulResponsePerson(){

        //I will construct this string with a string builder, and return it
        return null;
    }
}
