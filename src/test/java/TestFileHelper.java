import Users.UserPreferences;
import Utils.FileHelper;
import Utils.NotifyMeException;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFileHelper {

    FileHelper fh = new FileHelper();
    UserPreferences up = new UserPreferences();
    ArrayList<String> weather = new ArrayList<String>();


    //Testing the creation of the userPreferences file.
    @Test
    public void testWriteUserPreferences() {
        up.setUserFirstName("Tom");
        up.setUserLastName("Brady");
        up.setUserContactPreference("Sms");
        weather.add("Rain");
        weather.add("Cold");
        weather.add("Heat");
        up.setSelectedWeatherConditions(weather);
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
        up = fh.readUserPreferences();
        assertEquals(up.getUserFirstName(), "Tom");
        assertEquals(up.getUserLastName(), "Brady");
        assertEquals(up.getUserContactPreference(), "Sms");
        assertTrue(up.getSelectedWeatherConditions() instanceof ArrayList);
        assertTrue(up.getSelectedWeatherConditions().containsAll(weather));
        assertEquals(up.getUserContactId(), "1234567890");


    }

}
