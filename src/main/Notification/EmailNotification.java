package main.Notification;

//Concrete implementation of the EmailNotification class which extends from the abstract main.Notification class.
public class EmailNotification extends Notification {

    //Attribute to store the users email address.
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.setUserContact(userEmail);
    }

    public void setUserContact(String userContact) {
        this.userEmail = userContact;
    }

    //Message builder for Emails.
    public String messageBuilder() {
        String message = "This is a Email main.Notification";
        return message;
    }

}
