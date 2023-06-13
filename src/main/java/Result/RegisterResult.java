package Result;

/**
 * Represents the result of a user registration operation.
 */
public class RegisterResult {
    /**
     * The authentication token generated for the user.
     */
    private String authtoken;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The unique identifier of the user.
     */
    private String personID;

    /**
     * Indicates if the operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the operation.
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
