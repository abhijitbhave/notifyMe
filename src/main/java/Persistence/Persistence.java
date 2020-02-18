package Persistence;

import Users.UserPreferences;
import Utils.FileHelper;
import java.util.ArrayList;

public abstract class Persistence<K> {

    private PersistenceType persistenceType = null;

    public Persistence(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }

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
    public abstract Boolean deleteUserPreferences(Integer userId);


}

