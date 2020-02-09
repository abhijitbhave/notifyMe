package Notification;

//An enum class defining the various types of Notifications support by the Application.
//While today we are supporting only Email and SMs. we can easily extend this to WhatsApp and Slack tomorrow by expanding on enum
// constants as well as making few changes to the Factory class.

public enum NotificationType {
    SMS, EMAIL;
}
