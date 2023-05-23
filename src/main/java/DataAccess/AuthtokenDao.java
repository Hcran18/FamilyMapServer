package DataAccess;

import model.Authtoken;

import java.sql.*;

/**
 * Provides data access methods for interacting with the Authtoken table in the database.
 */
public class AuthtokenDao {
    private final Connection conn;

    /**
     * Constructs an AuthtokenDao object with the specified database connection.
     *
     * @param conn the database connection
     */
    public AuthtokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new authtoken in the database.
     *
     * @param authtoken the authtoken to create
     */
    public void createAuthtoken(Authtoken authtoken) {

    }

    /**
     * Finds an authtoken by its token value.
     *
     * @param authtoken the token value of the authtoken to find
     * @return the found Authtoken object, or null if not found
     */
    public Authtoken findByAuthtoken(String authtoken) {
        return null;
    }

    /**
     * Finds an authtoken by the associated username.
     *
     * @param username the associated username
     * @return the found Authtoken object, or null if not found
     */
    public Authtoken findByUsername(String username) {
        return null;
    }

    /**
     * Updates an existing authtoken in the database.
     *
     * @param authtoken the authtoken to update
     */
    public void updateAuthtoken(Authtoken authtoken) {

    }

    /**
     * Deletes an authtoken from the database.
     *
     * @param authtoken the authtoken to delete
     */
    public void deleteAuthtoken(Authtoken authtoken) {

    }

    /**
     * Deletes an authtoken by its token value.
     *
     * @param authtoken the token value of the authtoken to delete
     */
    public void deleteByAuthtoken(String authtoken) {

    }

    /**
     * Clears the Authtoken table in the database.
     */
    public void clear() {

    }
}
