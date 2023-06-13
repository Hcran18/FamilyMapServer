package DataAccess;

import model.Authtoken;

import java.sql.*;

/**
 * Provides data access methods for interacting with the Authtoken table in the database.
 */
public class AuthtokenDao {
    /**
     * The database connection.
     */
    private final Connection conn;

    /**
     * Constructs an AuthtokenDao object.
     *
     * @param conn the database connection
     */
    public AuthtokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an authtoken into the database.
     *
     * @param authtoken the authtoken to create
     * @throws DataAccessException if an error occurs while inserting authtoken
     */
    public void insertAuthtoken(Authtoken authtoken) throws DataAccessException {
        String sql = "INSERT INTO auth_token (authtoken, username) " +
                "VALUES(?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an Authtoken into the database");
        }
    }

    /**
     * Finds an authtoken by its authtoken value.
     *
     * @param authtoken the value of the authtoken to find
     * @return the found Authtoken object
     * @throws DataAccessException if an error occurs while finding authtoken
     */
    public Authtoken findByAuthtoken(String authtoken) throws DataAccessException {
        Authtoken user;
        ResultSet rs;
        String sql = "SELECT * FROM auth_token WHERE authtoken = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();

            if(rs.next()) {
                user = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return user;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database by username");
        }
    }

    /**
     * Finds an authtoken by the associated username.
     *
     * @param username the associated username
     * @return the found Authtoken object
     * @throws DataAccessException if an error occurs while finding authtoken
     */
    public Authtoken findByUsername(String username) throws DataAccessException {
        Authtoken authtoken;
        ResultSet rs;
        String sql = "SELECT * FROM auth_token WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if(rs.next()) {
                authtoken = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return authtoken;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database by username");
        }
    }

    /**
     * Deletes an authtoken by its authtoken value.
     *
     * @param authtoken the value of the authtoken to delete
     * @throws DataAccessException if an error occurs while deleting authtoken
     */
    public void deleteByAuthtoken(String authtoken) throws DataAccessException {
        String sql = "DELETE FROM auth_token WHERE authtoken = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, authtoken);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an authtoken in the database by authtoken");
        }
    }

    /**
     * Deletes all authtokens with the given associated username.
     *
     * @param username the associated username
     * @throws DataAccessException if an error occurs while deleting the authtokens from the database
     */
    public void deleteByUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM auth_token WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an authtoken in the database by username");
        }
    }

    /**
     * Clears the Authtoken table
     * @throws DataAccessException if an error occurs while clearing.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM auth_token";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the auth_token table");
        }
    }
}
