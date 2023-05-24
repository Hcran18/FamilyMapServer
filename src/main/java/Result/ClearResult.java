package Result;

/**
 * Represents the result of a clear operation.
 */
public class ClearResult {
    /**
     * The message associated with the clear operation result.
     */
    private String message;

    /**
     * Indicates whether the clear operation was successful.
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
