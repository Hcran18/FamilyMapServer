package Result;

import model.Event;

/**
 * Represents the result of an event-related operation.
 */
public class EventResult {
    private Event[] data;
    private boolean success;
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
