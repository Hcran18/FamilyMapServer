package Result;

import model.Event;

/**
 * Represents the result of an event operation.
 */
public class EventResult {
    /**
     * The array of events.
     */
    private Event[] data;

    /**
     * Indicates if operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the operation.
     */
    private String message;

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
