package Notification;

import Users.UserPreferences;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//Concrete implementation of the EmailNotification class which extends from the abstract Notification class.


public class EmailNotification extends Notification {


    //Invoking the super constructor.
    public EmailNotification(NotificationType notificationType) {
        super(notificationType);
    }

    //Given that most of the data is in userPreferences and in the Notification Object both are passed and used as required below.
    //The Method leverages the super.messageBuilder method to frame the right message to be sent via email. Super in this case is Notification.

    public String sendNotification(Object notificationObject, UserPreferences userPreferences) {

        //Setting up the various properties that will be required to send an email using gmail.
        Properties emailProperty = System.getProperties();
        emailProperty.setProperty("mail.smtp.host", "smtp.gmail.com");
        emailProperty.setProperty("mail.smtp.port", "465");
        emailProperty.setProperty("mail.smtp.auth", "true");

        //Acquiring a mailSession using the properties defined above.
        Session emailSession = Session.getDefaultInstance(emailProperty, null);

        //Creating a transport object for type SMTP.
        //SMTPTransport transport = null;

        //Running in a try catch block to try and send an email.
        try (SMTPTransport transport = (SMTPTransport) emailSession.getTransport("smtps")) {
            //Creating the various properties required for sending the email MimeMessage. Mime = Multipurpose Internet Mail Extension.
            MimeMessage emailMessage = new MimeMessage(emailSession);
            emailMessage.setFrom(new InternetAddress("abhijit.bhave@gmail.com"));
            emailMessage.addRecipient(RecipientType.TO, new InternetAddress(userPreferences.getUserContactId(), false));
            emailMessage.setSubject("Mail from NotifyMe");
            emailMessage.setText(super.messageBuilder(notificationObject, userPreferences));
            //Setting the various properties for authenticating gmail.
            transport.connect("smtp.gmail.com", "bhaveprojects", "abhijit1122");
            //Sending the message.
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            System.out.println("Email message sent to: " + userPreferences.getUserContactId());
            return ("Email message sent to: " + userPreferences.getUserContactId());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
