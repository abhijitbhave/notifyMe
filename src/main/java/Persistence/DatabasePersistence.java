package Persistence;

import Notification.Notification;
import Users.UserPreferences;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import database.DatabaseHelper;
import java.util.ArrayList;

//The class that handles Notification via SMS.
//We are leveraging the Twilio SDK and some code has been referenced from the Twilio API Documentation.

public class DatabasePersistence extends Persistence {

    DatabaseHelper databaseHelper = null;

    //Calling the super constructor.
    public DatabasePersistence(PersistenceType persistenceType) {
        //Calling the super constructor.
        super(persistenceType);
        this.databaseHelper = new DatabaseHelper();
        databaseHelper.createWeatherDatabase();
    }

    @Override
    public ArrayList<UserPreferences> getUserPreferences() {
        if(databaseHelper.weatherTableExists())
            return(databaseHelper.readDataFromDatabase());
        else
            return null;
    }

    @Override
    public Boolean setUserPreferences(UserPreferences userPreferences) {
        if(databaseHelper.weatherTableExists())
            return(databaseHelper.insertIntoDatabase(userPreferences));
        return false;
    }

    @Override
    public Boolean deleteUserPreferences(Integer userId) {
        if(databaseHelper.weatherTableExists())
            return(databaseHelper.deleteDataFromDatabase(userId));
        return false;
    }
}
