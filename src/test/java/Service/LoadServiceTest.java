package Service;

import DataAccess.*;
import Request.LoadRequest;
import Result.LoadResult;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
public class LoadServiceTest {
    private LoadService service;
    private static Database db = new Database();

    @BeforeAll
    public static void beforeAllSetUp() {
        try {
            db.openConnection();
            Connection conn = db.getConnection();

            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDAO eDao = new EventDAO(conn);

            uDao.clear();
            pDao.clear();
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
        service = new LoadService();
    }

    @Test
    public void loadPass() {
        LoadRequest request = new LoadRequest();

        User newUser = new User("username", "Dave123", "dave@gmail",
                "Dave", "David", "m", "Dave123A");
        Person newPerson = new Person("Dave123A", "username",
                "Dave", "David", "m", "fatherID", "motherID",
                "spouseID");
        Event newBirth = new Event("birthID", "username", "Dave123A",
                1, 2, "USA", "Provo", "Birth", 2022);

        User[] users = {newUser};
        Person[] people = {newPerson};
        Event[] events = {newBirth};

        request.setUsers(users);
        request.setPersons(people);
        request.setEvents(events);

        LoadResult result = service.load(request);

        assertEquals("Successfully added 1 users, 1 persons, and 1 events to the database.",
                result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void loadFail() {
        LoadRequest request = new LoadRequest();

        User newUser = new User("username", "Dave123", "dave@gmail",
                "Dave", "David", "m", "Dave123A");
        Person newPerson = new Person("Dave123A", "username",
                "Dave", "David", "m", "fatherID", "motherID",
                "spouseID");

        User[] users = {newUser};
        Person[] people = {newPerson};

        request.setUsers(users);
        request.setPersons(people);

        LoadResult result = service.load(request);

        assertEquals("Error: all fields must be filled out", result.getMessage());
        assertFalse(result.isSuccess());
    }
}
