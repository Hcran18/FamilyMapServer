package DataAccess;

import model.Event;

import java.sql.*;
import java.util.*;

/**
 * Provides data access methods for interacting with the Event table in the database.
 */
public class EventDAO {
    private final Connection conn;

    /**
     * Constructs an EventDAO object with the specified database connection.
     *
     * @param conn the database connection
     */
    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an event into the database.
     *
     * @param event the event to insert
     * @throws DataAccessException if an error occurs while inserting the event
     */
    public void insertEvent(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds an event by its ID.
     *
     * @param eventID the ID of the event to find
     * @return the found Event object, or null if not found
     * @throws DataAccessException if an error occurs while finding the event
     */
    public Event findByID(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }

    }

    /**
     * Finds all events associated with a specific user.
     *
     * @param username the username of the user
     * @return a list of Event objects associated with the user
     */
    public List<Event> findAllForUser(String username) {
        return Collections.emptyList();
    }

    /**
     * Finds all events associated with a specific person.
     *
     * @param personID the ID of the person
     * @return a list of Event objects associated with the person
     */
    public List<Event> findAllForPerson(String personID) {
        return Collections.emptyList();
    }

    /**
     * Finds events by event type.
     *
     * @param eventType the event type
     * @return a list of Event objects matching the event type
     */
    public List<Event> findByEventType(String eventType) {
        return Collections.emptyList();
    }

    /**
     * Finds events by location coordinates.
     *
     * @param latitude  the latitude coordinate
     * @param longitude the longitude coordinate
     * @return a list of Event objects near the specified location
     */
    public List<Event> findByLocation(float latitude, float longitude) {
        return Collections.emptyList();
    }

    /**
     * Finds events by country.
     *
     * @param country the country
     * @return a list of Event objects in the specified country
     */
    public List<Event> findByCountry(String country) {
        return Collections.emptyList();
    }

    /**
     * Finds events by city.
     *
     * @param city the city
     * @return a list of Event objects in the specified city
     */
    public List<Event> findByCity(String city) {
        return Collections.emptyList();
    }

    /**
     * Finds events by year.
     *
     * @param year the year
     * @return a list of Event objects in the specified year
     */
    public List<Event> findByYear(int year) {
        return Collections.emptyList();
    }

    /**
     * Updates an existing event in the database.
     *
     * @param event the event to update
     */
    public void updateEvent(Event event) {

    }

    /**
     * Deletes an event from the database.
     *
     * @param event the event to delete
     */
    public void deleteEvent(Event event) {

    }

    /**
     * Deletes an event by its ID.
     *
     * @param eventID the ID of the event to delete
     */
    public void deleteEventByID(String eventID) {

    }

    /**
     * Clears the Event table in the database.
     *
     * @throws DataAccessException if an error occurs while clearing the event table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }
}
