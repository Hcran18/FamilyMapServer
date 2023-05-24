package DataAccess;

import model.User;

import java.sql.*;

/**
 * Provides data access methods for interacting with the User table in the database.
 */
public class UserDao {
    /**
     * The database connection used for interacting with the User table.
     */
    private final Connection conn;

    /**
     * Constructs a UserDao object with the specified database connection.
     *
     * @param conn the database connection
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a user into the database.
     *
     * @param user the user to create
     */
    public void insertUser(User user) {

    }

    /**
     * Finds a user by their ID.
     *
     * @param personID the ID of the user to find
     * @return the found User object, or null if not found
     */
    public User findByID(String personID) {
        return null;
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the found User object, or null if not found
     */
    public User findByUsername(String username) {
        return null;
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return the found User object, or null if not found
     */
    public User findByEmail(String email) {
        return null;
    }

    /**
     * Finds a user by their full name.
     *
     * @param firstName the first name of the user to find
     * @param lastName  the last name of the user to find
     * @return the found User object, or null if not found
     */
    public User findByFullName(String firstName, String lastName) {
        return null;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the user to update
     */
    public void updateUser(User user) {

    }

    /**
     * Deletes a user from the database.
     *
     * @param user the user to delete
     */
    public void delete(User user) {

    }

    /**
     * Deletes a user by their ID.
     *
     * @param personID the ID of the user to delete
     */
    public void deleteByID(String personID) {

    }

    /**
     * Clears the User table in the database.
     */
    public void clear() {

    }
}
