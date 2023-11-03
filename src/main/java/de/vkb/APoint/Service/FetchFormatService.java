package de.vkb.APoint.Service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FetchFormatService {
    public List<String> formatAddressAPICall( Map<String, Object> response) {
        // Extract the predictions list from the JSON response
        List<Map<String, Object>> predictions = (List<Map<String, Object>>) response.get("results");

        // Extract the city names from the predictions list and return as a list of strings
        List<String> cityNames = new ArrayList<>();
        if (predictions != null) {
            for (Map<String, Object> prediction : predictions) {
                String cityName = (String) prediction.get("formatted_address");
                cityNames.add(cityName);
            }
        }
        return cityNames;
    }
}
