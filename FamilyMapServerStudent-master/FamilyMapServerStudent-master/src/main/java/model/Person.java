package model;

import java.util.Objects;

/**
 * Model class for the Person data
 */
public class Person {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    public Person() {

    }

    public Person(String person_id, String id, String first_name, String last_name, String gender) {
        personID=person_id;
        associatedUsername = id;
        firstName=first_name;
        lastName = last_name;
        this.gender = gender;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person Person = (Person) o;
        return Objects.equals(getPerson_id(), Person.getPerson_id()) &&
                Objects.equals(getAssociated_Username(), Person.getAssociated_Username()) &&
                Objects.equals(getFirst_name(), Person.getFirst_name()) &&
                Objects.equals(getLast_name(), Person.getLast_name()) &&
                Objects.equals(getGender(), Person.getGender()) &&
                Objects.equals(getFather_id(), Person.getFather_id()) &&
                Objects.equals(getMother_id(), Person.getMother_id()) &&
                Objects.equals(getSpouse_id(), Person.getSpouse_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson_id(), getAssociated_Username(), getFirst_name(), getLast_name(), getGender(), getFather_id(), getMother_id(), getSpouse_id());
    }

    /**
     * constructor
     * @param Person_id unique identifier for the Person
     * @param associated_Username Username associated to the Person
     * @param first_name Person's first name
     * @param last_name Person's last name
     * @param gender gender (m or f)
     * @param father_id unique identifier for the father
     * @param mother_id unique identifier for the mother
     * @param spouse_id unique identifier for the spouse
     */
    public Person(String Person_id, String associated_Username, String first_name, String last_name, String gender, String father_id, String mother_id, String spouse_id) {
        this.personID = Person_id;
        this.associatedUsername = associated_Username;
        this.firstName = first_name;
        this.lastName = last_name;
        this.gender = gender;
        this.fatherID = father_id;
        this.motherID = mother_id;
        this.spouseID = spouse_id;
    }

    public String getPerson_id() {
        return personID;
    }

    public void setPerson_id(String Person_id) {
        this.personID = Person_id;
    }

    public String getAssociated_Username() {
        return associatedUsername;
    }

    public void setAssociated_Username(String associated_Username) {
        this.associatedUsername = associated_Username;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather_id() {
        return fatherID;
    }

    public void setFather_id(String father_id) {
        this.fatherID = father_id;
    }

    public String getMother_id() {
        return motherID;
    }

    public void setMother_id(String mother_id) {
        this.motherID = mother_id;
    }

    public String getSpouse_id() {
        return spouseID;
    }

    public void setSpouse_id(String spouse_id) {
        this.spouseID = spouse_id;
    }
}
