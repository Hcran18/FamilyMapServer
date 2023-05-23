package DataAccess;

import model.Person;

import java.sql.*;
import java.util.*;

/**
 * Provides data access methods for interacting with the Person table in the database.
 */
public class PersonDao {
    private final Connection conn;

    /**
     * Constructs a PersonDao object with the specified database connection.
     *
     * @param conn the database connection
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new person in the database.
     *
     * @param person the person to create
     */
    public void createPerson(Person person) {

    }

    /**
     * Finds a person by their ID.
     *
     * @param personID the ID of the person to find
     * @return the found Person object, or null if not found
     */
    public Person findByID(String personID) {
        return null;
    }

    /**
     * Finds all persons associated with a specific user.
     *
     * @param associatedUsername the associated username
     * @return a list of Person objects associated with the user
     */
    public List<Person> findAllForUser(String associatedUsername) {
        return Collections.emptyList();
    }

    /**
     * Finds the parents of a person.
     *
     * @param personID the ID of the person to find parents for
     * @return a list of Person objects representing the parents
     */
    public List<Person> findParents(String personID) {
        return Collections.emptyList();
    }

    /**
     * Finds the spouse of a person.
     *
     * @param personID the ID of the person to find spouse for
     * @return the found Person object representing the spouse, or null if not found
     */
    public Person findSpouse(String personID) {
        return null;
    }

    /**
     * Finds the children of a person.
     *
     * @param personID the ID of the person to find children for
     * @return a list of Person objects representing the children
     */
    public List<Person> findChildren(String personID) {
        return Collections.emptyList();
    }

    /**
     * Updates an existing person in the database.
     *
     * @param person the person to update
     */
    public void update(Person person) {

    }

    /**
     * Deletes a person from the database.
     *
     * @param person the person to delete
     */
    public void delete(Person person) {

    }

    /**
     * Deletes a person by their ID.
     *
     * @param personID the ID of the person to delete
     */
    public void deleteByID(String personID) {

    }

    /**
     * Clears the Person table in the database.
     */
    public void clear() {

    }
}
