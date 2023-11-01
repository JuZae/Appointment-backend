package de.vkb.APoint.Controller;


import de.vkb.APoint.Service.FetchFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class AutocompleteController {

    private final  String API_KEY = "AIzaSyDUjRhIuQEwnHRnOMo5D6IZ0t9KkkrrzGQ";
    private final RestTemplate restTemplate;

    @Autowired
    FetchFormatService fetchFormatService;

    public AutocompleteController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/autocomplete/{input}")
    public String autocomplete(@PathVariable String input) {

        String apiUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + input +
                "&types=(cities)&key=" + API_KEY;

        String response = restTemplate.getForObject(apiUrl, String.class);

        System.out.println(response);

        return response;
    }

    @GetMapping("/places")
    public List<String> fetchPlaces(@RequestParam("location") String location) {
        String apiUrl =
                "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                        + location
                        + "&types=(cities)&key="
                        + API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        // Make the HTTP request and get the JSON response as a Map
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

       return fetchFormatService.formatDescriptionAPICall(response);
    }

    @GetMapping("/address")
    public List<String> fetchAddress(@RequestParam("location") String location) {
        String apiUrl =
                "https://maps.googleapis.com/maps/api/place/textsearch/json?query="
                        + location
                        + "&key="
                        + API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        // Make the HTTP request and get the JSON response as a Map
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        return fetchFormatService.formatAddressAPICall(response);
    }
}