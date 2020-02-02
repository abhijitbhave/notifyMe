package WeatherAttributes;

import java.util.Date;

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
}
