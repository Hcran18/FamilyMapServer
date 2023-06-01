package Service;

import DataAccess.AuthtokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import model.Authtoken;
import model.User;
import java.util.UUID;

import java.sql.Connection;

/**
 * Service class that handles the registration operation.
 */
public class RegisterService {
    private Database db;
    private User newUser;
    private Authtoken newAuthtoken;
    private UserDao uDao;
    private AuthtokenDao aDao;
    /**
     * Registers a new user based on the provided registration request.
     *
     * @param r The registration request containing the user details.
     * @return The result of the registration operation.
     */
    public RegisterResult register(RegisterRequest r) {
        db = new Database();

        try {
            db.openConnection();

            Connection conn = db.getConnection();

            uDao = new UserDao(conn);
            UUID personIDUuid = UUID.randomUUID();
            String personID = personIDUuid.toString();

            newUser = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(),
                    r.getLastName(), r.getGender(), personID);

            aDao = new AuthtokenDao(conn);
            UUID authtokenUuid = UUID.randomUUID();
            String authtoken = authtokenUuid.toString();
            newAuthtoken = new Authtoken(authtoken, r.getUsername());

            uDao.insertUser(newUser);
            aDao.insertAuthtoken(newAuthtoken);
            // TODO create family tree

            db.closeConnection(true);

            RegisterResult result = new RegisterResult();

            result.setAuthtoken(authtoken);
            result.setUsername(r.getUsername());
            result.setPersonID(personID);
            result.setSuccess(true);

            return result;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);
            RegisterResult result = new RegisterResult();

            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
