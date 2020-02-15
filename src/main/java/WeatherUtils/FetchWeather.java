package WeatherUtils;


import Utils.NotifyMeException;
import WeatherAttributes.Humidity;
import WeatherAttributes.Rain;
import WeatherAttributes.Snow;
import WeatherAttributes.Sun;
import WeatherAttributes.Temperature;
import WeatherAttributes.Weather;
import WeatherAttributes.Wind;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//The weather class that will be lveraged to fetch weather details from the third party openWeahter api.

public class FetchWeather {

    //The fetch weather method that connects to the OpenWeather API and downloads the required weather forecast information as a JSON file.
    //This JSON file will be passed onwards for further processing.

    private JSONObject fetchWeather() {

        //Setting the Units to Imperial since thats the system used in USA.

        String units = "Imperial";
        //Setting a API Key. Can be acquired free by registering at OpenWeather.
        String openWeatherAPIKey = "4deb275bbcfca2aca4d60da22b9863f5";
        //A City ID is used in the API. Default set to SF, however used Munich to test other weather confitions.
        //Integer cityId = 2867714; //City of Munich, De
        Integer cityId = 5391997; //City ID for San Francisco
        //Creating a base URL to call the API and retrieve information.
        String baseURL =
            "http://api.openweathermap.org/data/2.5/forecast/?id=" + cityId + "&appid=" + openWeatherAPIKey + "&units=" + units;

        //S string builder Object to build the response recived from the API.
        StringBuilder resultString = new StringBuilder();
        URL weatherUrl;

        {
            try {
                weatherUrl = new URL(baseURL);
                //Calling the actual API.
                URLConnection connection = weatherUrl.openConnection();
                //Reading the response from the OpenWeather API and using String builder to build the response.
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        resultString.append(line);
                    }
                    reader.close();
                    //Creating a JSON Object leveraging the string build string.
                    JSONObject weatherDump = new JSONObject(resultString.toString());
                    return weatherDump;
                } catch (UnknownHostException e) {
                    try {
                        throw new NotifyMeException(
                            "\n***** Destination host not reachable. Probablly no internet connection. Please try again later with a stable internet connection. *****\n");
                    } catch (NotifyMeException ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //A method to retrive data from the JSON object created by fetching the response and setting various attributes in the Weather object.
    public Weather setWeatherAttributes() {

        //Creating an Object of type weather
        Weather weather = new Weather();

        //Getting the JSON Object leveraging the fetchWeather method.
        JSONObject weatherDump = fetchWeather();
        //Retriving the City block from the JSON to get the sunrise and sunset times.
        JSONObject city = (JSONObject) weatherDump.get("city");
        Date sunrise = new Date((Integer) (city.get("sunrise")) * 1000L);
        Date sunSet = new Date((Integer) (city.get("sunset")) * 1000L);
        Sun sun = new Sun();
        sun.setSunRise(sunrise);
        sun.setSunSet(sunSet);
        //Calling on the generic weather.setAttribute method to set weather's Sun attribute.
        weather.setWeatherAttribute(sun);

        //Retiving the List array block from the JSON to process and retieve other attributes.
        JSONArray weatherForecasts = weatherDump.getJSONArray("list");
        //However off the list of all the forecasts, the one that important is the one for tomororw at 9:00 AM
        // since thats the time we reach office. Hence calculating the date for tomorrow.
        Integer arrayIndex = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date today = new Date();
        cal.setTime(today);
        cal.add(Calendar.DATE, 1);
        Date tomorrow = cal.getTime();

        //Finding the right array in the JSONObject which contains the forecast for tomorrow 9 AM.
        for (Integer i = 0; i < weatherForecasts.length(); i++) {
            if (weatherForecasts.getJSONObject(i).getString("dt_txt").contains(sdf.format(tomorrow) + " 09:00:00")) {
                arrayIndex = i;
            }
        }
        JSONObject weatherForecastForNineAm = (weatherForecasts.getJSONObject(arrayIndex));
        if (weatherForecastForNineAm == null) {
            try {
                throw new NotifyMeException("Could not read data from OpenWeatherAPI");
            } catch (NotifyMeException e) {
                e.printStackTrace();
            }
        }

        JSONArray weatherArray = weatherForecastForNineAm.getJSONArray("weather");

        //Now retriving the various weather attributes from the 9 AM Tomorrow Array and using weather's generic set method to enrich the object.
        weather.setWeatherAttribute(weatherArray.getJSONObject(0).getString("description"));
        JSONObject main = weatherForecastForNineAm.getJSONObject("main");

        Humidity humidity = new Humidity();
        humidity.setHumidity(main.getDouble("humidity"));
        weather.setWeatherAttribute(humidity);

        Temperature temp = new Temperature();
        temp.setTemperature(main.getDouble("temp"));
        temp.setFeelsLike(main.getDouble("feels_like"));
        temp.setMaxTemp(main.getDouble("temp_max"));
        temp.setMinTemp(main.getDouble("temp_min"));
        weather.setWeatherAttribute(temp);

        JSONObject winds = weatherForecastForNineAm.getJSONObject("wind");
        Wind wind = new Wind();
        wind.setWindSpeed(winds.getDouble("speed"));
        wind.setDegrees(winds.getDouble("deg"));
        weather.setWeatherAttribute(wind);

        //Setting up try catch blocks for Snow and Rain since Snow and Rain may or may not exist.

        try {
            JSONObject snows = weatherForecastForNineAm.getJSONObject("snow");
            Snow snow = new Snow();
            snow.setSnowVolume(snows.getDouble("3h"));
            weather.setWeatherAttribute(snow);
        } catch (JSONException e) {
            Snow snow = null;
            weather.setWeatherAttribute(snow);
        }

        try {
            JSONObject rains = weatherForecastForNineAm.getJSONObject("rain");
            Rain rain = new Rain();
            System.out.println("SNOW: " + rains.getDouble("3h"));
            weather.setWeatherAttribute(rain);
            rain.setRainInInches(rains.getDouble("3h"));
            weather.setWeatherAttribute(rain);
        } catch (JSONException e) {
            Rain rain = null;
            weather.setWeatherAttribute(rain);
        }
        return (weather);

    }


}


