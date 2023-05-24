package model;

/**
 * Represents an event in the system.
 */
public class Event {
    /**
     * The unique identifier of the event.
     */
    private String eventID;

    /**
     * The associated username of the event.
     */
    private String associatedUsername;

    /**
     * The unique identifier of the person associated with the event.
     */
    private String personID;

    /**
     * The latitude coordinate of the event's location.
     */
    private float latitude;

    /**
     * The longitude coordinate of the event's location.
     */
    private float longitude;

    /**
     * The country where the event occurred.
     */
    private String country;

    /**
     * The city where the event occurred.
     */
    private String city;

    /**
     * The type or description of the event.
     */
    private String eventType;

    /**
     * The year when the event occurred.
     */
    private int year;

    /**
     * Constructs an Event object with the specified attributes.
     *
     * @param eventID           the unique identifier of the event
     * @param associatedUsername the username associated with the event
     * @param personID          the unique identifier of the person associated with the event
     * @param latitude          the latitude coordinate of the event's location
     * @param longitude         the longitude coordinate of the event's location
     * @param country           the country where the event occurred
     * @param city              the city where the event occurred
     * @param eventType         the type/category of the event
     * @param year              the year when the event occurred
     */
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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
}
