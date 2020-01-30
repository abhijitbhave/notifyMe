package Utils;

import Users.UserPreferences;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;



public class FileHelper {

    //Defining fileName as a class level attribute to be used by various methods.
    private String fileName = "UserPreferences.properties";


    public void writeUserPrefereces(UserPreferences userPreferences) {
        try {
            OutputStream output = new FileOutputStream("UserPreferences.properties");
            Properties prop = new Properties();
            prop.setProperty("userFirstName", userPreferences.getUserFirstName());
            prop.setProperty("userContactPreference", userPreferences.getUserContactPreference());
            prop.setProperty("userContactId", userPreferences.getUserContactId());
            prop.setProperty("userContactUntilDate",
                (userPreferences.getUserContactUntilDate().toString() != null) ? userPreferences.getUserContactUntilDate().toString()
                    : "NA");
            prop.setProperty("selectedWeatherConditions", userPreferences.getSelectedWeatherConditions().toString());
            prop.store(output, "");
            System.out.println(prop);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }


    public UserPreferences readUserPreferences() {
        try {
            InputStream input = new FileInputStream(fileName);
            UserPreferences userPreferences = new UserPreferences();
            Properties prop = new Properties();
            prop.load(input);
            userPreferences.setUserFirstName(prop.getProperty("userFirstName"));
            userPreferences.setUserLastName(prop.getProperty("userLastName"));
            userPreferences.setUserContactPreference(prop.getProperty("userContactPreference"));
            userPreferences.setUserContactId(prop.getProperty("userContactId"));
            userPreferences.setSelectedWeatherConditions(prop.getProperty("selectedWeatherConditions"));
            System.out.println(userPreferences.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}



