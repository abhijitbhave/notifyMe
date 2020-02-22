package Users;


import java.util.ArrayList;
import java.util.Date;

//A detailed object that will save all the user preferences selected via menu.
//All names should be self explaining.

public class UserPreferences {

    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userContactPreference;
    private String userContactId;
    private ArrayList<String> selectedWeatherConditions = new ArrayList<String>();
    private Integer zipCode;
    private Integer isDeleted;


    public ArrayList<String> getSelectedWeatherConditions() {
        return selectedWeatherConditions;
    }

    //Overloading for set weather conditions from properties File.
    public void setSelectedWeatherConditions(String weatherCondition) {
        weatherCondition = weatherCondition.substring(1, weatherCondition.length() - 1);
        String[] weatherconditions = weatherCondition.split(",");
        for (String weather : weatherconditions) {
            this.selectedWeatherConditions.add(weather);
        }
    }

    //Overloading for set weather conditions from Menu.
    public void setSelectedWeatherConditions(ArrayList<String> weatherConditions) {
        this.selectedWeatherConditions = weatherConditions;
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

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public Integer getZipCode() {return zipCode;}

    public void setZipCode(Integer zipCode) {this.zipCode = zipCode;}

    public Integer getIsDeleted() {return isDeleted;}

    public void setIsDeleted(Integer isDeleted) {this.isDeleted = isDeleted;}

    @Override
    public String toString() {
        return
            "userId='" + userId + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userContactPreference='" + userContactPreference + '\'' +
                ", userContactId='" + userContactId + '\'' +
                ", selectedWeatherConditions=" + selectedWeatherConditions +
                ", zipCode = " + zipCode;
    }


}
