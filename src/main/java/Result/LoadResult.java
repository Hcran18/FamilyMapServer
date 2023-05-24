package Result;

/**
 * Represents the result of a data loading operation.
 */
public class LoadResult {
    /**
     * The message associated with the loading operation result.
     */
    private String message;

    /**
     * Indicates whether the loading operation was successful.
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
