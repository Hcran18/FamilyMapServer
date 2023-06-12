package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Result.Event_EventIDResult;
import model.Event;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventIDServiceTest {
    private Event_EventIDService service;

    private static Database db = new Database();

    @BeforeAll
    public static void beforeAllSetUp() {
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            EventDAO eDao = new EventDAO(conn);

            Event newEvent = new Event("eventID", "username", "Dave123A",
                    1, 2, "USA", "Provo", "Birth", 2023);

            eDao.insertEvent(newEvent);

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @AfterAll
    public static void tearDown() {
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            EventDAO eDao = new EventDAO(conn);

            eDao.clear();

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @BeforeEach
    public void setUp() {
        service = new Event_EventIDService();
    }

    @Test
    public void eventIDPass() {
        Event_EventIDResult result = service.event_eventID("username", "eventID");

        assertTrue(result.isSuccess());
        assertEquals("username", result.getAssociatedUsername());
        assertEquals("eventID", result.getEventID());
        assertEquals("Dave123A", result.getPersonID());
        assertEquals(1, result.getLatitude());
        assertEquals(2, result.getLongitude());
        assertEquals("USA", result.getCountry());
        assertEquals("Provo", result.getCity());
        assertEquals("Birth", result.getEventType());
        assertEquals(2023, result.getYear());
    }

    @Test
    public void eventIDFail() {
        // Look for event with the wrong associated Username
        Event_EventIDResult result = service.event_eventID("IncorrectUsername", "eventID");

        assertFalse(result.isSuccess());
        assertNull(result.getAssociatedUsername());
        assertNull(result.getEventID());
        assertNull(result.getPersonID());
        assertNull(result.getCountry());
        assertNull(result.getCity());
        assertNull(result.getEventType());
        assertNotNull(result.getMessage());
    }
}
