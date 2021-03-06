import Users.UserPreferences;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestUserPreferences {

    UserPreferences up = new UserPreferences();
    ArrayList<String> weather = new ArrayList<String>();


    //Everything in Before will be run prior to the tests being run.
    @Before
    public void init() {
        up.setUserFirstName("Tom");
        up.setUserLastName("Brady");
        up.setUserContactPreference("Sms");
        weather.add("Rain");
        weather.add("Cold");
        weather.add("Heat");
        up.setSelectedWeatherConditions(weather);
        up.setUserContactId("1234567890");
    }

    //A test to make sure that whatever was set in the UserPreferecens above was set correctly un value and type.
    @Test
    public void testUserPreferences() {


        assertTrue(up.getUserFirstName() == "Tom");
        assertTrue(up.getUserLastName() == "Brady");
        assertTrue(up.getUserContactPreference() == "Sms");
        assertTrue(up.getSelectedWeatherConditions() instanceof ArrayList);
        assertTrue(up.getSelectedWeatherConditions().contains("Rain"));
        assertTrue(up.getUserContactId() == "1234567890");
        up.setSelectedWeatherConditions("[Rain, Wind, Heat]");
        assertTrue(up.getSelectedWeatherConditions() instanceof ArrayList);
        assertTrue(up.getSelectedWeatherConditions().containsAll(weather));
    }

    //Testing the too String functionality of the UserPreference object.
    @Test
    public void testUserPreferencesToString(){
        assertTrue(up.toString()instanceof String);
        assertTrue(up.toString().contains("1234567890"));
        assertTrue(up.toString().contains("Tom"));
        assertTrue(up.toString().contains("Brady"));
    }

}
