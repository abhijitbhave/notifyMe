package Persistence;

import static Notification.NotificationType.EMAIL;
import static Notification.NotificationType.SMS;

import Notification.Notification;
import Utils.NotifyMeException;
import database.DatabaseHelper;

//given that we have more than one type of Notifications and we may extend this in the future, I thought this would be a good opportunity to implement the factory pattern.
//In this factory pattern, the NotificationFactory is the Factory class. The class is driven by a NotificationType which is defined as enums.
//Based on the value of the enum the factory will either generate an instance of the SMSNotification Class or the EmailNotification class at run time,
public class PersistenceFactory {
    //The buildNotification method will be used to build the type of Notification instance the user has opted for.
    public static Persistence buildPersistenceLayer(PersistenceType persistenceType) {


        Persistence persistence = null;
    switch (persistenceType) {
            case DATABASE:
                persistence = new DatabasePersistence(PersistenceType.DATABASE);

                break;

            case FILE:
                persistence = new FilePersistence(PersistenceType.FILE);
                break;
            default:
                try {
                    throw new NotifyMeException("Invalid option. Restarting the process");
                } catch (NotifyMeException e) {
                    e.printStackTrace();
                }
                break;

        }
        return persistence;
    }


}
