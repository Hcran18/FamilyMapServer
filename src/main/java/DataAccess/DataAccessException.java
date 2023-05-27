package DataAccess;

/**
 * Custom exception class for data access-related errors.
 */
public class DataAccessException extends Exception {
    /**
     * Constructs a new DataAccessException with the specified error message.
     *
     * @param message the error message
     */
    public DataAccessException(String message) {
        super(message);
    }
}
