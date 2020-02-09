package Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


//there is a need to calculate tomorrows date in multiple places int he application, hence creating a Static method to help resove code duplicaiton.

public class DateHelper {

    //Simple method to get todays, date, calculate tomorrow's date, and return it in the yyyy-mm-dd format.

    public static String getTomorrow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date today = new Date();
        cal.setTime(today);
        cal.add(Calendar.DATE, 1);
        Date tomorrow = cal.getTime();
        return sdf.format(tomorrow);
    }

}
