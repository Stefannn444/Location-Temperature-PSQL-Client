package org.example;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class ClientUtils {
    public static String createLoginRequest(String username) {
        return new Gson().toJson(new Request(username, "LOGIN", null));
    }

    public static String createAddLocationRequest(String username, String name, double latitude, double longitude, double day1, double day2, double day3) {
        Map<String, Object> weather = new HashMap<>();
        weather.put("day1", day1);
        weather.put("day2", day2);
        weather.put("day3", day3);

        Map<String, Object> data = new HashMap<>();
        data.put("name",name);
        data.put("latitude", latitude);
        data.put("longitude", longitude);
        data.put("weather", weather);

        Request request = new Request(username, "ADD_LOCATION", data);
        return new Gson().toJson(request);
    }

    public static String createGetWeatherRequest(String username) {
        return new Gson().toJson(new Request(username, "GET_WEATHER", null));
    }

    public static String createChangeLocationRequest(String username, double latitude, double longitude) {
        Map<String, Object> data = new HashMap<>();
        data.put("latitude", latitude);
        data.put("longitude", longitude);

        return new Gson().toJson(new Request(username, "CHANGE_LOCATION", data));
    }
}