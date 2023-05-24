package Result;

import model.Person;

/**
 * Represents the result of a person-related operation.
 */
public class PersonResult {
    /**
     * An array of Person objects containing the data related to the operation.
     */
    private Person[] data;

    /**
     * Indicates whether the operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the operation result.
     */
    private String message;

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
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
