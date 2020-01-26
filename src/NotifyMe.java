import main.Users.UserPreferences;
import main.Utils.Menu;
public class NotifyMe {

    //Current main class to drive the application.

    public static void main(String[] args) {

        Menu menu = new main.Utils.Menu();
        UserPreferences up = menu.runMenu();
        System.out.println(up.toString());
    }


}
