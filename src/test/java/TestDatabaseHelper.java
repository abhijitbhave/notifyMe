import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import Users.UserPreferences;
import database.DatabaseHelper;
import java.util.ArrayList;
import org.junit.Test;

public class TestDatabaseHelper {

    DatabaseHelper dh = new DatabaseHelper();
    UserPreferences up = new UserPreferences();
    ArrayList<UserPreferences> userPreferencesList = new ArrayList<>();
    ArrayList<String> weather = new ArrayList<String>();


    //Testing the insertion of the userPreferences Object into the database.
    @Test
    public void testWriteUserPreferences() {
        Integer size = dh.readDataFromDatabase().size();
        up.setUserId("1");
        up.setUserFirstName("Tom");
        up.setUserLastName("Brady");
        up.setUserContactPreference("Sms");
        weather.add("Rain");
        weather.add("Cold");
        weather.add("Heat");
        up.setSelectedWeatherConditions(weather);
        up.setUserContactId("1234567890");
        up.setZipCode(94568);
        dh.insertIntoDatabase(up);
        assertTrue(dh.readDataFromDatabase().size() == size+1);

    }

    //Testing the read of the UserPreferences object from the database.

    @Test
    public void testReadUserPreferences() {
        userPreferencesList = dh.readDataFromDatabase();
        //The database can return more than one record. Hence interating through all of them to find the test record.
        userPreferencesList.forEach(userPreferences -> {
            if(userPreferences.getUserFirstName().matches("Tom"))
            {
                assertEquals(userPreferences.getUserFirstName(), "Tom");
                assertEquals(userPreferences.getUserLastName(), "Brady");
                assertEquals(userPreferences.getUserContactPreference(), "Sms");
                assertTrue(userPreferences.getSelectedWeatherConditions() instanceof ArrayList);
                assertTrue(userPreferences.getSelectedWeatherConditions().containsAll(weather));
                assertEquals(userPreferences.getUserContactId(), "1234567890");
                assertEquals(userPreferences.getZipCode().toString(), "94568");
            }

        });



    }

}
