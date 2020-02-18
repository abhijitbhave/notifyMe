import static org.junit.Assert.assertTrue;

import Notification.Notification;
import Notification.NotificationFactory;
import Notification.NotificationType;
import Persistence.*;
import Users.UserPreferences;
import org.junit.Test;

public class testPersistence {


    UserPreferences userPreferences =null;

    //Testing sending SMS. the assumption is that if we see "Sid" returned then the SMS was sent out.
//    @Test
//    public void testDatabasePersistence() {
//        userPreferences.setUserContactId("Sms");
//        userPreferences.setUserContactId("9255489200");
//         Persistence persistence = PersistenceFactory.buildPersistenceLayer(PersistenceType.DATABASE);
//        assertTrue(persistence.sendNotification("Test", userPreferences).contains("sid:"));
//        assertTrue(persistence.getUserPreferences());
//    }

    //Testing sending Emaisl. the assumption is that if we see "sent to:" returned then the Email was sent out.
@Test
    public void testEmailNotification() {
    userPreferences.setUserContactId("Email");
    userPreferences.setUserContactId("abhave@bu.edu");
    Notification notification =  NotificationFactory.buildNotification(NotificationType.EMAIL);
    assertTrue(notification.sendNotification("Test", userPreferences).contains("sent to:"));
}

}
