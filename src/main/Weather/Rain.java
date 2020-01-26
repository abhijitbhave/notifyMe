package main.Weather;

//Class to model Rain conditions.
public class Rain {

    private Integer rainInInches;
    private Integer precipitationChance;

    public Integer getRainInInches() {
        return rainInInches;
    }

    public void setRainInInches(Integer rainInInches) {
        this.rainInInches = rainInInches;
    }

    public Integer getPrecipitationChance() {
        return precipitationChance;
    }

    public void setPrecipitationChance(Integer precipitationChance) {
        this.precipitationChance = precipitationChance;
    }
}
