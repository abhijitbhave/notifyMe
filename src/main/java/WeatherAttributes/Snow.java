package WeatherAttributes;

public class Snow {

    private Double snowVolume;

    public Double getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(Double snowVolume) {
        this.snowVolume = snowVolume;
    }

    public String toString(){
        return ("\nSNOW\nSnow: "+snowVolume.toString())+"\n";
    }

}
