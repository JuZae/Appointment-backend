package de.example.APoint.Controller;


import de.example.APoint.Service.FetchFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
class AutocompleteController {

    @Value("${google.api.key}")
    private String API_KEY;

    @Value("${google.api.maps.textsearch}")
    private String API_TEXTSEARCH;

    @Autowired
    FetchFormatService fetchFormatService;

    public AutocompleteController(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
    }
    @GetMapping("/address")
    public List<String> fetchAddress(@RequestParam("location") String location) {
        String apiUrl = API_TEXTSEARCH
                        + location
                        + "&key="
                        + API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        // Make the HTTP request and get the JSON response as a Map
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        return fetchFormatService.formatAddressAPICall(response);
    }
}