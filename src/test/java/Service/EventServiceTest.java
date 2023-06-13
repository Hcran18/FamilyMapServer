package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Result.EventResult;
import model.Event;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    private EventService service;

    private static Database db = new Database();

    @BeforeAll
    public static void beforeAllSetUp() {
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            EventDAO eDao = new EventDAO(conn);

            eDao.clear();

            Event newBirth = new Event("birthID", "username", "Dave123A",
                    1, 2, "USA", "Provo", "Birth", 2022);
            Event newDeath = new Event("deathID", "username", "Dave123A",
                    1, 2, "USA", "Provo", "Death", 2023);

            eDao.insertEvent(newBirth);
            eDao.insertEvent(newDeath);

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
        service = new EventService();
    }

    @Test
    public void eventPass() {
        EventResult result = service.events("username");

        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(2, result.getData().length);
        assertNull(result.getMessage());
    }

    @Test
    public void eventFail() {
        // Call for user that does not exist
        EventResult result = service.events("InvalidUsername");

        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(0, result.getData().length);
        assertNull(result.getMessage());
    }
}
