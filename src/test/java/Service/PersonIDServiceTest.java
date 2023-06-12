package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Result.Person_PersonIDResult;
import model.Person;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonIDServiceTest {
    private Person_PersonIDService service;

    private static Database db = new Database();

    @BeforeAll
    public static void beforeAllSetUp() {
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            PersonDao pDao = new PersonDao(conn);

            Person newPerson = new Person("Dave123A", "username",
                    "Dave", "David", "m", "fatherID", "motherID",
                    "spouseID");

            pDao.insertPerson(newPerson);

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
            PersonDao pDao = new PersonDao(conn);

            pDao.clear();

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @BeforeEach
    public void setUp() {
        service = new Person_PersonIDService();
    }

    @Test
    public void PersonIDPass() {
        Person_PersonIDResult result = service.person_personID("username", "Dave123A");

        assertTrue(result.isSuccess());
        assertEquals("Dave123A", result.getPersonID());
        assertEquals("username", result.getAssociatedUsername());
        assertEquals("Dave", result.getFirstName());
        assertEquals("David", result.getLastName());
        assertEquals("m", result.getGender());
        assertEquals("fatherID", result.getFatherID());
        assertEquals("motherID", result.getMotherID());
        assertEquals("spouseID", result.getSpouseID());
    }

    @Test void PersonIDFail() {
        Person_PersonIDResult result = service.person_personID("IncorrectUsername", "Dave123A");

        assertFalse(result.isSuccess());
        assertNull(result.getPersonID());
        assertNull(result.getAssociatedUsername());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
        assertNull(result.getGender());
        assertNull(result.getFatherID());
        assertNull(result.getMotherID());
        assertNull(result.getSpouseID());
        assertNotNull(result.getMessage());
    }
}
