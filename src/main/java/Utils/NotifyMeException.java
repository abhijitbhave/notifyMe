package Utils;

//A User defined exception for the notify me application.
// The intent is to catch various exceptions in the Application and be able to return back to main Menu.

public class NotifyMeException extends Exception {

    public NotifyMeException(String message) {
        System.out.println("\n ***** Caught a NotifyMeException *****");
        System.out.println(message);
        System.out.println("\n Returning to main menu");
        Menu menu = new Menu();
        menu.mainMenu();
    }
}
