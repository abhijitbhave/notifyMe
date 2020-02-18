package Persistence;

import Users.UserPreferences;
import Utils.FileHelper;
import database.DatabaseHelper;
import java.util.ArrayList;

//The class that handles Notification via SMS.
//We are leveraging the Twilio SDK and some code has been referenced from the Twilio API Documentation.

public class FilePersistence extends Persistence {

    FileHelper fileHelper = null;

    //Calling the super constructor.
    public FilePersistence(PersistenceType persistenceType) {
        //Calling the super constructor.
        super(persistenceType);
        this.fileHelper = new FileHelper();
    }

    @Override
    public ArrayList<UserPreferences> getUserPreferences() {
        if (fileHelper.checkIfFileExistsI()) {
            return fileHelper.readUserPreferences();
        }
        // return(fileHelper.readUserPreferences());
        else {
            return null;
        }
    }

    @Override
    public Boolean setUserPreferences(UserPreferences userPreferences) {
        if (fileHelper.checkIfFileExistsI()) {
            return (fileHelper.writeUserPreferences(userPreferences));
        }
        return false;
    }


    @Override
    public Boolean deleteUserPreferences(Integer userId) {
        if (fileHelper.checkIfFileExistsI()) {
            System.out.println("Cannot delete data in File");
            return false;
        }
        return false;
    }
}
