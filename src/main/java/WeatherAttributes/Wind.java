package WeatherAttributes;

//Class to model Wind conditions.
public class Wind {

    private Double windSpeed;
    private Double degrees;

    public Double getDegrees() {
        return degrees;
    }

    public void setDegrees(Double degrees) {
        this.degrees = degrees;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }


    public String toString() {
        return ("\nWIND: \nwindSpeed: " + windSpeed.toString()+",\nDegrees: "+degrees.toString()+"\n");
    }
}
