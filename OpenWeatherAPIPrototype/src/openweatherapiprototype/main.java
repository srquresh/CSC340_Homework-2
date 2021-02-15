package openweatherapiprototype;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;
/**
 * The purpose of this class is to create a connection to Open Weather API and test it.
 * In this example we will find the current weather of Greensboro, North Carolina.
 * This will also include other information such as latitude, longitude, country, etc.
 * last updated: 02/15/2021
 * @author Sarah Qureshi
 */
public class main {

    public static void main(String[] args) {
        //Create an HTTP connection.
        String weatherUrl = "http://api.openweathermap.org/data/2.5/weather";
        String location = "?q=Greensboro,nc,us";
        String apiKey = "a22dd61ea3991b220eeb22c1d5991672";
        String units = "&units=imperial";
        String language = "&lang=en";
        //Combine variables into a single connection for use.
        String urlString = weatherUrl + location + "&appid=" + apiKey + units + language ;
        URL url;
        try{
            //Make the connection.
            url = new URL(urlString);
            HttpURLConnection weatherConnection = (HttpURLConnection) url.openConnection();
            weatherConnection.setRequestMethod("GET");
            //Examine the response.
            int status = weatherConnection.getResponseCode();
            if(status != 200){
                System.out.printf("Error: Could not load current weather report: " + status);
            }
            else{
               //Parsing input stream into a text string.
                BufferedReader input = new BufferedReader(new InputStreamReader(weatherConnection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while((inputLine = input.readLine()) != null) {
                    content.append(inputLine);
                }
                //Close the connection.
                input.close();
                weatherConnection.disconnect();
                //Print out the complete JSON string of current weather.
                System.out.println("Output:  " + content.toString());
                JSONObject obj = new JSONObject(content.toString());
                String currentWeather = obj.getString("weather");
                System.out.println("Current Weather: " + currentWeather);
                //Print out the complete JSON string of temperature.
                JSONObject obj2 = new JSONObject(content.toString());
                String temperature = obj2.getString("main");
                System.out.println("Other statistics: " + temperature);
            }

        }
        catch(Exception ex){
            System.out.println("Error: " + ex);
            return;
        }
    }
    
}
