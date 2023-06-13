package model;

import java.util.Objects;

/**
 * Represents an authentication token associated with a user.
 */
public class Authtoken {
    /**
     * The authentication token.
     */
    private String authtoken;

    /**
     * The username associated with the authentication token.
     */
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

    /**
     * Compares this Authtoken object with an object for equality.
     *
     * @param o the object to compare for equality
     * @return true if equal false otherwise
     */
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

        Authtoken authtoken = (Authtoken) o;

        return Objects.equals(this.authtoken, authtoken.authtoken) &&
                Objects.equals(this.username, authtoken.username);
    }
}
