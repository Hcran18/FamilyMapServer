package DataAccess;

import model.Authtoken;

import java.sql.*;

public class AuthtokenDao {
    private final Connection conn;

    public AuthtokenDao(Connection conn) {
        this.conn = conn;
    }

    public void createAuthtoken(Authtoken authtoken) {

    }

    public Authtoken findByAuthtoken(String authtoken) {
        return null;
    }

    public Authtoken findByUsername(String username) {
        return null;
    }

    public void updateAuthtoken(Authtoken authtoken) {

    }

    public void deleteAuthtoken(Authtoken authtoken) {

    }

    public void deleteByAuthtoken(String authtoken) {

    }
}
