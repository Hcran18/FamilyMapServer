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
        db = new Database();

        newPerson = new Person("Dave123A", "Dave1234", "Dave",
                "David", "m", "Dad123A", "Mom123A", "Spouse123A");

        Connection conn = db.getConnection();

        pDao = new PersonDao(conn);

        pDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.insertPerson(newPerson);

        Person compareTest = pDao.findByID(newPerson.getPersonID());

        assertNotNull(compareTest);

        assertEquals(newPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insertPerson(newPerson);

        assertThrows(DataAccessException.class, () -> pDao.insertPerson(newPerson));
    }

    @Test
    public void findByIDPass() throws DataAccessException {
        pDao.insertPerson(newPerson);

        Person foundPerson = pDao.findByID(newPerson.getPersonID());

        assertNotNull(foundPerson);

        assertEquals(newPerson, foundPerson);
    }

    @Test
    public void findByIDFail() throws DataAccessException {
        Person foundPerson = pDao.findByID("IDForFakeUser");

        assertNull(foundPerson);
    }

    @Test
    public void clearTest() throws DataAccessException {
        Person person1 = newPerson;
        Person person2 = new Person("Daniel123A", "1234Daniel", "Daniel",
                "Darrel", "m", "Dad123B", "Mom123B", "Spouse123B");

        pDao.insertPerson(person1);
        pDao.insertPerson(person2);

        pDao.clear();

        Person foundPerson1 = pDao.findByID(person1.getPersonID());
        Person foundPerson2 = pDao.findByID(person2.getPersonID());

        assertNull(foundPerson1);
        assertNull(foundPerson2);
    }
}
