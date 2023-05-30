package DataAccess;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person newPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Create database
        db = new Database();

        // Create a new person
        newPerson = new Person("Dave123A", "Dave1234", "Dave",
                "David", "m", "Dad123A", "Mom123A", "Spouse123A");

        // Open the connection to the database
        Connection conn = db.getConnection();

        // Give the connection to the personDao
        pDao = new PersonDao(conn);

        // Clear the database
        pDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        // Close the connection and don't commit any changes
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Attempt to insert a new person
        pDao.insertPerson(newPerson);

        // Find the person that we tried to insert
        Person compareTest = pDao.findByID(newPerson.getPersonID());

        // Assert that what we found is not null
        assertNotNull(compareTest);

        // Assert that what we found is the same as what we tried to insert
        assertEquals(newPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Attempt to insert a person
        pDao.insertPerson(newPerson);

        // Try to insert the person again which should throw a DataAccessException
        assertThrows(DataAccessException.class, () -> pDao.insertPerson(newPerson));
    }

    @Test
    public void findByIDPass() throws DataAccessException {
        // Insert the person
        pDao.insertPerson(newPerson);

        // Attempt to find the inserted person
        Person foundPerson = pDao.findByID(newPerson.getPersonID());

        // Assert that what was found is not null
        assertNotNull(foundPerson);

        // Assert that what we found is the same as what we inserted
        assertEquals(newPerson, foundPerson);
    }

    @Test
    public void findByIDFail() throws DataAccessException {
        // Attempt to find a person that does not exist
        Person foundPerson = pDao.findByID("IDForFakeUser");

        // Assert the found person to be null/does not exist
        assertNull(foundPerson);
    }

    @Test
    public void clearTest() throws DataAccessException {
        // Create two new people
        Person person1 = newPerson;
        Person person2 = new Person("Daniel123A", "1234Daniel", "Daniel",
                "Darrel", "m", "Dad123B", "Mom123B", "Spouse123B");

        // Insert them into the database
        pDao.insertPerson(person1);
        pDao.insertPerson(person2);

        // Clear the database
        pDao.clear();

        // Attempt to find the events in the database
        Person foundPerson1 = pDao.findByID(person1.getPersonID());
        Person foundPerson2 = pDao.findByID(person2.getPersonID());

        // Assert that the found persons are null
        assertNull(foundPerson1);
        assertNull(foundPerson2);
    }
}
