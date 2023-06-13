package Service;

import DataAccess.*;
import Result.ClearResult;

import java.sql.Connection;

/**
 * Service class that handles the clear operation.
 */
public class ClearService {
    /**
     * The Database object.
     */
    private Database db;

    /**
     * The Dao object used for accessing the user data.
     */
    private UserDao uDao;

    /**
     * The Dao object used for accessing the person data.
     */
    private PersonDao pDao;

    /**
     * The Dao object used for accessing the authtoken data.
     */
    private AuthtokenDao aDao;

    /**
     * The DAO object used for accessing the event data
     */
    private EventDAO eDAO;

    /**
     * Clears all data in the database.
     *
     * @return The result of the operation.
     */
    public ClearResult clear() {
        db = new Database();

        try {
            db.openConnection();

            Connection conn = db.getConnection();

            uDao = new UserDao(conn);
            pDao = new PersonDao(conn);
            aDao = new AuthtokenDao(conn);
            eDAO = new EventDAO(conn);

            uDao.clear();
            pDao.clear();
            aDao.clear();
            eDAO.clear();

            db.closeConnection(true);

            ClearResult result = new ClearResult();

            result.setMessage("Clear succeeded");
            result.setSuccess(true);

            return result;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);
            ClearResult result = new ClearResult();

            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
