package Persistence;

import Users.UserPreferences;
import Utils.FileHelper;
import java.util.ArrayList;

//This class extends and implements the abstract persistence class.

public class FilePersistence extends Persistence {

    //Creating an object of fileHelper to help with all the File operations.
    FileHelper fileHelper = null;

    //Calling the super constructor.
    public FilePersistence(PersistenceType persistenceType) {
        //Calling the super constructor.
        super(persistenceType);
        this.fileHelper = new FileHelper();
    }

    @Override
    //Overriding the getUserPreferences since the file implementation will read from the file.
    //The method simply forwards the task to readUserPreferences in the FileHelper class.
    public ArrayList<UserPreferences> getUserPreferences() {
        if (fileHelper.checkIfFileExistsI()) {
            return fileHelper.readUserPreferences();
        }
        else {
            return null;
        }
    }

    @Override
    //Overriding the setUserPreferences since the file implementation will write to the file.
    //The method simply forwards the task to writeUserPreferences in the FileHelper class.
    public Boolean setUserPreferences(UserPreferences userPreferences) {
        if (fileHelper.checkIfFileExistsI()) {
            return (fileHelper.writeUserPreferences(userPreferences));
        }
        return false;
    }


    @Override
    //Overriding the deleteUserPreferences since we will not support this operation in the file mode.
    //The method basically prints out a message saying the operation is not supported.
    public Boolean deleteUserPreferences(String userId) {
        if (fileHelper.checkIfFileExistsI()) {
            System.out.println("Cannot delete data in File");
            return false;
        }
        return false;
    }
}
