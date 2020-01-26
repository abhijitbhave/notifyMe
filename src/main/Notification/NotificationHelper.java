package main.Notification;

import main.Users.UserPreferences;

//Helper class for Notifications.
public class NotificationHelper {

    public void complieNotification(UserPreferences userPreferences) {

        //Creating a notificaiton object.
        Notification notification;

        //Setting up the main.Notification object based on weather the user selected Email main.Notification or SmsNotification.
        //Also setting the userContactIdentifier once the type of main.Notification is identified.
        if (userPreferences.getUserContactPreference() == "Email") {
            notification = new EmailNotification();
            notification.setUserContact((userPreferences.getUserContactId()));
        } else {
            notification = new SmsNotification();
            notification.setUserContact((userPreferences.getUserContactId()));
        }

    }

}
