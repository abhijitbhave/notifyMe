package WeatherAttributes;

//Class to model Rain conditions.
public class Rain {

    private Double rainInInches;
    private Double precipitationChance;

    public Double getRainInInches() {
        return rainInInches;
    }

    public void setRainInInches(Double rainInInches) {
        this.rainInInches = rainInInches;
    }

    public Double getPrecipitationChance() {
        return precipitationChance;
    }

    public void setPrecipitationChance(Double precipitationChance) {
        this.precipitationChance = precipitationChance;
    }

    public String toString() {
        return ("\nRAIN:\nRain in Inches: " + rainInInches.toString()+",\nPrecipitation Chance: "+precipitationChance.toString()+"\n");
    }
}
