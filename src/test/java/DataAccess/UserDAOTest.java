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
        User compareTest = uDao.findByID(newUser.getPersonID());

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
    public void findByIDPass() throws DataAccessException {
        // Insert a new user
        uDao.insertUser(newUser);

        // Attempt to find what we inserted
        User foundUser = uDao.findByID(newUser.getPersonID());

        // Assert that what we found is not null
        assertNotNull(foundUser);

        // Assert that what we tried to find is what we inserted
        assertEquals(newUser, foundUser);
    }

    @Test
    public void findByIDFail() throws DataAccessException {
        // Attempt to find a user that does not exist
        User foundUser = uDao.findByID("IDForFakeUser");

        // Assert that what we found is null
        assertNull(foundUser);
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

        // Attempt to find the users in the database
        User foundUser1 = uDao.findByID(user1.getPersonID());
        User foundUser2 = uDao.findByID(user2.getPersonID());

        // Assert that the found users are null
        assertNull(foundUser1);
        assertNull(foundUser2);
    }

}
