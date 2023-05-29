package model;

import java.util.Objects;

/**
 * Represents a person in the system.
 */
public class Person {
    /**
     * The unique identifier of the person.
     */
    private String personID;

    /**
     * The associated username of the person.
     */
    private String associatedUsername;

    /**
     * The first name of the person.
     */
    private String firstName;

    /**
     * The last name of the person.
     */
    private String lastName;

    /**
     * The gender of the person.
     */
    private String gender;

    /**
     * The unique identifier of the father of the person.
     */
    private String fatherID;

    /**
     * The unique identifier of the mother of the person.
     */
    private String motherID;

    /**
     * The unique identifier of the spouse of the person.
     */
    private String spouseID;

    /**
     * Constructs a Person object with the specified attributes.
     *
     * @param personID           the unique identifier of the person
     * @param associatedUsername the username associated with the person
     * @param firstName          the first name of the person
     * @param lastName           the last name of the person
     * @param gender             the gender of the person
     * @param fatherID           the unique identifier of the person's father
     * @param motherID           the unique identifier of the person's mother
     * @param spouseID           the unique identifier of the person's spouse
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Person person = (Person) o;

        return Objects.equals(this.personID, person.personID) &&
                Objects.equals(this.associatedUsername, person.associatedUsername) &&
                Objects.equals(this.firstName, person.firstName) &&
                Objects.equals(this.lastName, person.lastName) &&
                Objects.equals(this.gender, person.gender) &&
                Objects.equals(this.fatherID, person.fatherID) &&
                Objects.equals(this.motherID, person.motherID) &&
                Objects.equals(this.spouseID, person.spouseID);
    }
}
