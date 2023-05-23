package DataAccess;

import model.Person;

import java.sql.*;
import java.util.*;

public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    public void createPerson(Person person) {

    }

    public Person findByID(String personID) {
        return null;
    }

    public List<Person> findAllForUser(String associatedUsername) {
        return Collections.emptyList();
    }

    public List<Person> findParents(String personID) {
        return Collections.emptyList();
    }

    public Person findSpouse(String personID) {
        return null;
    }

    public List<Person> findChildren(String personID) {
        return Collections.emptyList();
    }

    public void update(Person person) {

    }

    public void delete(Person person) {

    }

    public void deleteByID(String personID) {

    }
}
