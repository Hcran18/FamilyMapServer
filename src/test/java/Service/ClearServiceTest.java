package Service;

import DataAccess.*;
import Result.ClearResult;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService service;
    private static Database db = new Database();

    @BeforeEach
    public void SetUp() {
        service = new ClearService();

        try {
            db.openConnection();
            Connection conn = db.getConnection();

            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDAO eDao = new EventDAO(conn);
            AuthtokenDao aDao = new AuthtokenDao(conn);

            User newUser = new User("username", "password", "Dave@email",
                    "Dave", "David", "m", "Dave123A");
            Person newPerson = new Person("Dave123A", "username",
                    "Dave", "David", "m", "fatherID", "motherID",
                    "spouseID");
            Event newEvent = new Event("eventID", "username", "Dave123A",
                    1, 2, "USA", "provo", "Birth", 2023);
            Authtoken newAuthtoken = new Authtoken("newAuthtoken", "username");

            uDao.insertUser(newUser);
            pDao.insertPerson(newPerson);
            eDao.insertEvent(newEvent);
            aDao.insertAuthtoken(newAuthtoken);

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            db.openConnection();
            Connection conn = db.getConnection();

            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDAO eDao = new EventDAO(conn);
            AuthtokenDao aDao = new AuthtokenDao(conn);

            uDao.clear();
            pDao.clear();
            eDao.clear();
            aDao.clear();

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void clearPass() {
        ClearResult result = service.clear();

        assertTrue(result.isSuccess());
        assertNotNull(result.getMessage());
    }

    @Test
    public void clearEmptyDatabasePass() {
        ClearResult result = service.clear();

        assertTrue(result.isSuccess());
        assertNotNull(result.getMessage());

        // Clear a now empty Database
        result = service.clear();

        assertTrue(result.isSuccess());
        assertNotNull(result.getMessage());
    }
}
