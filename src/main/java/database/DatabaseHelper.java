package database;

import Users.UserPreferences;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//This is a class to help manage all operations with the database. The database being used here is DerbyDb.

public class DatabaseHelper {

    //The connection string being defined as static and final since I don't expect this application to connect to multiple databases.
    public static final String dbUrl = "jdbc:derby:weatherDB;create=true";

    //Creating a database schema post checking if one already exists.
    public void createWeatherDatabase() {
        System.out.println("Checking if the database table already exists.");
        if (!weatherTableExists()) {
            System.out.println("The required tables don't exist.\nCreating new tables.");
            //A simple create statement for the various schema items.
            String sql = "create table WEATHER (USER_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "userFirstName varchar(25), userLastName varchar(25), "
                + "selectedWeatherConditions varchar(50), userContactId varchar(200), userContactPreference varchar(20), zipCode INT, isdeleted INT)";
            //Leveraging try with functionality to connect to the database and create the schema.
            try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
                databaseConnection.createStatement().execute(sql);
                System.out.println("Tables created");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        //If its detected that the schema already exists then skipping creation of the database schema.
        } else {
            System.out.println("Required tables exist. Skipping table creation.");
        }
    }

    //A method to insert data into the database. Note that we can leverage frameworks such as hibernate here, however given the scope of this application is very small
    //I am using insert statements directly. However once the scope increases from just weather items, we should certainly look at frameworks.
    public Boolean insertIntoDatabase(UserPreferences userPreferences) {
            try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
                StringBuilder sb = new StringBuilder();
                //Using a simple Lambda to iterate over all weather conditions and put them into a String for insertion.
                userPreferences.getSelectedWeatherConditions().forEach((weather) -> sb.append(weather));
                String sql =
                    "insert into WEATHER (userFirstName, userLastName, selectedWeatherConditions, userContactId, userContactPreference, zipCode, isDeleted) values  ('"
                        + userPreferences.getUserFirstName() + "', '" + userPreferences.getUserLastName() + "', '" + sb.toString() + "', '"
                        + userPreferences.getUserContactId() + "', '" + userPreferences.getUserContactPreference() + "', " + userPreferences.getZipCode() + ", 0)";
                //  Returning a boolean of weather the operation completed successfully.
                return (databaseConnection.createStatement().execute(sql));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
    }

    //Reading the UserPreferences object from the database. Given that there could now be multiple rows returned (given that the implementation now supports multiple users)
    // I am now leveraging ArrayLists of UserPreferences to be read and passed back to the calling function.
    public ArrayList<UserPreferences> readDataFromDatabase() {
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            //The select statement to bring in all the rows where isDeleted is not true.
            String sql = "Select * from weather where isdeleted = 0 order by User_ID";
            //Reading the results into resultSet.
            ResultSet resultSet = databaseConnection.createStatement().executeQuery(sql);
            ArrayList<UserPreferences> userPreferencesList = new ArrayList<>();
           //Enriching the Arraylist of userPreferences.
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
                up.setZipCode(Integer.parseInt(resultSet.getString(7)));
                userPreferencesList.add(up);
            }
            return userPreferencesList;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Returning null if now rows were returned.
        return null;
    }

    //Method to check if the schema exists.
    public boolean weatherTableExists() {
        boolean isTableCreated = false;
        //leveraging the try with strucutre to connect to the database.
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
            //Although system exit is frowned upon, once the database issue is fixed, we will need to restart the database, hence existing.
            System.exit(1);
        }
        return isTableCreated;
    }

    //Method to delete the data from the database. Note that this method only soft deletes the data in the database.
    //A hard delete would need to be performed by actually purging all the rows that are marked with is_delete = 1; This will be done manually.
    //Leveraging try with structure for connection to the database.
    public Boolean deleteDataFromDatabase(Integer userID) {
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            String sql = "Update weather set isdeleted = 1 where user_Id = " + userID;
            return (databaseConnection.createStatement().execute(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
