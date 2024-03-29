package DataAccess;

import model.User;

import java.sql.*;

/**
 * Provides data access methods for interacting with the User table in the database.
 */
public class UserDao {
    /**
     * The database connection.
     */
    private final Connection conn;

    /**
     * Constructs a UserDao object.
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
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the found User object, or null if not found
     * @throws DataAccessException if an error occurs while finding User
     */
    public User findByUsername(String username) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM user WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
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
     * Deletes a user from the database.
     *
     * @param username the user to delete
     * @throws DataAccessException if an error occurs while deleting a User
     */
    public void delete(String username) throws DataAccessException {
        String sql = "DELETE FROM user WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a User in the database by username");
        }
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
