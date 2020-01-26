package main.Users;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

//A detailed object that will save all the user preferences selected via menu.
//All names should be self explaining.

public class UserPreferences {

    private String userFirstName;
    private String userLastName;
    private String userContactPreference;
    private String userContactId;
    private Date userContactUntilDate;
    private ArrayList<String> selectedWeatherConditions = new ArrayList<String>();

    public ArrayList<String> getSelectedWeatherConditions() {
        return selectedWeatherConditions;
    }

    public void setSelectedWeatherConditions(String weatherCondition) {
        this.selectedWeatherConditions.add(weatherCondition);
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserContactPreference() {
        return userContactPreference;
    }

    public void setUserContactPreference(String userContactPreference) {
        this.userContactPreference = userContactPreference;
    }

    public String getUserContactId() {
        return userContactId;
    }

    public void setUserContactId(String userContactId) {
        this.userContactId = userContactId;
    }

    public Date getUserContactUntilDate() {
        return userContactUntilDate;
    }

    public void setUserContactUntilDate(String date) {
        if(date != "" || date != " "){
            LocalTime userContactUntilDate = LocalTime.parse(date);
        }
        else
            userContactUntilDate = null;
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
            "userFirstName='" + userFirstName + '\'' +
            ", userLastName='" + userLastName + '\'' +
            ", userContactPreference='" + userContactPreference + '\'' +
            ", userContactId='" + userContactId + '\'' +
            ", userContactUntilDate=" + userContactUntilDate +
            ", selectedWeatherConditions=" + selectedWeatherConditions +
            '}';
    }
}
