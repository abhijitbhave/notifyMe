package Utils;

import Users.UserPreferences;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;


public class FileHelper {

    //Defining fileName as a class level attribute to be used by various methods.
    private String fileName = "UserPreferences.properties";


    public boolean checkIfFileExistsI(){
        File file = new File(fileName);
        return file.exists();
    }
    //Leveraging the Java Properties dictionary to create Properties (text) file to be able to store data.
    public boolean writeUserPreferences(UserPreferences userPreferences) {
        //OutputStream to be able to write to the file.

        try (OutputStream outStream = new FileOutputStream(fileName)) {
            //Using the Java properties framework to set user preferences into the properties file.
            Properties userPrefProp = new Properties();
            userPrefProp.setProperty("userID", "1");
            userPrefProp.setProperty("userFirstName", userPreferences.getUserFirstName());
            userPrefProp.setProperty("userLastName", userPreferences.getUserLastName());
            userPrefProp.setProperty("userContactPreference", userPreferences.getUserContactPreference());
            userPrefProp.setProperty("userContactId", userPreferences.getUserContactId());
            //Since date is currently an optional field ensuring that Nulls are handled.
            userPrefProp.setProperty("userContactUntilDate",
                (userPreferences.getUserContactUntilDate() == null) ? "NA" : userPreferences.getUserContactUntilDate().toString());
            userPrefProp.setProperty("selectedWeatherConditions", userPreferences.getSelectedWeatherConditions().toString());
            userPrefProp.store(outStream, "");
            return true;
        }
        //Catching various exceptions that can be thrown by file management.
        //Leveraging a try with approach to ensure that the file is closed regardless of the behavior.
        catch (FileNotFoundException e) {
            try {
                throw new NotifyMeException(e.getMessage());
            } catch (NotifyMeException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            try {
                throw new NotifyMeException(e.getMessage());
            } catch (NotifyMeException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }


    //Leveraging the Java properties framework to read from a file (text) and store the data in the relevant object.
    public ArrayList<UserPreferences> readUserPreferences() {
        //Creating an instance of the object to be enriched.
       ArrayList<UserPreferences> userList = new ArrayList<UserPreferences>();
        UserPreferences userPreferences = new UserPreferences();
        //Will read the various properties and leveraging the getters from the Object class enriching this instance of this object.
        try (InputStream inStream = new FileInputStream(fileName)) {
            Properties userPrefProps = new Properties();
            userPrefProps.load(inStream);
            userPreferences.setUserId(userPrefProps.getProperty("userId"));
            userPreferences.setUserFirstName(userPrefProps.getProperty("userFirstName"));
            userPreferences.setUserLastName(userPrefProps.getProperty("userLastName"));
            userPreferences.setUserContactPreference(userPrefProps.getProperty("userContactPreference"));
            userPreferences.setUserContactId(userPrefProps.getProperty("userContactId"));
            userPreferences.setSelectedWeatherConditions(userPrefProps.getProperty("selectedWeatherConditions"));
            userList.add(userPreferences);
            return userList;
        }
        //Catching various exceptions that can be thrown by file management.
        //Leveraging a try with approach to ensure that the file is closed regardless of the behavior.
        catch (FileNotFoundException e) {
            try {
                throw new NotifyMeException(e.getMessage());
            } catch (NotifyMeException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            try {
                throw new NotifyMeException(e.getMessage());
            } catch (NotifyMeException ex) {
                ex.printStackTrace();
            }
        }
        //Returning an object of type UserPreference.
        return null;
    }

}


