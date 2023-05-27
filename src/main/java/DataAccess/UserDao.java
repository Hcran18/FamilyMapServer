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
     * @throws DataAccessException if an error occurs while inserting a User
     */
    public void insertUser(User user) throws DataAccessException{
        String sql = "INSERT INTO user (username, password, email, firstName, lastName, gender, personID) " +
                "VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a User into the database");
        }
    }

    /**
     * Finds a user by their ID.
     *
     * @param personID the ID of the user to find
     * @throws DataAccessException if an error occurs while finding the User by ID
     * @return the found User object, or null if not found
     */
    public User findByID(String personID) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM user WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if(rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a User in the database by personID");
        }
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
     * @throws DataAccessException if an error occurs while clearing the user table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM user";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }
}
