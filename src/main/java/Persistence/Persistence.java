package Persistence;

import Users.UserPreferences;
import Utils.FileHelper;
import java.util.ArrayList;

//This is a new class introduced in order to be able to support multiple types of persistence layers.
//Currently we are support database persistence OR File based persistence. The abstract class is extended and implemented by its child classes.
public abstract class Persistence {

    public Persistence(PersistenceType persistenceType) {    }

    //Method to retrieve UserPreferences.
    private ArrayList<UserPreferences> getUserOptions() {
        FileHelper fh = new FileHelper();
        ArrayList<UserPreferences> userPreferences = fh.readUserPreferences();
        return userPreferences;

    }

    //An abstract getUserPreferences method that will be implemented by concrete Child classes.
    public abstract ArrayList<UserPreferences> getUserPreferences();

    //An abstract setUserPreferences method that will be implemented by concrete Child classes.
    public abstract Boolean setUserPreferences(UserPreferences userPreferences);

    //An abstract setUserPreferences method that will be implemented by concrete Child classes.
    public abstract Boolean deleteUserPreferences(String userId);


}

