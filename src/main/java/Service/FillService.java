package Service;

import DataAccess.*;
import Generation.FamilyTreeGenerator;
import Result.FillResult;

import java.sql.Connection;


/**
 * Service class that handles the fill operation.
 */
public class FillService {
    private Database db;
    private UserDao uDao;
    private PersonDao pDao;
    private EventDAO eDao;
    /**
     * Fills the database with generated data.
     *
     * @return The result of the fill operation.
     */
    public FillResult fill(String username, int generations) {
        db = new Database();

        try {
            db.openConnection();

            Connection conn = db.getConnection();

            uDao = new UserDao(conn);
            pDao = new PersonDao(conn);
            eDao = new EventDAO(conn);

            // TODO clear persons and events for specific username

            // TODO get personID, firstName, lastName, gender of user
            String personID = null;
            String firstName = null;
            String lastName = null;
            String gender = null;

            FamilyTreeGenerator ft = new FamilyTreeGenerator(generations, personID, username, firstName, lastName, gender, conn);

            ft.generateFamilyTree();

            int numPersons = 1;
            int numEvents = 1;
            if (generations > 0) {
                // TODO get number of people associated with specific username
                // TODO get number of events associated with specific username
            }

            db.closeConnection(true);

            FillResult result = new FillResult();
            result.setMessage("Successfully added " + numPersons + " persons and " + numEvents + " events to the database.");
            result.setSuccess(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);
            FillResult result = new FillResult();

            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);
        }
        return null;
    }
}
