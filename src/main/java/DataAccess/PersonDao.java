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
     * @throws DataAccessException if an error occurs while finding the Person by ID
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
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a Person in the database by personID");
        }
    }

    // TODO JavaDoc
    public int getCountByUsername(String associatedUsername) throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM person WHERE associatedUsername = ?";
        int count = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while getting number of people with associatedUsername");
        }

        return count;
    }

    /**
     * Finds all persons associated with a specific user.
     *
     * @param associatedUsername the associated username
     * @return an array of Person objects associated with the user
     */
    public Person[] findAllForUser(String associatedUsername) throws DataAccessException {
        List<Person> persons = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Person person = new Person(
                        rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID")
                );
                persons.add(person);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding persons in the database for a user");
        }

        return persons.toArray(new Person[0]);
    }

    /**
     * Finds the child of a person.
     *
     * @param personID the ID of the person to find children for
     * @return a personID representing the child
     */
    public String findChild(String personID) throws DataAccessException {
        String childID = null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM person WHERE FatherID = ? OR MotherID = ?")) {
            stmt.setString(1, personID);
            stmt.setString(2, personID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    childID = rs.getString("personID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding the Child of the given personID");
        }

        return childID;
    }

    /**
     * Clears all person for their associatedUserName.
     *
     * @param username the associatedUsername of the people to delete
     */
    public void clearByUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM person WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing people by associatedUsername");
        }
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
