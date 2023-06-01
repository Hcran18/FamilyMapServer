package Service;

import DataAccess.AuthtokenDao;
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
    private AuthtokenDao aDao;
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

            LoginResult result = new LoginResult();

            if (foundUser != null) {
                if (r.getPassword().equals(foundUser.getPassword())) {
                    aDao = new AuthtokenDao(conn);

                    result.setAuthtoken(aDao.findByUsername(username).getAuthtoken());
                    result.setUsername(foundUser.getUsername());
                    result.setPersonID(foundUser.getPersonID());
                    result.setSuccess(true);
                }
                else {
                    result.setMessage("Error: Password is incorrect");
                    result.setSuccess(false);
                }

                db.closeConnection(true);

                return result;
            }
            else {
                result.setMessage("Error: User does not exist");
                result.setSuccess(false);

                db.closeConnection(true);
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
