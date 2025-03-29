package cs335_package;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class SearchYelp {

    private static final String API_KEY = "i9p0sLerDK1nOzWkMSEo9IwyZ3UoeU3jzKBVRna6sAJaJDB4FrKdwfLbLvzxGx7SsEaMAfHf2w4cjVtUADNhEHiw9k2vKyMDk5Jg7oTgQu_58ZOd-4Cj-vnBdZvlZ3Yx"; // Replace with your actual Yelp API key
    private static final String BASE_URL = "https://api.yelp.com/v3/businesses/search";

    public static void initiateSearch() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter search term (e.g., 'restaurants'): ");
        String term = scanner.nextLine();

        System.out.print("Enter latitude: ");
        double latitude = scanner.nextDouble();

        System.out.print("Enter longitude: ");
        double longitude = scanner.nextDouble();

        try {
            fetchAndDisplayBusinesses(term, latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public static void fetchAndDisplayBusinesses(String term, double latitude, double longitude) throws Exception {
        String url = String.format("%s?term=%s&latitude=%f&longitude=%f", BASE_URL, term, latitude, longitude);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Bearer " + API_KEY);
            request.addHeader("Accept", "application/json");

            try (CloseableHttpResponse response = client.execute(request)) {
                int statusCode = response.getCode();

                if (statusCode != 200) {
                    System.out.println("Error: Failed to connect to Yelp API. Response code: " + statusCode);
                    return;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Debug print to see the raw response
                System.out.println("Raw response from Yelp API: " + result.toString());

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(result.toString());

                if (rootNode.has("businesses")) {
                    JsonNode businesses = rootNode.get("businesses");

                    for (JsonNode business : businesses) {
                        String name = business.get("name").asText();
                        JsonNode locationNode = business.get("location");
                        String address = locationNode != null && locationNode.has("address1") ? locationNode.get("address1").asText() : "Address not available";
                        String phone = business.has("phone") ? business.get("phone").asText() : "Phone not available";
                        Double rating = business.has("rating") ? business.get("rating").asDouble() : 0.0;
                        String price = business.has("price") ? business.get("price").asText() : "Price not available";
                        String businessUrl = business.has("url") ? business.get("url").asText() : "URL not available";

                        System.out.println("\n✅ Business Found:");
                        System.out.println("Name: " + name);
                        System.out.println("Location: " + address);
                        System.out.println("Phone: " + phone);
                        System.out.println("Price: " + price);
                        System.out.println("Stars: " + rating);
                        System.out.println("URL: " + businessUrl);
                        System.out.println("-----------------------------");
                    }
                } else {
                    System.out.println("No businesses found.");
                }
            }
        }
    }


    public static void main(String[] args) {
        initiateSearch();
    }
}