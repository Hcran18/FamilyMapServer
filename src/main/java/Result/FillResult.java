package Result;

/**
 * Represents the result of a data filling operation.
 */
public class FillResult {
    /**
     * The message associated with the filling operation result.
     */
    private String message;

    /**
     * Indicates whether the filling operation was successful.
     */
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
