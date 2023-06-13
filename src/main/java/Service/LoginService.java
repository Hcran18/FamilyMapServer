package Service;

import DataAccess.AuthtokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.LoginRequest;
import Result.LoginResult;
import model.Authtoken;
import model.User;

import java.sql.Connection;

/**
 * Service class that handles the login operation.
 */
public class LoginService {
    /**
     * The Database object used for accessing the database.
     */
    private Database db;

    /**
     * The User object representing the found user during the login process.
     */
    private User foundUser;

    /**
     * The UserDao object used for accessing the user data in the database.
     */
    private UserDao uDao;

    /**
     * The AuthtokenDao object used for accessing the authtoken data in the database.
     */
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

            aDao = new AuthtokenDao(conn);

            Authtoken userAuthtoken = aDao.findByUsername(username);

            if (userAuthtoken != null) {
                String authtoken = userAuthtoken.getAuthtoken();

                LoginResult result = new LoginResult();

                if (foundUser != null) {
                    if (r.getPassword().equals(foundUser.getPassword())) {
                        db.closeConnection(true);

                        result.setAuthtoken(authtoken);
                        result.setUsername(foundUser.getUsername());
                        result.setPersonID(foundUser.getPersonID());
                        result.setSuccess(true);
                    }
                    else {
                        db.closeConnection(false);
                        result.setMessage("Error: Password is incorrect");
                        result.setSuccess(false);
                    }

                    return result;
                }
                else {
                    result.setMessage("Error: User does not exist");
                    result.setSuccess(false);

                    db.closeConnection(false);
                    return result;
                }
            }
            else {
                LoginResult result = new LoginResult();
                result.setMessage("Error: Invalid login attempt");
                result.setSuccess(false);

                db.closeConnection(false);
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
