package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Result.PersonResult;
import model.Person;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private PersonService service;
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
            Person newPerson2 = new Person("Bob123A", "username",
                    "Bob", "David", "m", "otherFatherID", "otherMotherID",
                    "otherSpouseID");

            pDao.insertPerson(newPerson);
            pDao.insertPerson(newPerson2);

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
        service = new PersonService();
    }

    @Test
    public void personPass() {
        PersonResult result = service.persons("username");

        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(2, result.getData().length);
        assertNull(result.getMessage());
    }

    @Test
    public void personFail() {
        PersonResult result = service.persons("InvalidUsername");

        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(0, result.getData().length);
        assertNull(result.getMessage());
    }
}
