package Result;

import model.Person;

/**
 * Represents the result of a person-related operation.
 */
public class PersonResult {
    private Person[] data;
    private boolean success;
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
