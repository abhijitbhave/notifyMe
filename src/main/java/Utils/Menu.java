package Utils;

import Notification.NotificationHelper;
import Users.UserPreferences;
import WeatherAttributes.Weather;
import WeatherUtils.FetchWeather;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


//The Menu class. Could be extended in the future.
public class Menu {

    //The main menu will allow the user to either register a user or create a notification.
    public void mainMenu() {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the notifyMe application.");
        System.out.println("Select an option");
        System.out.println("1. Register User.");
        System.out.println("2. Notify User. \n");
        Integer choice = console.nextInt();
        if (choice == 1) {
            //If user selected to register User, then running that option.
            System.out.println("You have selected to Register user");
            registerUser();
        } else if (choice == 2) {
            //If user chose to send notification, first checking if a user is registered.
            //Note at this point only one user can be registered.
            FileHelper fh = new FileHelper();
            UserPreferences userPreferences = fh.readUserPreferences();
            //If user is not registered then running menu to register user.
            if (userPreferences.getUserFirstName() == null) {
                System.out.println("\nDid not find any registered users.");
                System.out.println("Please register a user.\n");
                registerUser();
            }
            //If user is registered, then will call the notification flow, to be implemented.
            System.out.println("You have selected to Notify user " + userPreferences.getUserFirstName());
            NotificationHelper notificationHelper = new NotificationHelper();
            FetchWeather fetchWeather = new FetchWeather();
            Weather weather = fetchWeather.setWeatherAttributes();
            notificationHelper.complieNotification(weather, userPreferences);

//            Notification notification = new SmsNotification(NotificationType.SMS);
//            notification.sendNotifiation(weather, userPreferences);
            //To be implemented;
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
            if ((!userContactID.contains("@"))) {
                do {
                    System.out.println("Please provide a valid email id");
                    userContactID = console.next();
                }
                while ((!userContactID.contains("@")));
            }

        } else if (up.getUserContactPreference() == "Sms") {
            userContactID = console.next();
            if (!userContactID.matches("\\d{10}")) {
                do {
                    System.out.println("Please provide a valid phone number without spaces or dashes id");
                    userContactID = console.next();
                }
                while (!userContactID.matches("\\d{10}"));
            }

        }
        up.setUserContactId(userContactID);
        //System.out.println("Enter contact till date");
        //up.setUserContactUntilDate(console.next());

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
        System.out.println(
            "You have selected to be notified by: " + up.getUserContactPreference() + " for the following weather conditions: \n" + up
                .getSelectedWeatherConditions().toString()+"\n");
        FileHelper fh = new FileHelper();
        fh.writeUserPreferences(up);
        mainMenu();
    }


}
