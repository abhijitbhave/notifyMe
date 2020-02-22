package Utils;

import Notification.NotificationHelper;
import Persistence.*;
import Users.UserPreferences;
import WeatherAttributes.Weather;
import WeatherUtils.FetchWeather;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;


//The Menu class. Could be extended in the future.
//Changed the implementation of the Menu class significantly to use Persistence Factory as well as to deal with ArrayList of UserPreferences rather that just one Object.
//Also introduced threads for concurrent runs of the calls for multiple users

public class Menu {


    Persistence persistence = null;
    ArrayList<UserPreferences> userPreferencesList = null;


    public Menu() {
        String fileName = "application.properties";
        try (InputStream inStream = new FileInputStream(fileName)) {
            Properties dataPersistenceProperties = new Properties();
            dataPersistenceProperties.load(inStream);
            //Setting Persistence type based on properties in application.properties file.
            //Currently supported types are database and files.
            String dataPersistanceFlag = dataPersistenceProperties.getProperty("dataPersistenceType");
            PersistenceType persistenceType = null;
            if (dataPersistanceFlag.toUpperCase().matches("DATABASE")) {
                persistenceType = PersistenceType.DATABASE;
            } else if (dataPersistanceFlag.toUpperCase().matches("FILE")) {
                persistenceType = PersistenceType.FILE;
            } else {
                persistenceType = null;
            }
            //Leveraging the persistence factory to create a new database/file persistence object.
            PersistenceFactory persistenceFactory = new PersistenceFactory();
            if (persistenceType != null) {
                this.persistence = persistenceFactory.buildPersistenceLayer(persistenceType);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //The main menu will allow the user to either register a user or create a notification.
    public void mainMenu() {
        Scanner console = new Scanner(System.in);
        //ArrayList<UserPreferences> userPreferences = null;
        System.out.println("Welcome to the notifyMe application.");
        System.out.println("Select an option");
        System.out.println("1. Register User.");
        System.out.println("2. Notify User");
        System.out.println("3. List Registered Users");
        System.out.println("4. Delete Registered User \n");
        Integer choice = null;
        try{
            choice = console.nextInt();
        }
        catch(InputMismatchException e) {
            try {
                throw new NotifyMeException("Invalid Menu Option ");
            } catch (NotifyMeException ex) {
                ex.printStackTrace();
            }
        }
        if (choice == 1) {
            //If user selected to register User, then running that option.
            System.out.println("You have selected to Register user");
            registerUser();
        } else if (choice == 2) {
            //If user chose to send notification, first checking if a user is registered.
            //Note at this point only one user can be registered.
            userPreferencesList = persistence.getUserPreferences();
            //If user is not registered then running menu to register user.
            if (userPreferencesList == null || userPreferencesList.size() == 0) {
                System.out.println("\nDid not find any registered users.");
                System.out.println("Please register a user.\n");
                registerUser();
            }
            //If user is registered, then will call the notification flow, to be implemented.
            System.out.println("You have selected to Notify users: ");
            //Creating an ArrayList of thread where all runnable items will be added to the list and run at once in parallel.
            //That means that the core functionality of the application (fetching data and notifying the user) will be run in parallel.
            ArrayList<Thread> threadList = new ArrayList<Thread>();
            userPreferencesList.forEach(userPreferences -> {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                            System.out.println(userPreferences.getUserFirstName());
                            NotificationHelper notificationHelper = new NotificationHelper();
                            FetchWeather fetchWeather = new FetchWeather();
                            Weather weather = fetchWeather.setWeatherAttributes(userPreferences.getZipCode());
                            notificationHelper.complieNotification(weather, userPreferences);
                    }

                });
                //Adding all threads into the list one by one.
                threadList.add(thread);

            });
            //Using Lambdas to run/start all threads, there by running the calls for multiple users in parallel. This will help when the application scales.
            threadList.forEach(threads -> threads.start());
        }
        //Adding in a new menu choice to list all users. Given that the database version of the application will now support multiple users there may be a need for the
        // administrator to list all registered users.
        else if (choice == 3) {
            System.out.println("You have selected to List all Registered Users");
            ArrayList<UserPreferences> userPreferenceList = persistence.getUserPreferences();
            if(userPreferenceList.size() > 0) {
                userPreferenceList.forEach(userPreferences -> System.out.println(userPreferences.toString()));
            }
            else{
                System.out.println("No registered Users found. Please register a User.\n");
                registerUser();
            }
            mainMenu();
        }
        //Adding a new menu option to delete existing users. This option will work only for database based featureFlag.
        else if (choice == 4) {
            System.out.println("You have selected to Delete a Registered Users.");
            ArrayList<UserPreferences> userPreferenceList = persistence.getUserPreferences();
            userPreferenceList.forEach(userPreferences -> System.out.println(userPreferences.toString()));
            System.out.println("Please enter the UserID you wish to delete");
            String userId = console.next();
            persistence.deleteUserPreferences(userId);
            mainMenu();
        } else {
            System.out.println("Invalid choice.");
            mainMenu();
        }

    }

