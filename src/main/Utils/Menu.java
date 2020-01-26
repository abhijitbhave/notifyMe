package main.Utils;

import main.Users.UserPreferences;
import java.util.Scanner;

//The main.Utils.Menu class. Could be extended in the future.
public class Menu {


    public UserPreferences runMenu() {
        Scanner console = new Scanner(System.in);
        UserPreferences up = new UserPreferences();
        int menuChoice;

        System.out.println("\n Welcome to the notifyMe registration page.");
        System.out.println("\n Please enter First Name");
        up.setUserFirstName(console.next());
        System.out.println("\n Please enter last Name");
        up.setUserLastName(console.next());
        System.out.println("\n Please enter comunication preference (1. Email 2. Sms)");
        Integer choice = console.nextInt();
        if (choice == 1) {
            up.setUserContactPreference("Email");
        } else if (choice == 2) {
            up.setUserContactPreference("Sms");
        } else {
            System.out.println("Invalid choice.");
            runMenu();
        }
        System.out.println("Enter contact identifier");
        up.setUserContactId(console.next());
        //System.out.println("Enter contact till date");
        //up.setUserContactUntilDate(console.next());

        System.out.println("\n Please select which weather conditions you would like to be notified for: (Select all that apply)");
        System.out.println("\n\t 1. Rain.");
        System.out.println("\n\t 2. Cold.");
        System.out.println("\n\t 3. Heat.");
        System.out.println("\n\t 4. Exit.");
        do {
            menuChoice = console.nextInt();
            switch (menuChoice) {
                case 1:
                    up.setSelectedWeatherConditions("Rain");
                    break;
                case 2:
                    up.setSelectedWeatherConditions("Cold");
                    break;
                case 3:
                    up.setSelectedWeatherConditions("Heat");
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Select a value between 1 and 4");
            }


        }
        while (menuChoice != 4);
        return (up);

    }


}
