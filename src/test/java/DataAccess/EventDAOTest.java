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
    public void setUp() {
        try {
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
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        // Close the connection and don't commit any changes
        db.closeConnection(false);
    }

    @Test
    public void insertPass() {
        try {
            // Attempt to insert the event into the database
            eDao.insertEvent(bestEvent);

            // Find the event that we tried to insert
            Event compareTest = eDao.findByID(bestEvent.getEventID());

            // Assert that what we found in not null
            assertNotNull(compareTest);

            // Assert that the event we found is the same as the one we tried to insert
            assertEquals(bestEvent, compareTest);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertFail() {
        try {
            // Attempt to insert an event
            eDao.insertEvent(bestEvent);

            // Try to insert the event again which should throw a DataAccessException
            assertThrows(DataAccessException.class, () -> eDao.insertEvent(bestEvent));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByIDPass() {
        try {
            // Insert the event
            eDao.insertEvent(bestEvent);

            // Attempt to find the inserted event
            Event foundEvent = eDao.findByID(bestEvent.getEventID());

            // Assert the found event is not null
            assertNotNull(foundEvent);

            // Assert that the event we put in is the same as the event we found
            assertEquals(bestEvent, foundEvent);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByIDFail() {
        try {
            // Attempt to find an event that does not exist
            assertNull(eDao.findByID("IDForFakeEvent"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllPass() {
        try {
            Event event1 = bestEvent;
            Event event2 = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(event1);
            eDao.insertEvent(event2);

            Event[] events = eDao.findAllForUser("Gale");

            assertEquals(2, events.length);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllFail() {
        try {
            Event event1 = bestEvent;
            Event event2 = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(event1);
            eDao.insertEvent(event2);

            Event[] events = eDao.findAllForUser("InvalidUsername");

            assertEquals(0, events.length);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findBirthYearPass() {
        try {
            Event birth = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(birth);

            int birthYear = eDao.findBirthYear("Gale123A");

            assertEquals(2000, birthYear);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findBirthYearFail() {
        try {
            Event birth = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(birth);

            int birthYear = eDao.findBirthYear("InvalidUsername");

            assertEquals(0, birthYear);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findDeathYearPass() {
        try {
            Event death = new Event("Death_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Death", 2023);

            eDao.insertEvent(death);

            int deathYear = eDao.findDeathYear("Gale123A");

            assertEquals(2023, deathYear);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findDeathYearFail() {
        try {
            Event death = new Event("Death_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Death", 2023);

            eDao.insertEvent(death);

            int deathYear = eDao.findDeathYear("InvalidUsername");

            assertEquals(0, deathYear);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBirthPass() {
        try {
            Event birth = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(birth);

            eDao.updateBirthByID("Gale123A", 1990);

            assertEquals(1990, eDao.findByID("Birth_123A").getYear());
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBirthFail()  {
        try {
            Event death = new Event("Death_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Death", 2023);

            eDao.insertEvent(death);

            eDao.updateBirthByID("Gale123A", 1990);

            assertEquals(2023, eDao.findByID("Death_123A").getYear());
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateDeathPass() {
        try {
            Event death = new Event("Death_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Death", 2023);

            eDao.insertEvent(death);

            eDao.updateDeathByID( "Gale123A",2021);

            assertEquals(2021, eDao.findByID("Death_123A").getYear());
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateDeathFail() {
        try {
            Event birth = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(birth);

            eDao.updateDeathByID("Gale123A", 2021);

            assertEquals(2000, eDao.findByID("Birth_123A").getYear());
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearByUsernamePass() {
        try {
            Event event1 = bestEvent;
            Event event2 = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(event1);
            eDao.insertEvent(event2);

            eDao.clearByUsername("Gale");

            assertNull(eDao.findByID("Biking_123A"));
            assertNull(eDao.findByID("Birth_123A"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearByUsernameFail() {
        try {
            Event event1 = bestEvent;
            Event event2 = new Event("Birth_123A", "Gale", "Gale123A",
                    1, 2, "Japan", "Ushiku",
                    "Birth", 2000);

            eDao.insertEvent(event1);
            eDao.insertEvent(event2);

            eDao.clearByUsername("InvalidUsername");

            assertNotNull(eDao.findByID("Biking_123A"));
            assertNotNull(eDao.findByID("Birth_123A"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearTest() {
        try {
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

            // These events should now not exist so a DataAccessException should be thrown
            assertNull(eDao.findByID(event1.getPersonID()));
            assertNull(eDao.findByID(event2.getPersonID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
