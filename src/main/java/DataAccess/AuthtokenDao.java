package DataAccess;

import model.Authtoken;

import java.sql.*;

public class AuthtokenDao {
    private final Connection conn;

    public AuthtokenDao(Connection conn) {
        this.conn = conn;
    }
}
