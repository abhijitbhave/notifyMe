
import Notification.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class testGenericNotification {


    //Testing the Generic message builder method. The idea here is that since the method accepts a Generic datatype, as long as the datatype has a
    // toString method supported, the messageBuilder method should work and return the required String.
    //Run the test for Email and SMS route leveraging Factory design pattern.
    Notification notification = null;

    @Test
    public void testGenericSmsNotification() {
        String message = "This is a TEST message";
        notification = new SmsNotification(NotificationType.SMS);
        assertTrue(notification.messageBuilder(message).contains("TEST"));

    }

    @Test
    public void testGenericEmailNotification() {
        String message = "This is a TEST message";
        notification = new EmailNotification(NotificationType.EMAIL);
        assertTrue(notification.messageBuilder(message).contains("TEST"));
    }

}
