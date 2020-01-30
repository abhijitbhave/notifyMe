package Notification;

//This will be the abstract notification package which will be extended by the concrete sms and email notification classes.
//Defination of the abstract class will make it easier for future implementations of new concrete classes, Eg. Slack or Whatsapp.
public abstract class Notification {

    //Method to retrieve UserPreferences.
    public void getUserOptions() {
        //TBD
    }

    //Method to set the required schedules based on dates and times for main.Notification. Expected to be a concrete method.
    public void setNotificationSchedule() {
    }

    //A probable Send notification method. Expected to be a concrete method.
    public void sendNotifiation() {
    }

    //Defined as a abstract method to set the users contact Identifier. Expected to have concrete classes implement this since the contact identifier
    //could take on various data types.
    public abstract void setUserContact(String userContact);

    //The message builder method will actually build the message, but will probably be overridden  by the concrete classes.
    public String messageBuilder() {
        String message = "This is a default Notification";
        return message;
    }

}
