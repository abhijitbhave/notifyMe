import Utils.Menu;
import java.io.IOException;
import java.util.UUID;


public class NotifyMe {

    //Current main class to drive the application.

    public static final String persistenceType = "FILE";

    public static void main(String[] args) throws IOException {
        System.out.println(UUID.randomUUID());
        Menu menu = new Menu();
        menu.mainMenu();
    }


}
