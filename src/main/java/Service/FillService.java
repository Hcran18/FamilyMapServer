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
    /**
     * The Database object used for accessing the database.
     */
    private Database db;

    /**
     * The UserDao object used for accessing the user data in the database.
     */
    private UserDao uDao;

    /**
     * The PersonDao object used for accessing the person data in the database.
     */
    private PersonDao pDao;

    /**
     * The EventDao object used for accessing the event data in the database.
     */
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

            if (user != null) {
                String personID = user.getPersonID();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String gender = user.getGender();

                FamilyTreeGenerator ft = new FamilyTreeGenerator(generations, personID, username,
                        firstName, lastName, gender, conn);

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
            else {
                db.closeConnection(false);

                FillResult result = new FillResult();

                result.setMessage("Error: User does not exist");
                result.setSuccess(false);

                return result;
            }
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
