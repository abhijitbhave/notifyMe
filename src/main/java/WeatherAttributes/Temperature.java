package WeatherAttributes;

//Class to model Temperature conditions.
public class Temperature {

    private Double temperature;
    private Double feelsLike;
    private Double maxTemp;
    private Double minTemp;

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String toString() {
        return ("\nTEMP: \nTemperature: " + temperature.toString()+",\nFeels Like: "+feelsLike.toString()
            +",\nMax Temp: "+maxTemp.toString()+",\nMin Temp: "+minTemp.toString()+"\n");
    }
}
