package Persistence;

import Users.UserPreferences;
import database.DatabaseHelper;
import java.util.ArrayList;

//The class extended the Persistence class and is the concrete implementation of a database persistence path.

public class DatabasePersistence extends Persistence {

    //Creating an object for databaseHelper which will actually perform all the database operations.
    DatabaseHelper databaseHelper = null;

    //Calling the super constructor.
    public DatabasePersistence(PersistenceType persistenceType) {
        //Calling the super constructor.
        super(persistenceType);
        this.databaseHelper = new DatabaseHelper();
        databaseHelper.createWeatherDatabase();
    }

    @Override
    //Overriding the getUserPreferences since the database implementation will read from the database.
    //The method simply forwards the task to readDataFromDatabase as long as the table exists in the DatabaseHelper class.
    public ArrayList<UserPreferences> getUserPreferences() {
        if(databaseHelper.weatherTableExists())
            return(databaseHelper.readDataFromDatabase());
        else
            return null;
    }

    @Override
    //Overriding the setUserPreferences since the database implementation will write to the database.
    //The method simply forwards the task to insertIntoDatabase as long as the table exists in the DatabaseHelper class.
    public Boolean setUserPreferences(UserPreferences userPreferences) {
        if(databaseHelper.weatherTableExists())
            return(databaseHelper.insertIntoDatabase(userPreferences));
        return false;
    }

    @Override
    //Overriding the deleteUserPreferences since the database implementation will delete from the database.
    //The method simply forwards the task to deleteDataFromDatabase as long as the table exists in the DatabaseHelper class.

    public Boolean deleteUserPreferences(Integer userId) {
        if(databaseHelper.weatherTableExists())
            return(databaseHelper.deleteDataFromDatabase(userId));
        return false;
    }
}
