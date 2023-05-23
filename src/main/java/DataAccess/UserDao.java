package DataAccess;

import model.User;

import java.sql.*;

public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public void createUser(User user) {

    }

    public User findByID(String personID) {
        return null;
    }

    public User findByUsername(String username) {
        return null;
    }

    public User findByEmail(String email) {
        return null;
    }

    public User findByFullName(String firstName, String LastName) {
        return null;
    }

    public void updateUser(User user) {

    }

    public void delete(User user) {

    }

    public void deleteByID(String personID) {

    }

    public void clear() {

    }
}
