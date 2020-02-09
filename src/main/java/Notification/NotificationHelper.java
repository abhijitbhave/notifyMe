package Notification;

import Users.UserPreferences;


//Helper class for Notifications.
public class NotificationHelper<K> {

    //This Notification Helper class leverages Java Generics to receive multiple types of Objects.
    //While today the application is built only to support Weather updates, the hope is that in the future
    // we should be able to support other types of events as well.
    //As long as the objects of new events contain a toString method (implicit or explicit) the compile Generics method should work fine.

    public void complieNotification(K notificaitonObject, UserPreferences userPreferences) {

        //Leveraging the Notification Factory class to build the right notification object based on the users preferences.
        //IF the type is set to Sms a SMSNotification object is created, else if the type is set to Email an EmailNotification object is created.
        Notification notification = null;
        NotificationType notificationType = null;
        if (userPreferences.getUserContactPreference().toUpperCase().matches("SMS")) {
            notificationType = NotificationType.SMS;
        } else if (userPreferences.getUserContactPreference().toUpperCase().matches("EMAIL")) {
            notificationType = NotificationType.EMAIL;
        } else {
            notificationType = null;
        }
        notification = NotificationFactory.buildNotification(notificationType);
        notification.sendNotification(notificaitonObject, userPreferences);


    }

}
