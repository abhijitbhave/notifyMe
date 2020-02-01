package Utils;

import Users.UserPreferences;
import com.sun.tools.corba.se.idl.constExpr.Not;
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


    //Leveraging the Java Properties dictionary to create Prosperties (text) file to be able to store data.
    public void writeUserPreferences(UserPreferences userPreferences) {
        //OutputStream to be able to write to the file.
        OutputStream outStream = null;
        try {
            //Using the Java properties framework to set user preferences into the properties file.
            outStream = new FileOutputStream(fileName);
            Properties userPrefProp = new Properties();
            userPrefProp.setProperty("userFirstName", userPreferences.getUserFirstName());
            userPrefProp.setProperty("userLastName", userPreferences.getUserLastName());
            userPrefProp.setProperty("userContactPreference", userPreferences.getUserContactPreference());
            userPrefProp.setProperty("userContactId", userPreferences.getUserContactId());
            //Since date is currently an optional field ensuring that Nulls are handled.
            userPrefProp.setProperty("userContactUntilDate",
                (userPreferences.getUserContactUntilDate() == null) ? "NA" : userPreferences.getUserContactUntilDate().toString());
            userPrefProp.setProperty("selectedWeatherConditions", userPreferences.getSelectedWeatherConditions().toString());
            userPrefProp.store(outStream, "");

        }
        //Catching various exceptions that can be thrown by file management.
        // Also included a finally block to close the file which can have its own exception in the catch block.
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
        } finally {
            try {
                System.out.println("Completed the call to write to file for user: " + userPreferences.getUserFirstName());
                outStream.close();
            } catch (IOException e) {
                try {
                    throw new NotifyMeException(e.getMessage());
                } catch (NotifyMeException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }


    //Leveraging the Java properties framework to read from a file (text) and store the data in the relevant object.
    public UserPreferences readUserPreferences() {
        //Creating an instance of the object to be enriched.
        UserPreferences userPreferences = new UserPreferences();
        InputStream inStream = null;
        //Will read the various properties and leveraging the getters from the Object class enriching this instance of this object.
        try {
            inStream = new FileInputStream(fileName);
            Properties userPrefProp = new Properties();
            userPrefProp.load(inStream);
            userPreferences.setUserFirstName(userPrefProp.getProperty("userFirstName"));
            userPreferences.setUserLastName(userPrefProp.getProperty("userLastName"));
            userPreferences.setUserContactPreference(userPrefProp.getProperty("userContactPreference"));
            userPreferences.setUserContactId(userPrefProp.getProperty("userContactId"));
            userPreferences.setSelectedWeatherConditions(userPrefProp.getProperty("selectedWeatherConditions"));
        }
        //Catching various exceptions that can be thrown by file management.
        // Also included a finally block to close the file which can have its own exception in the catch block.
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
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }

            } catch (IOException e) {
                try {
                    throw new NotifyMeException(e.getMessage());
                } catch (NotifyMeException ex) {
                    ex.printStackTrace();
                }
            }


        }
        //Finally returning an object of type UserPreference.
        return userPreferences;
    }

}


