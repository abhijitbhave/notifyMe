package WeatherAttributes;

//Class to model Temperature conditions.
public class Temperature {

    private Integer temperature;
    private Integer feelsLike;

    public Integer getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Integer feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
