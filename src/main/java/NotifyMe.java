
import static javafx.application.Application.launch;

//import Utils.Menu;
import Utils.Menu;
import java.io.IOException;


public class NotifyMe {

    //Current main class to drive the application.

    public static final String persistenceType = "FILE";

    public static void main(String[] args) throws IOException {


   //     ConfirmationWindow cw = null;
   //     cw.start();
       // Creating a menu object and calling menu. Menu will collect data and enrich the User Prerefrences Object.
       // FileReadHelper fileReadHelper = new FileReadHelper();
      //  ConfirmationWindow cw = new ConfirmationWindow();
      //  cw.start();

           Menu menu = new Menu();
           menu.mainMenu();
//        FileHelper fh = new FileHelper();
//        UserPreferences userPreferences = fh.readUserPreferences();
//        System.out.println(userPreferences.toString());
//        FetchWeather fw = new FetchWeather();
//        Weather weather = fw.setWeatherAttributes();
//        SmsNotification smsNotification = new SmsNotification();
//        smsNotification.messageBuilder(weather, userPreferences);



    }


}