    public void registerUser() {
        //Using the scanner object to run user registration.
        Scanner console = new Scanner(System.in);
        UserPreferences up = new UserPreferences();
        int menuChoice;

        System.out.println("Welcome to the notifyMe registration page.");
        System.out.println("Please enter First Name");
        up.setUserFirstName(console.next());
        System.out.println("Please enter last Name");
        up.setUserLastName(console.next());
        System.out.println("Please enter communication preference (1. Email 2. Sms)");
        //Running in a try catch block to catch InputMismatchException and handling that scenario.
        try {
            Integer choice = console.nextInt();
            if (choice == 1) {
                try {
                    up.setUserContactPreference("Email");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input");
                    registerUser();
                }
            } else if (choice == 2) {
                try {
                    up.setUserContactPreference("Sms");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input");
                    registerUser();
                }
            } else {
                System.out.println("Invalid choice.");
                registerUser();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            registerUser();
        }

        //using regex to validate that the email address contains @ and the phone number is 10 digits.
        System.out.println("Enter contact identifier");
        String userContactID = null;
        if (up.getUserContactPreference() == "Email") {
            userContactID = console.next();
            Pattern emailAddressPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            if (!emailAddressPattern.matcher(userContactID).matches()) {
                do {
                    System.out.println("Please provide a valid email id");
                    userContactID = console.next();
                }
                while (!emailAddressPattern.matcher(userContactID).matches());
            }

        } else if (up.getUserContactPreference() == "Sms") {
            userContactID = console.next();
            Pattern phoneNumberPattern = Pattern.compile("^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$");
            if (!phoneNumberPattern.matcher(userContactID).matches()) {
                do {
                    System.out.println("Please provide a valid phone number");
                    userContactID = console.next();
                }
                while (!phoneNumberPattern.matcher(userContactID).matches());
            }

        }
        up.setUserContactId(userContactID);
        //Introducing OTP validation of user. Ensures that user is in possession of the cell phone or the email address prior to sending data.
        OTPConfirmation otpConfirmation = new OTPConfirmation();
        otpConfirmation.sendOtp(up);
        //Once OTP is sent to device, launching the JavaFx UI to allow user to validate the information.
        try{

            javafx.application.Application.launch(OTPConfirmation.class);
        }
        catch(IllegalStateException e) {
            try {
                throw new NotifyMeException("Currently the application supports only one run per session. Please restart the app and Register 2nd user. \n"+e.getMessage());
            } catch (NotifyMeException ex) {
                ex.printStackTrace();
            }
        }


        System.out.println("\n Please select which weather conditions you would like to be notified for: (Select all that apply)");
        System.out.println("\t 1. Rain.");
        System.out.println("\t 2. Cold.");
        System.out.println("\t 3. Heat.");
        System.out.println("\t 4. Exit.");
        ArrayList<String> weatherConditions = new ArrayList<String>();
        do {
            menuChoice = console.nextInt();
            switch (menuChoice) {
                case 1:
                    weatherConditions.add("Rain");
                    break;
                case 2:
                    weatherConditions.add("Cold");
                    break;
                case 3:
                    weatherConditions.add("Heat");
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Select a value between 1 and 4");
            }
        }
        while (menuChoice != 4);
        //Once the user has selected the required options, persisting the data to the properties file using the FileHelper class and
        //returninng to main menu
        up.setSelectedWeatherConditions(weatherConditions);

        //A new menu  option for the user to select a zipCode of interest. With the database based implementation of the application we may now have multiple
        // Users and each one may be interested in a different or multiple cities. In order to enable that we are asking the zipCode of interest from the user.
        System.out.println("\n Please provide the zipCode for which the weather forcast needs to be fetched");
        try {
            up.setZipCode(console.nextInt());
        } catch (InputMismatchException e) {
            try {
                throw new NotifyMeException("Invalid Choice. Please provide a numeric choice. " + e.getMessage());
            } catch (NotifyMeException ex) {
                ex.printStackTrace();
            }
        }
        up.setUserId(UUID.randomUUID().toString());

        System.out.println(
            "You have selected to be notified by: " + up.getUserContactPreference() + "\n");
        //Use of lambda for printing the various weather conditions selected by the user from an ArrayList.
        up.getSelectedWeatherConditions()
            .forEach((weather) -> System.out.println(weather + " Is one of the selected weather conditions"));
        System.out.println("\n");
        persistence.setUserPreferences(up);
        mainMenu();
    }


}
