package DataAccess;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private User newUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();

        newUser = new User("Dave1234", "password", "Dave@email",
                "Dave", "David", "m", "Dave123A");

        Connection conn = db.getConnection();

        uDao = new UserDao(conn);

        uDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insertUser(newUser);

        User compareTest = uDao.findByID(newUser.getPersonID());

        assertNotNull(compareTest);

        assertEquals(newUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insertUser(newUser);

        assertThrows(DataAccessException.class, () -> uDao.insertUser(newUser));
    }

    @Test
    public void findByIDPass() throws DataAccessException {
        uDao.insertUser(newUser);

        User foundUser = uDao.findByID(newUser.getPersonID());

        assertNotNull(foundUser);

        assertEquals(newUser, foundUser);
    }

    @Test
    public void findByIDFail() throws DataAccessException {
        User foundUser = uDao.findByID("IDForFakeUser");

        assertNull(foundUser);
    }

    @Test
    public void clearTest() throws DataAccessException {
        User user1 = newUser;
        User user2 = new User("Bob1234", "1234Bob", "Bob@email",
                "Bob", "Anders", "m", "Bob123A");

        uDao.insertUser(user1);
        uDao.insertUser(user2);

        uDao.clear();

        User foundUser1 = uDao.findByID(user1.getPersonID());
        User foundUser2 = uDao.findByID(user2.getPersonID());

        assertNull(foundUser1);
        assertNull(foundUser2);
    }

}
