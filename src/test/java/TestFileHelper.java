import Users.UserPreferences;
import Utils.FileHelper;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFileHelper {

    FileHelper fh = new FileHelper();
    UserPreferences up = new UserPreferences();
    ArrayList<UserPreferences> userPreferencesList = new ArrayList<>();
    ArrayList<String> weather = new ArrayList<String>();


    //Testing the creation of the userPreferences file.
    @Test
    public void testWriteUserPreferences() {
        up.setUserId("1");
        up.setUserFirstName("Tom");
        up.setUserLastName("Brady");
        up.setUserContactPreference("Sms");
        weather.add("Rain");
        weather.add("Cold");
        weather.add("Heat");
        up.setSelectedWeatherConditions(weather);
        up.setZipCode(94568);
        up.setUserContactId("1234567890");
        fh.writeUserPreferences(up);
        File file = new File("userPreferences.properties");
        assertTrue(file.exists());
        assertTrue(file.canRead());
        assertTrue(file.canWrite());

    }

    //Testing the read from the UserPreferences file.

    @Test
    public void testReadUserPreferences() {
        userPreferencesList = fh.readUserPreferences();
        userPreferencesList.forEach(userPreferences -> {
            assertEquals(userPreferences.getUserFirstName(), "Tom");
            assertEquals(userPreferences.getUserLastName(), "Brady");
            assertEquals(userPreferences.getUserContactPreference(), "Sms");
            assertTrue(userPreferences.getSelectedWeatherConditions() instanceof ArrayList);
            assertTrue(userPreferences.getSelectedWeatherConditions().containsAll(weather));
            assertEquals(userPreferences.getUserContactId(), "1234567890");
        });



    }

}
