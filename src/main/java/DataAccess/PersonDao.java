package DataAccess;

import model.Person;

import java.sql.*;

public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }
}
