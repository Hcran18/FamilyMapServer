package DataAccess;

import model.Person;
import model.User;

import java.sql.*;
import java.util.*;

/**
 * Provides data access methods for interacting with the Person table in the database.
 */
public class PersonDao {
    /**
     * The database connection used for interacting with the Person table.
     */
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
     * Inserts a person into the database.
     *
     * @param person the person to create
     * @throws DataAccessException if an error occurs while inserting the person
     */
    public void insertPerson(Person person) throws DataAccessException {
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a Person into the database");
        }
    }

    /**
     * Finds a person by their ID.
     *
     * @param personID the ID of the person to find
     *                 @throws DataAccessException if an error occurs while finding the Person by ID
     * @return the found Person object, or null if not found
     */
    public Person findByID(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if(rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
            else {
                throw new DataAccessException("Person does not exist");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a Person in the database by personID");
        }
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
     * @throws DataAccessException if an error occurs while clearing the person table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM person";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }
}
