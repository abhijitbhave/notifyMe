
import Utils.FileHelper;
import Utils.Menu;
import java.io.IOException;



public class NotifyMe {

    //Current main class to drive the application.

    public static void main(String[] args) throws IOException {

        //Creating a menu object and calling menu. Menu will collect data and enrich the User Prerefrences Object.
       Menu menu = new Menu();
       menu.mainMenu();
//        FileHelper fh = new FileHelper();
//        UserPreferences userPreferences = fh.readUserPreferences();
//        FetchWeather fw = new FetchWeather();
//        Weather weather = fw.setWeatherAttributes();
//        SmsNotification smsNotification = new SmsNotification();
//        smsNotification.messageBuilder(weather, userPreferences);



    }


}
