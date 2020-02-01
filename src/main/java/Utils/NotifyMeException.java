package Utils;

//A User defined exception to catch some cases where a File is not found or IO issues are hit, and the exception posts the error and returns to the main menu.
public class NotifyMeException extends Exception {

    public NotifyMeException(String message) {
        System.out.println("Hit a NotifyMeException");
        System.out.println(message);
        System.out.println("Returning to main menu");
        Menu menu = new Menu();
        menu.mainMenu();
    }
}
