package DataAccess;

import model.Event;
import model.Person;

import java.sql.*;
import java.util.*;

/**
 * Provides data access methods for interacting with the Event table in the database.
 */
public class EventDAO {
    /**
     * The database connection used for interacting with the Event table.
     */
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
        String sql = "INSERT INTO event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
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
        String sql = "SELECT * FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
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
     * @param associatedUsername the username of the user
     * @return an array of Event objects associated with the user
     */
    public Event[] findAllForUser(String associatedUsername) throws DataAccessException {
        List<Event> events = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM event WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Event event = new Event(
                        rs.getString("eventID"),
                        rs.getString("associatedUsername"),
                        rs.getString("personID"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        rs.getInt("year")
                );
                events.add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding events in the database for a user");
        }

        return events.toArray(new Event[0]);
    }

    // TODO JavaDoc
    public int findBirthYear(String personID) throws DataAccessException {
        String sql = "SELECT year FROM event WHERE personID = ? AND eventType = 'Birth';";
        ResultSet rs;
        int birthYear = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                birthYear = rs.getInt("year");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding birth year in the database");
        }

        return birthYear;
    }

    public int findDeathYear(String personID) throws DataAccessException {
        String sql = "SELECT year FROM event WHERE personID = ? AND eventType = 'Death';";
        ResultSet rs;
        int deathYear = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                deathYear = rs.getInt("year");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding death year in the database");
        }

        return deathYear;
    }

    public void updateBirthByID(String personID, int year) throws DataAccessException {
        String sql = "UPDATE event SET year = ? WHERE personID = ? AND eventType = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, year);
            stmt.setString(2, personID);
            stmt.setString(3, "Birth");

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating death year");
        }
    }

    public void updateDeathByID(String personID, int year) throws DataAccessException {
        String sql = "UPDATE event SET year = ? WHERE personID = ? AND eventType = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, year);
            stmt.setString(2, personID);
            stmt.setString(3, "Death");

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating death year");
        }
    }

    /**
     * Clears all events for their associatedUserName.
     *
     * @param username the associatedUsername of the events to delete
     */
    public void clearByUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM event WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing events by associatedUsername");
        }
    }

    /**
     * Clears the Event table in the database.
     *
     * @throws DataAccessException if an error occurs while clearing the event table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }
}
