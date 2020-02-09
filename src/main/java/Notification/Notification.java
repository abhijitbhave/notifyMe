package Notification;

import Users.UserPreferences;
import Utils.DateHelper;
import Utils.FileHelper;


//This will be the abstract notification package which will be extended by the concrete sms and email notification classes.
//Definition  of the abstract class will make it easier for future implementations of new concrete classes, Eg. Slack or Whatsapp.
public abstract class Notification<K> {

    private NotificationType notificationType = null;

    public Notification(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    //Method to retrieve UserPreferences.
    private UserPreferences getUserOptions() {
        FileHelper fh = new FileHelper();
        UserPreferences userPreferences = fh.readUserPreferences();
        return userPreferences;

    }

    //An abstract sendNotification method that will be implemented by concrete Child classes.
    public abstract String sendNotification(K notificationObject, UserPreferences userPreferences);

    //A concrete method to be leveraged by all child classes to be able to frame the actual message being sent out by Email or Sms.
    //As long as the Notification Object supports a toString method, the type of even would not matter for a notification to be framed.
    public String messageBuilder(K outgoingMessage) {
        UserPreferences userPreferences = getUserOptions();
        StringBuilder message = new StringBuilder();
        message.append("This message is for " + userPreferences.getUserFirstName() + "\n");
        message.append("Here is the weather for tomorrow: " + DateHelper.getTomorrow());
        message.append("\n *******************************\n");
        message.append(outgoingMessage.toString());
        message.append("\n *******************************\n");
        return message.toString();

    }

}
