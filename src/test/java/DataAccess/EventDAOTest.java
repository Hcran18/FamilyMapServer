package DataAccess;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

// Tests fpr the EventDAO class
public class EventDAOTest {
    private Database db;
    private Event bestEvent;
    private EventDAO eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Create the database
        db = new Database();

        // Create a new event
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        // Open the connection to the database
        Connection conn = db.getConnection();

        // Give the connection to the eventDao
        eDao = new EventDAO(conn);

        // Clear the database
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Close the connection and don't commit any changes
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Attempt to insert the event into the database
        eDao.insertEvent(bestEvent);

        // Find the event that we tried to insert
        Event compareTest = eDao.findByID(bestEvent.getEventID());

        // Assert that what we found in not null
        assertNotNull(compareTest);

        // Assert that the event we found is the same as the one we tried to insert
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Attempt to insert an event
        eDao.insertEvent(bestEvent);

        // Try to insert the event again which should throw a DataAccessException
        assertThrows(DataAccessException.class, () -> eDao.insertEvent(bestEvent));
    }

    @Test
    public void findByIDPass() throws DataAccessException {
        // Insert the event
        eDao.insertEvent(bestEvent);

        // Attempt to find the inserted event
        Event foundEvent = eDao.findByID(bestEvent.getEventID());

        // Assert the found event is not null
        assertNotNull(foundEvent);

        // Assert that the event we put in is the same as the event we found
        assertEquals(bestEvent, foundEvent);
    }

    @Test
    public void findByIDFail() throws DataAccessException{
        // Attempt to find an event that does not exist
        Event foundEvent = eDao.findByID("IDForFakeEvent");

        // Assert the found event to be null
        assertNull(foundEvent);
    }

    @Test
    public void clearTest() throws DataAccessException {
        // Create two events
        Event event1 = bestEvent;
        Event event2 = new Event("Hiking_123A", "Dave", "Dave123A",
                42.5f, 155.3f, "United States", "Provo",
                "Hiking_Around", 2000);

        // Add the two events to the database
        eDao.insertEvent(event1);
        eDao.insertEvent(event2);

        // Attempt to clear the database
        eDao.clear();

        // Attempt to find the events in the database
        Event foundEvent1 = eDao.findByID(event1.getEventID());
        Event foundEvent2 = eDao.findByID(event2.getEventID());

        // Assert the found events are null
        assertNull(foundEvent1);
        assertNull(foundEvent2);
    }
}
