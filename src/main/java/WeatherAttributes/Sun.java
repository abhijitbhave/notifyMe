package WeatherAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//Class to model Sunset and SunRise.
public class Sun {

    private Date sunRise;
    private Date sunSet;

    public Date getSunRise() {
        return sunRise;
    }

    public void setSunRise(Date sunRise) {
        this.sunRise = sunRise;
    }

    public Date getSunSet() {
        return sunSet;
    }

    public void setSunSet(Date sunSet) {
        this.sunSet = sunSet;
    }

    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
        String sunriseTime = dateFormat.format(sunRise);
        String sunsetTime = dateFormat.format(sunSet);
        return ("\nSUN:\nSunRise: " + sunriseTime+",\nSunSet: "+sunsetTime+"\n");
    }
}
