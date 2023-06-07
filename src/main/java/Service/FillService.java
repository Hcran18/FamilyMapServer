package Service;

import DataAccess.*;
import Generation.FamilyTreeGenerator;
import Result.FillResult;
import model.User;

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

            pDao.clearByUsername(username);
            eDao.clearByUsername(username);

            User user = uDao.findByUsername(username);

            String personID = user.getPersonID();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String gender = user.getGender();

            FamilyTreeGenerator ft = new FamilyTreeGenerator(generations, personID, username, firstName, lastName, gender, conn);

            ft.generateFamilyTree();

            int numPersons = 1;
            int numEvents = 1;
            if (generations > 0) {
                numPersons = pDao.getCountByUsername(username);
                numEvents = numPersons * 3 - 2;
            }

            db.closeConnection(true);

            FillResult result = new FillResult();

            result.setMessage("Successfully added " + numPersons + " persons and " + numEvents + " events to the database.");
            result.setSuccess(true);

            return result;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);
            FillResult result = new FillResult();

            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }

}
