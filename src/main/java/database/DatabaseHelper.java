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
        System.out.println("Checking if the database table already exists.\n");
            //Leveraging try with functionality to connect to the database and create the schema.
            try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
                //Checking if the User Details table exists. If it does not will create a new one.
                if(!weatherTableExists( "userDetails"))
                {
                    databaseConnection.createStatement().execute("CREATE TABLE userDetails (USERID VARCHAR(255), userFirstName VARCHAR(25), "
                        + "userLastName VARCHAR(25), userContactPreference VARCHAR(20), zipCode INT, isdeleted INT)");
                    System.out.println("Created UserDetails table");
                }
                else{
                    System.out.println("UserDetails Table exists, skipping creation");
                }
                //Checking if the User Contact Details table exists. If it does not will create a new one.
                if(!weatherTableExists( "UserContactDetails")) {
                    databaseConnection.createStatement().execute("CREATE TABLE UserContactDetails(USERID VARCHAR(255), selectedWeatherConditions VARCHAR(50), "
                        + "userContactId VARCHAR(200))");
                    System.out.println("Created UserContactDetails table");
                }
                else{
                    System.out.println("UserContactDetails Table exists, skipping creation\n");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    //A method to insert data into the database. Note that we can leverage frameworks such as hibernate here, however given the scope of this application is very small
    //I am using insert statements directly. However once the scope increases from just weather items, we should certainly look at frameworks.
    //Note that I am performing two separate inserts at this point however if the tables numbers grow we need to consider frameworks like hibernate.
    public Boolean insertIntoDatabase(UserPreferences userPreferences) {
            try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
                StringBuilder sb = new StringBuilder();
                Boolean successfulInsert = false;
                //Using a simple Lambda to iterate over all weather conditions and put them into a String for insertion.
                userPreferences.getSelectedWeatherConditions().forEach((weather) -> sb.append(weather + " "));
                //  Returning a boolean of weather the operation completed successfully.
                successfulInsert = (databaseConnection.createStatement().execute("insert into UserDetails (userId, userFirstName, userLastName,  userContactPreference, zipCode, isDeleted) values  "
                    + "('" + userPreferences.getUserId() + "', '" + userPreferences.getUserFirstName() + "', "
                    + "'" + userPreferences.getUserLastName() + "', '" + userPreferences.getUserContactPreference() + "', "
                    + "" + userPreferences.getZipCode() + ", 0)"));
                successfulInsert = (databaseConnection.createStatement().execute("insert into UserContactDetails (userId,  selectedWeatherConditions, userContactId) values  "
                    + "('" + userPreferences.getUserId() + "', '" + sb.toString() + "', '"
                    + userPreferences.getUserContactId() + "')"));
                return successfulInsert;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
    }

    //Reading the UserPreferences object from the database. Given that there could now be multiple rows returned (given that the implementation now supports multiple users)
    // I am now leveraging ArrayLists of UserPreferences to be read and passed back to the calling function.
    public ArrayList<UserPreferences> readDataFromDatabase() {
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            //The select statement to bring in all the rows where isDeleted is not true. Note that the select statement contains a join and is reading data
            //from both the tables.
            //Reading the results into resultSet.
            ResultSet resultSet = databaseConnection.createStatement().executeQuery("SELECT ud.USERID, ud.UserFirstName, ud.UserLastName, ucd.selectedWeatherConditions, ucd.userContactId, "
                + "ud.userContactPreference, ud.zipCode "
                + "FROM UserDetails ud JOIN UserContactDetails ucd ON ud.UserId = ucd.UserID "
                + "WHERE ud.isDeleted = 0 "
                + "ORDER BY ud.UserLastName");
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
    public boolean weatherTableExists(String tableName) {
        boolean isTableCreated = false;
        //leveraging the try with structure to connect to the database.
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)){
            DatabaseMetaData dbMetaData = databaseConnection.getMetaData();
            ResultSet metaDataResultSet = dbMetaData.getTables(null, null, null, new String[] {"TABLE"});
            while (metaDataResultSet.next()) {
                if (metaDataResultSet.getString(3).toUpperCase().matches(tableName.toUpperCase())) {
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
    public Boolean deleteDataFromDatabase(String userID) {
        try (Connection databaseConnection = DriverManager.getConnection(dbUrl)) {
            String sql = "Update userDetails set isdeleted = 1 where userId = '" + userID+ "'";
            return (databaseConnection.createStatement().execute(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
