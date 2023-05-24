package Result;

/**
 * Represents the result of an event-related operation by event ID.
 */
public class Event_EventIDResult {
    /**
     * The username associated with the event.
     */
    private String associatedUsername;

    /**
     * The ID of the event.
     */
    private String eventID;

    /**
     * The ID of the person associated with the event.
     */
    private String personID;

    /**
     * The latitude coordinate of the event location.
     */
    private float latitude;

    /**
     * The longitude coordinate of the event location.
     */
    private float longitude;

    /**
     * The country where the event took place.
     */
    private String country;

    /**
     * The city where the event took place.
     */
    private String city;

    /**
     * The type of the event.
     */
    private String eventType;

    /**
     * The year when the event occurred.
     */
    private int year;

    /**
     * Indicates whether the event-related operation was successful.
     */
    private boolean success;

    /**
     * The message associated with the event-related operation result.
     */
    private String message;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
