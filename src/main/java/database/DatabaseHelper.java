package database;

import Users.UserPreferences;
import Utils.FileHelper;
import Utils.NotifyMeException;
import com.twilio.rest.chat.v1.service.User;
import com.twilio.twiml.voice.Connect;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseHelper {

    public static final String dbUrl = "jdbc:derby:weatherDB;create=true";

    public void createWeatherDatabase() {

        System.out.println("Checking if the database table already exists.");
        if (!weatherTableExists()) {
            System.out.println("The required tables dont exist.\nCreating new tables.");
            try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
                databaseConnection.createStatement()
                    .execute(
                        "create table WEATHER (USER_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "userFirstName varchar(25), userLastName varchar(25), "
                            + "selectedWeatherConditions varchar(50), userContactId varchar(200), userContactPreference varchar(20))");
                System.out.println("Tables created");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Required tables exist. Skipping table creation.");
        }
    }

    public Boolean insertIntoDatabase(UserPreferences userPreferences) {
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            StringBuilder sb = new StringBuilder();
            userPreferences.getSelectedWeatherConditions().forEach((weather) -> sb.append(weather));
            String sql =
                "insert into WEATHER (userFirstName, userLastName, selectedWeatherConditions, userContactId, userContactPreference) values  ('"
                    + userPreferences.getUserFirstName() + "', '" + userPreferences.getUserLastName() + "', '" + sb.toString() + "', '"
                    + userPreferences.getUserContactId() + "', '" + userPreferences.getUserContactPreference() + "')";
            return (databaseConnection.createStatement().execute(sql));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<UserPreferences> readDataFromDatabase() {
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            String sql = "Select * from weather";
            ResultSet resultSet = databaseConnection.createStatement().executeQuery(sql);
            ArrayList<UserPreferences> userPreferencesList = new ArrayList<>();
            while (resultSet.next()) {
                UserPreferences up = new UserPreferences();
                up.setUserId(resultSet.getString(1));
                up.setUserFirstName(resultSet.getString(2));
                up.setUserLastName(resultSet.getString(3));
                up.setSelectedWeatherConditions(
                    (ArrayList<String>) Stream.of(resultSet.getString(4).split(" "))
                        .collect(Collectors.toCollection(ArrayList<String>::new)));
                up.setUserContactId(resultSet.getString(5));
                up.setUserContactPreference(resultSet.getString(6));
                userPreferencesList.add(up);
            }
            return userPreferencesList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean weatherTableExists() {
        boolean isTableCreated = false;
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            DatabaseMetaData dbMetaData = databaseConnection.getMetaData();
            ResultSet metaDataResultSet = dbMetaData.getTables(null, null, null, new String[] {"TABLE"});
            while (metaDataResultSet.next()) {
                if (metaDataResultSet.getString(3).toUpperCase().matches("WEATHER")) {
                    isTableCreated = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Hit a SQLException, probably because the database is open via command line using ij.");
            System.out.println("Quitting the application. Please fix the database issue and restart the application");
            e.printStackTrace();
            System.exit(1);
        }
        return isTableCreated;
    }

    public Boolean deleteDataFromDatabase(Integer userID) {
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            String sql = "Delete from weather where user_Id = " + userID;
            System.out.println(sql);
            return (databaseConnection.createStatement().execute(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
