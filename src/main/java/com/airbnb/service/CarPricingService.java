package com.airbnb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CarPricingService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public CarPricingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to calculate price based on origin and destination
    public double calculatePrice(String origin, String destination) {
        // Get route data from Google Maps Directions API
        String routeData = getRouteFromGoogleMaps(origin, destination);

        // Parse the response to extract the distance
        double distance = parseDistance(routeData);

        // Calculate price based on distance (example: $2 per kilometer)
        return distance * 50; // Price per kilometer
    }


    // Method to call Google Maps Directions API and get the response
    private String getRouteFromGoogleMaps(String origin, String destination) {
        // Build the URL to call Google Maps Directions API
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/directions/json")
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .queryParam("key", apiKey)
                .toUriString();

        // Call Google Maps Directions API and return the response as a string
        return restTemplate.getForObject(url, String.class);
    }


    // Method to parse the response and extract the distance
    private double parseDistance(String response) {
        try {
            // Use Jackson ObjectMapper to parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode legsNode = rootNode.path("routes").get(0).path("legs").get(0);
            JsonNode distanceNode = legsNode.path("distance").path("value");

            // Convert distance from meters to kilometers
            return distanceNode.asDouble() / 1000;  // Return distance in kilometers
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;  // Return 0 if there is an error
        }
    }
}

