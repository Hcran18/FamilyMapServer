package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class responsible for managing the database connection.
 */
public class Database {
    /**
     * The connection object representing the database connection.
     */
    private Connection conn;

    /**
     * Opens a connection to the database.
     *
     * @return the opened database connection
     * @throws DataAccessException if unable to open the connection
     */
    public Connection openConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FMSDB.sqlite";

            conn = DriverManager.getConnection(CONNECTION_URL);

            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * Gets the current database connection. If the connection is not open, it opens a new connection.
     *
     * @return the current database connection
     * @throws DataAccessException if unable to open the connection
     */
    public Connection getConnection() throws DataAccessException {
        if (conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    /**
     * Closes the database connection.
     *
     * @param commit true to commit the changes, false to rollback the changes
     */
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

