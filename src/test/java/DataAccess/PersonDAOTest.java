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
    public void setUp() {
        try {
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
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        // Close the connection and don't commit any changes
        db.closeConnection(false);
    }

    @Test
    public void insertPass() {
        try {
            // Attempt to insert a new person
            pDao.insertPerson(newPerson);

            // Find the person that we tried to insert
            Person compareTest = pDao.findByID(newPerson.getPersonID());

            // Assert that what we found is not null
            assertNotNull(compareTest);

            // Assert that what we found is the same as what we tried to insert
            assertEquals(newPerson, compareTest);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertFail() {
        try {
            // Attempt to insert a person
            pDao.insertPerson(newPerson);

            // Try to insert the person again which should throw a DataAccessException
            assertThrows(DataAccessException.class, () -> pDao.insertPerson(newPerson));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByIDPass() {
        try {
            // Insert the person
            pDao.insertPerson(newPerson);

            // Attempt to find the inserted person
            Person foundPerson = pDao.findByID(newPerson.getPersonID());

            // Assert that what was found is not null
            assertNotNull(foundPerson);

            // Assert that what we found is the same as what we inserted
            assertEquals(newPerson, foundPerson);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByIDFail() {
        try {
            // Attempt to find a person that does not exist
            assertNull(pDao.findByID("IDForFakePerson"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllPass() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dad123B", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            Person[] people = pDao.findAllForUser("Dave1234");

            assertEquals(2, people.length);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllFail() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dad123B", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            Person[] people = pDao.findAllForUser("InvalidUsername");

            assertEquals(0, people.length);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findChildPass() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dave123A", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            String childID = pDao.findChild(newPerson1.getPersonID());

            assertEquals("Bob123A", childID);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findChildFail() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dave123A", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            String childID = pDao.findChild("InvalidUsername");

            assertNull(childID);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearByUsernamePass() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dave123A", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            pDao.clearByUsername("Dave1234");

            assertNull(pDao.findByID("Dave123A"));
            assertNull(pDao.findByID("Bob123A"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearByUsernameFail() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dave123A", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            pDao.clearByUsername("InvalidUsername");

            assertNotNull(pDao.findByID("Dave123A"));
            assertNotNull(pDao.findByID("Bob123A"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countPass() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dad123B", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            int numPeople = pDao.getCountByUsername("Dave1234");

            assertEquals(2, numPeople);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countFail() {
        try {
            Person newPerson1 = newPerson;
            Person newPerson2 = new Person("Bob123A", "Dave1234", "Bob",
                    "Dave", "m", "Dad123B", "Mom123B", "Spouse123B");

            pDao.insertPerson(newPerson1);
            pDao.insertPerson(newPerson2);

            int numPeople = pDao.getCountByUsername("InvalidUsername");

            assertEquals(0, numPeople);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearTest() {
        try {
            // Create two new people
            Person person1 = newPerson;
            Person person2 = new Person("Daniel123A", "1234Daniel", "Daniel",
                    "Darrel", "m", "Dad123B", "Mom123B", "Spouse123B");

            // Insert them into the database
            pDao.insertPerson(person1);
            pDao.insertPerson(person2);

            // Clear the database
            pDao.clear();

            // These people should now not exist so a DataAccessException should be thrown
            assertNull(pDao.findByID(person1.getPersonID()));
            assertNull(pDao.findByID(person2.getPersonID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
