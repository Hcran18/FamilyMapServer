package Request;

/**
 * Represents a request for user login.
 */
public class LoginRequest {
    /**
     * The username for the request.
     */
    private String username;

    /**
     * The password for the request.
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
