package WeatherAttributes;

import org.json.JSONObject;

//The all inclusive class for various weather components.
public class Weather<T> {

    private String primaryWeather;
    private Humidity humidity;
    private Rain rain;
    private Sun sun;
    private Temperature temperature;
    private Wind wind;
    private Snow snow;

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public String getPrimaryWeather() {
        return primaryWeather;
    }

    private void setPrimaryWeather(String primaryWeather) {
        this.primaryWeather = primaryWeather;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    private void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public Rain getRain() {
        return rain;
    }

    private void setRain(Rain rain) {
        this.rain = rain;
    }

    public Sun getSun() {
        return sun;
    }

    private void setSun(Sun sun) {
        this.sun = sun;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    private void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    private void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setWeatherAttribute(T weatherAttribute) {
        if (weatherAttribute instanceof String) {
            setPrimaryWeather(weatherAttribute.toString());
        } else if (weatherAttribute instanceof Humidity) {
            setHumidity((Humidity) weatherAttribute);
        } else if (weatherAttribute instanceof Temperature) {
            setTemperature((Temperature) weatherAttribute);
        } else if (weatherAttribute instanceof Wind) {
            setWind((Wind) weatherAttribute);
        } else if (weatherAttribute instanceof Rain) {
            setRain((Rain) weatherAttribute);
        } else if (weatherAttribute instanceof Sun) {
            setSun((Sun) weatherAttribute);
        }
        else if(weatherAttribute instanceof Snow){
            setSnow((Snow)weatherAttribute);
        }
    }

    public String toString() {
        StringBuilder weatherString = new StringBuilder();
        weatherString.append("\nWEATHER:\nOverAll Weather: ");
        String builder = null;
        builder = (primaryWeather == null)?"None":primaryWeather;
        weatherString.append(primaryWeather + "\n");
        builder = (temperature == null)?"Temp: None\n":temperature.toString();
        weatherString.append(builder);
        builder = (humidity == null)?"Humidity: None\n":humidity.toString();
        weatherString.append(builder);
        builder = (wind == null)?"Wind : None\n":wind.toString();
        weatherString.append(builder);
        builder = (sun == null)?"Sun : None\n":sun.toString();
        weatherString.append(builder);
        builder = (rain == null)?"\nRAIN: None\n":rain.toString();
        weatherString.append(builder);
        builder = (snow == null)?"\nSNOW: None":snow.toString();
        weatherString.append(builder);
        return(weatherString.toString());


//        return ("" + primaryWeather + temperature.toString() == null ? "NA"
//            : temperature.toString() + wind
//                .toString() == null ? "NA" : wind.toString() + sun.toString() == null ? "NA"
//                : sun.toString() + humidity.toString() == null ? "NA"
//                    : humidity.toString()
//                        + rain.toString() == null ? "NA" : rain.toString());
    }

    public void getWeather() {
        System.out.println("Reaching out to get weather data");
    }


}
