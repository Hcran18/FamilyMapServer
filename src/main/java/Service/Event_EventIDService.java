package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Result.Event_EventIDResult;
import model.Event;

import java.sql.Connection;
import java.util.Objects;

/**
 * Service class that handles the retrieval of an event by its ID.
 */
public class Event_EventIDService {
    private Database db;
    private EventDAO eDao;
    private Event event;
    /**
     * Retrieves the event details for a specific event ID.
     *
     * @return The result of the event retrieval operation.
     */
    public Event_EventIDResult event_eventID(String username, String eventID) {
        db = new Database();

        try {
            db.openConnection();
            Connection conn = db.getConnection();

            eDao = new EventDAO(conn);

            event = eDao.findByID(eventID);

            String eventUser = event.getAssociatedUsername();

            if (Objects.equals(eventUser, username)) {
                Event_EventIDResult result = new Event_EventIDResult();

                result.setAssociatedUsername(username);
                result.setEventID(eventID);
                result.setPersonID(event.getPersonID());
                result.setLatitude(event.getLatitude());
                result.setLongitude(event.getLongitude());
                result.setCountry(event.getCountry());
                result.setCity(event.getCity());
                result.setEventType(event.getEventType());
                result.setYear(event.getYear());
                result.setSuccess(true);

                db.closeConnection(true);

                return result;
            }
            else {
                db.closeConnection(false);

                Event_EventIDResult result = new Event_EventIDResult();

                result.setMessage("Error: Requested event does not belong to this user");
                result.setSuccess(false);

                return result;
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);

            Event_EventIDResult result = new Event_EventIDResult();

            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
