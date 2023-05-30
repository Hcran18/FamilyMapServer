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
        // Create database
        db = new Database();

        // Create a new user
        newUser = new User("Dave1234", "password", "Dave@email",
                "Dave", "David", "m", "Dave123A");

        // Open the connection to the database
        Connection conn = db.getConnection();

        // Give the connection to the UserDao
        uDao = new UserDao(conn);

        // Clear the database
        uDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        // Close the connection and don't commit any changes
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Attempt to insert a new user
        uDao.insertUser(newUser);

        // Find the person that we tried to insert
        User compareTest = uDao.findByUsername(newUser.getUsername());

        // Assert that what we found is not null
        assertNotNull(compareTest);

        // Assert that what we tried to insert is what we found
        assertEquals(newUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Attempt to insert a new user
        uDao.insertUser(newUser);

        // Attempt to insert the user again which should throw a DataAccessException
        assertThrows(DataAccessException.class, () -> uDao.insertUser(newUser));
    }

    @Test
    public void findByUsernamePass() throws DataAccessException {
        // Insert a new user
        uDao.insertUser(newUser);

        // Attempt to find what we inserted
        User foundUser = uDao.findByUsername(newUser.getUsername());

        // Assert that what we found is not null
        assertNotNull(foundUser);

        // Assert that what we tried to find is what we inserted
        assertEquals(newUser, foundUser);
    }

    @Test
    public void findByUsernameFail() throws DataAccessException {
        // Attempt to find a user that does not exist
        assertNull(uDao.findByUsername("IDForFakeUser"));
    }

    @Test
    public void clearTest() throws DataAccessException {
        // Create two users
        User user1 = newUser;
        User user2 = new User("Bob1234", "1234Bob", "Bob@email",
                "Bob", "Anders", "m", "Bob123A");

        // Insert the users into the database
        uDao.insertUser(user1);
        uDao.insertUser(user2);

        // Clear the database
        uDao.clear();

        // These users should now not exist so a DataAccessException should be thrown
        assertNull(uDao.findByUsername(user1.getUsername()));
        assertNull(uDao.findByUsername(user2.getUsername()));
    }
}
