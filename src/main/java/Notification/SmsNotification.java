package Notification;

public class SmsNotification extends Notification {

    //Concrete implementation of the SmsNotification class that extends from the main.Notification abstract class.
    private Long userPhoneNumber;

    public Long getUserPhoneNumber() {
        return userPhoneNumber;
    }

    //Use of downcasting to convert a String to Integer for the users phone number.
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.setUserContact(userPhoneNumber);
    }

    //Use of downcasting to convert a String to Integer for the users phone number.
    @Override
    public void setUserContact(String userContact) {
        this.userPhoneNumber = Long.parseLong(userContact);
    }

    //SmsNotification objects message Builder.
    public String messageBuilder() {
        String message = "This is a Sms Notification";
        return message;
    }


}
