package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.LoginRequest;
import Result.LoginResult;
import model.User;

import java.sql.Connection;

/**
 * Service class that handles the login operation.
 */
public class LoginService {
    private Database db;
    private User foundUser;
    private UserDao uDao;
    /**
     * Authenticates the user login based on the provided login request.
     *
     * @param r The login request containing the user credentials.
     * @return The result of the login operation.
     */
    public LoginResult login(LoginRequest r) {
        db = new Database();

        try {
            db.openConnection();

            Connection conn = db.getConnection();

            uDao = new UserDao(conn);
            String username = r.getUsername();

            foundUser = uDao.findByUsername(username);
            db.closeConnection(true);

            LoginResult result = new LoginResult();

            if (foundUser != null) {
                if (r.getPassword().equals(foundUser.getPassword())) {
                    result.setAuthtoken("Find Authtoken here");
                    result.setUsername(foundUser.getUsername());
                    result.setPersonID(foundUser.getPersonID());
                    result.setSuccess(true);
                }
                else {
                    result.setMessage("Error: Password is incorrect");
                    result.setSuccess(false);
                }

                return result;
            }
            else {
                result.setMessage("Error: User does not exist");
                result.setSuccess(false);

                return result;
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);
            LoginResult result = new LoginResult();

            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
