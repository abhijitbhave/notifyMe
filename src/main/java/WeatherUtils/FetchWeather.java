package WeatherUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;


public class FetchWeather {

    //http://api.openweathermap.org/data/2.5/weather?id=5391997&units=Imperial&appid=4deb275bbcfca2aca4d60da22b9863f5


    private String units = "Imperial";
    String openWeatherAPIKey = "4deb275bbcfca2aca4d60da22b9863f5";
    Integer cityId = 5391997; //City ID for San Francisco
    private String baseURL = "http://api.openweathermap.org/data/2.5/weather?id="+cityId+"&appid="+openWeatherAPIKey+"&units="+units;

    StringBuilder resultString = new StringBuilder();
    URL weatherUrl;

    {
        try {
            weatherUrl = new URL(baseURL);
            URLConnection connection = weatherUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                resultString.append(line);
            }
            reader.close();
            System.out.println(resultString);
            Map<String, Object> respMap = jsonToMap(resultString.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
            System.out.println(mainMap.keySet());
            System.out.println(mainMap.values());
            System.out.println(mainMap.keySet());
            System.out.println(windMap.keySet());

            JSONObject jsonObject = new JSONObject(resultString.toString());
            System.out.println(jsonObject.toString());
            JSONArray array = jsonObject.getJSONArray("weather");
            System.out.println("Am here");
            System.out.println(array.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> jsonToMap(String string){
        Map<String, Object> map = new Gson().fromJson(string, new TypeToken<HashMap<String, Object>>(){}.getType());
        return map;
    }

}


