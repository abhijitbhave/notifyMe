import Notification.EmailNotification;
import Notification.Notification;
import Notification.NotificationType;
import Notification.SmsNotification;
import Notification.NotificationFactory;
import Users.UserPreferences;
import Utils.FileHelper;
import java.util.logging.FileHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class testNotification {

    UserPreferences userPreferences = new FileHelper().readUserPreferences();

    //Testing sending SMS. the assumption is that if we see "Sid" returned then the SMS was sent out.
    @Test
    public void testSmsNotification() {
        userPreferences.setUserContactId("Sms");
        userPreferences.setUserContactId("9255489200");
        Notification notification =  NotificationFactory.buildNotification(NotificationType.SMS);
        assertTrue(notification.sendNotification("Test", userPreferences).contains("sid:"));
    }

    //Testing sending Emaisl. the assumption is that if we see "sent to:" returned then the Email was sent out.
@Test
    public void testEmailNotification() {
    userPreferences.setUserContactId("Email");
    userPreferences.setUserContactId("abhave@bu.edu");
    Notification notification =  NotificationFactory.buildNotification(NotificationType.EMAIL);
    assertTrue(notification.sendNotification("Test", userPreferences).contains("sent to:"));
}

}
