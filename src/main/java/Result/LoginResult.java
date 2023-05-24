package Result;

/**
 * Represents the result of a user login operation.
 */
public class LoginResult {
    /**
     * The authentication token associated with the logged-in user.
     */
    private String authtoken;

    /**
     * The username of the logged-in user.
     */
    private String username;

    /**
     * The person ID associated with the logged-in user.
     */
    private String personID;

    /**
     * Indicates whether the login operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the login operation result.
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
