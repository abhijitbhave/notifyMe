package Notification;

import Users.UserPreferences;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

//The class that handles Notification via SMS.
//We are leveraging the Twilio SDK and some code has been referenced from the Twilio API Documentation.

public class SmsNotification extends Notification {

    //Calling the super constructor.
    public SmsNotification(NotificationType notificationType) {
        super(notificationType);
    }

    //Leveraging the super.messageBuilder, to frame the actual message.  Super in this case is Notification.
    //Rest of the code in this class is referenced from Twilio's Api documentation.
    public String sendNotification(Object notificationObject, UserPreferences userPreferences) {
        final String ACCOUNT_SID = "ACc86863eb364dc1d765398f593275eaeb";
        final String AUTH_TOKEN = "948fd5712c2f9a0f1df379ba39eafe43";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber((userPreferences.getUserContactId())),
            new PhoneNumber("+12053089430"), super.messageBuilder(notificationObject.toString())).create();
        System.out.println("Sent message with sid: " + message.getSid());
        return
            ("Sent message with sid: " + message.getSid());
    }
}
