package Result;

/**
 * Represents the result of a person-related operation by person ID.
 */
public class Person_PersonIDResult {
    /**
     * The username associated with the person.
     */
    private String associatedUsername;

    /**
     * The ID of the person.
     */
    private String personID;

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
     * The ID of the person's father.
     */
    private String fatherID;

    /**
     * The ID of the person's mother.
     */
    private String motherID;

    /**
     * The ID of the person's spouse.
     */
    private String spouseID;

    /**
     * Indicates whether the operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the operation result.
     */
    private String message;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
