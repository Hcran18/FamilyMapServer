package model;

/**
 * Represents an authentication token associated with a user.
 */
public class Authtoken {
    private String authtoken;
    private String username;

    /**
     * Constructs an Authtoken object with the specified token and username.
     *
     * @param authtoken the authentication token
     * @param username  the associated username
     */
    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

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
}
