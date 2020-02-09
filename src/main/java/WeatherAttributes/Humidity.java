package WeatherAttributes;

//Class to model Humidity conditions.
public class Humidity {

    private Double humidity;

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String toString() {
        return ("\nHUMIDITY\nHumidity: "+humidity.toString())+"\n";
    }
}
