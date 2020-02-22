import Utils.Menu;
import java.io.IOException;


public class NotifyMe {

    //Current main class to drive the application.

    public static final String persistenceType = "FILE";

    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        menu.mainMenu();
    }


}
