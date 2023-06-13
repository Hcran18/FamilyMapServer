package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Result.EventResult;
import model.Event;

import java.sql.Connection;

/**
 * Service class that handles the retrieval of events.
 */
public class EventService {
    /**
     * The Database object.
     */
    private Database db;

    /**
     * The Dao object used for accessing the event data.
     */
    private EventDAO eDao;

    /**
     * An array of events.
     */
    private Event[] events;

    /**
     * Retrieves the events.
     *
     * @return The result of the event retrieval operation.
     */
    public EventResult events(String username) {
        db = new Database();

        try {
            db.openConnection();
            Connection conn = db.getConnection();

            eDao = new EventDAO(conn);

            events = eDao.findAllForUser(username);

            db.closeConnection(true);

            EventResult result = new EventResult();
            result.setData(events);
            result.setSuccess(true);

            return result;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);

            EventResult result = new EventResult();
            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
