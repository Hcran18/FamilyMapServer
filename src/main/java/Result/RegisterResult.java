package Result;

/**
 * Represents the result of a user registration operation.
 */
public class RegisterResult {
    /**
     * The authentication token generated for the registered user.
     */
    private String authtoken;

    /**
     * The username of the registered user.
     */
    private String username;

    /**
     * The unique identifier of the person associated with the registered user.
     */
    private String personID;

    /**
     * Indicates whether the registration operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the registration result.
     */
    private String message;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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
