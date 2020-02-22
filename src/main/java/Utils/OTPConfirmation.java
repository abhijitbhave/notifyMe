package Utils;

import Notification.Notification;
import Notification.NotificationFactory;
import Notification.NotificationType;
import Users.UserPreferences;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


//Intend: The intend of this class is to be able to bring up a JavaFX based UI window where the user can enter the OTP (One Time Passcode) sent to him/her
//via an Sms or Email based on their selection in the Menu. Once the user inputs the code and hits the validate button, the code will validate the OTP sent to ensure
//That the user that typing the input is the user in possession of the device/email before sending any notifications to them.
//PreReq- user provides valid email address or cell phone number in the menu and has access to the device/email.
//PostExecution - if the code is validated, the control will be sent back to the Menu. If the code is not validated the user will have endless re-try options.
//Creating a JavaFx application to be able to validate that the email address of the phone number provided by the user is actually in his position.
//This class makes use of Streams as well as Lambdas to run the said use case.

public class OTPConfirmation extends Application {


    /**
     * The main entry point for all JavaFX applications. The start method is called after the init method has returned, and after the
     * system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set. The primary stage will
     * be embedded in the browser if the application was launched as an applet. Applications may create other stages, if needed, but
     * they will not be primary stages and will not be embedded in the browser.
     */

    //Defining a Long variable to be able to store the Generated Otp.
    public static Long generatedOTP;

    //As with any JavaFx application, we are overriding the Start method below.
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Setting the stage. Calling it otpWindow.
        Stage otpWindow = primaryStage;
        //Setting a title for the Window when its displayed.
        otpWindow.setTitle("OTP Validation");
        //Defining a text box for user to provide input.
        TextField inputString = new TextField();
        //Creating a button with the text "Validate" for user to click and validate OTP.
        Button button = new Button("Validate");
        //When the button is clicked by the user, the following lambda function will be executed.
        //Primarily the lambda calls the validateOTP() method to validate if the user entered OTP matches the one that was generated.
        //If the two match, an alert window will be shown which will confirm the validation and present an OK button.
        // Once the user clicks the OK button the notifyMe application will continue with its menu.
        //If the OTPs don't match, the else loop will be called and the user will see an alert window with the confirmation that validation failed.
        //The user will have an option to retry validation.
        button.setOnAction(e -> {
            if (validateOTP(generatedOTP, Long.parseLong(inputString.getText()))) {
                Stage alertWindow = new Stage();
                alertWindow.initModality(Modality.APPLICATION_MODAL);
                Label label = new Label("Validated");
                Button alterButton = new Button("OK");
                alterButton.setOnAction(a -> {
                    alertWindow.close();
                    Platform.exit();
                    try {
                        stop();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                VBox alertLayout = new VBox(10);
                alertLayout.setPadding(new Insets(20, 20, 20, 20));
                alertLayout.getChildren().addAll(label, alterButton);
                Scene alertScene = new Scene(alertLayout, 300, 200);
                otpWindow.setScene(alertScene);
                otpWindow.show();
            } else {
                Stage alertWindow = new Stage();
                alertWindow.initModality(Modality.APPLICATION_MODAL);
                Label label = new Label("Failed Validation");
                Button retryButton = new Button("Retry");
                //Button cancelButton = new Button("Cancel");
                retryButton.setOnAction(a -> {
                    alertWindow.close();
                });

                VBox alertLayout = new VBox(10);
                alertLayout.setPadding(new Insets(20, 20, 20, 20));
                alertLayout.getChildren().addAll(label, retryButton);
                Scene alertScene = new Scene(alertLayout, 300, 200);
                otpWindow.setScene(alertScene);
                otpWindow.show();
            }
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(40, 40, 40, 40));
        layout.getChildren().addAll(inputString, button);
        Scene scene = new Scene(layout, 300, 200);
        otpWindow.setScene(scene);
        otpWindow.show();
    }

    //A method in the class which leverages Streams to generate SecureRandom numbers.
    //We are leveraging int streams here where we create random 6 numbers between 0 and 99.
    //The streams are mapping the object to Strings since in future we may want to generate  a alphanumeric code.
    public long generateOtp() {
        SecureRandom randomNumbers = new SecureRandom();
        String optNumbers = randomNumbers.ints(6, 0, 99)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(""));
        return Long.parseLong(optNumbers);
    }

    //A simple validate function that compares the two objects and returns the result.
    public boolean validateOTP(Long optNumbers, Long userEnteredNumbers) {
        return optNumbers.equals(userEnteredNumbers) ? true : false;
    }

    //The method which actually sends the OTP out to the method of choice. (Sms/Email)
    public void sendOtp(UserPreferences userPreferences) {
         this.generatedOTP = generateOtp();
        NotificationType notificationType = null;
        if (userPreferences.getUserContactPreference().toUpperCase().matches("SMS")) {
            notificationType = NotificationType.SMS;
        } else if (userPreferences.getUserContactPreference().toUpperCase().matches("EMAIL")) {
            notificationType = NotificationType.EMAIL;
        } else {
            notificationType = null;
        }
        Notification notification = NotificationFactory.buildNotification(notificationType);
        notification.sendNotification("The code for registering is: " + generatedOTP, userPreferences);

    }

}

