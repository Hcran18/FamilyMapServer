package Result;

import model.Event;

/**
 * Represents the result of an event-related operation.
 */
public class EventResult {
    /**
     * The array of events returned as the result of the operation.
     */
    private Event[] data;

    /**
     * Indicates whether the event-related operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the event-related operation result.
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
