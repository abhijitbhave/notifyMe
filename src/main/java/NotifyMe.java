import Users.UserPreferences;
import Utils.FileHelper;
import Utils.Menu;
import java.io.IOException;


public class NotifyMe {

    //Current main class to drive the application.

    public static void main(String[] args) throws IOException {
        //Creating a menu object and calling menu. Menu will collect data and enrich the UserPrerefrences Object.
       // Menu menu = new Utils.Menu();
       // UserPreferences up = menu.runMenu();
        //The fileHelper Object will convert the userPreferences Object to a json Object and store it in a file.
        FileHelper fh = new FileHelper();
        //fh.writeUserPrefereces(up);
        fh.readUserPreferences();



    }


}
