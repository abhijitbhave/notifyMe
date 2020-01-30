package Test;

import Notification.EmailNotification;
import Notification.SmsNotification;
import org.junit.Test;
import static org.junit.Assert.*;

public class testNotifyMe {

@Test
public void testSmsNotification() {
    SmsNotification smsNotification = new SmsNotification();
    smsNotification.setUserContact("4084084080");
    assertTrue(smsNotification.getUserPhoneNumber() instanceof Long);

}
@Test
    public void testEmailNotification() {
    EmailNotification emailNotification = new EmailNotification();
    emailNotification.setUserContact("abcd@bu.edu");
    assertTrue(emailNotification.getUserEmail() instanceof String);
    assertTrue(emailNotification.getUserEmail().contains("@"));
}

}
