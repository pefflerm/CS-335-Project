package cs335_package;
import cs335_package.Main; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchYelp {

    private static final String API_KEY = "i9p0sLerDK1nOzWkMSEo9IwyZ3UoeU3jzKBVRna6sAJaJDB4FrKdwfLbLvzxGx7SsEaMAfHf2w4cjVtUADNhEHiw9k2vKyMDk5Jg7oTgQu_58ZOd-4Cj-vnBdZvlZ3Yx"; // Replace with your actual Yelp API key
    private static final String BASE_URL = "https://api.yelp.com/v3/businesses/search";
    private static final Map<Integer, String> DAY_MAPPING = new HashMap<>();

 // Initialize the map with day names
 static {
     DAY_MAPPING.put(0, "Sunday");
     DAY_MAPPING.put(1, "Monday");
     DAY_MAPPING.put(2, "Tuesday");
     DAY_MAPPING.put(3, "Wednesday");
     DAY_MAPPING.put(4, "Thursday");
     DAY_MAPPING.put(5, "Friday");
     DAY_MAPPING.put(6, "Saturday");
 }
 public static List<String> initiateSearch(String term) {  
	    // Boston latitude and longitude
	    double latitude = 42.3601;
	    double longitude = -71.0589;
	    

	    try {
	        // Encode the term parameter
	        String encodedTerm = URLEncoder.encode(term, StandardCharsets.UTF_8.toString());

	        // Generate the URL with proper formatting
	        String url = String.format("%s?term=%s&latitude=%.6f&longitude=%.6f", 
	                                  BASE_URL, encodedTerm, latitude, longitude);

	        System.out.println("Generated URL: " + url);

	        // Perform the HTTP request
	        
	       List<String> ArrayReturned=fetchAndDisplayBusinesses(url);
	       System.out.println("Called saveToCsv function: ");
	       String path="";
	       String cleanedTerm = term.trim(); 
	       // Determine the path based on the cleanedTerm (formerly x)
	       if (cleanedTerm.equals("Night Out") || cleanedTerm.equals("Brunch") || cleanedTerm.equals("Cafes/Bakeries") || cleanedTerm.equals("Lunch/Dinner")) {
	           path = "places/restaurants/" + Main.getCsvFile(cleanedTerm);
	       } else if (cleanedTerm.equals("Shopping") || cleanedTerm.equals("Fun Activities") || cleanedTerm.equals("Outdoors")) {
	         path = "places/toDo/" + Main.getCsvFile(cleanedTerm);
	       } else if (cleanedTerm.equals("Spa") || cleanedTerm.equals("Massage") || cleanedTerm.equals("Skincare/Haircare"))  {
	           path = "places/selfCare/" + Main.getCsvFile(cleanedTerm);
	       } else  {
	    	   Main.createGeneralUserChoiceCSV(cleanedTerm);
	    	   path = "places/" + Main.getCsvFile(cleanedTerm);}
	   
	       
	       System.out.println(path);
	       System.out.println(term);
	        if (path == null || path.isEmpty()) {
	            System.out.println("Error: CSV file name is null or empty.");
	            return new ArrayList<>(); // Handle missing CSV file gracefully
	        }

	        System.out.println("CSV file to save to: " + path);
	        saveToCsv(ArrayReturned, path);

	       
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();  // Return an empty array in case of an exception
	    }
		return null;
	}





	





	public static List<String> fetchAndDisplayBusinesses(String url) throws Exception {
        final String[] DAY_NAMES = {
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        };

        List<String> businessList = new ArrayList<>();
        
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Bearer " + API_KEY);
            request.addHeader("Accept", "application/json");

            try (CloseableHttpResponse response = client.execute(request)) {
                int statusCode = response.getCode();

                if (statusCode != 200) {
                    System.out.println("Error: Failed to connect to Yelp API. Response code: " + statusCode);
                    return new ArrayList<>();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder result = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(result.toString());

                if (rootNode.has("businesses")) {
                    JsonNode businesses = rootNode.get("businesses");

                    for (JsonNode business : businesses) {
                        String name = business.get("name").asText();
                        JsonNode locationNode = business.get("location");
                        String address = locationNode != null && locationNode.has("address1") ? locationNode.get("address1").asText() : "";
                        String phone = business.has("phone") ? business.get("phone").asText() : "";
                        Double rating = business.has("rating") ? business.get("rating").asDouble() : 0.0;
                        String price = business.has("price") ? business.get("price").asText() : "";
                        String businessUrl = business.has("url") ? business.get("url").asText() : "";
                        String type = "";  // Type is not fetched, so empty
                        String reservation = "";  // Reservation is not fetched, so empty
                        String coatCheck = "";  // Coat Check is not fetched, so empty
                        String cover = "";  // Cover is not fetched, so empty
                        String review = "";  // Review is not fetched, so empty
                        String numOfReviews = "0";
                        JsonNode hoursNode = business.path("business_hours");
                        List<String> hoursList = new ArrayList<>();

                        if (hoursNode.isArray()) {
                            for (JsonNode hoursEntry : hoursNode) {
                                JsonNode openHours = hoursEntry.path("open");

                                if (openHours.isArray()) {
                                    for (JsonNode openHour : openHours) {
                                        int dayNumber = openHour.path("day").asInt(-1); // Default to -1 if missing
                                        String dayName = DAY_NAMES[dayNumber] != null ? DAY_NAMES[dayNumber] : "Unknown Day";
                                        String startTime = formatTime(openHour.path("start").asText());
                                        String endTime = formatTime(openHour.path("end").asText());

                                        hoursList.add(dayName + ": " + startTime + " - " + endTime);
                                    }
                                }
                            }
                        }
                        

                        // Change the delimiter for business hours to semicolon (;)
                        String operatingHours = String.join("; ", hoursList);
                        // Print each business details
                        System.out.println("\n✅ Business Found:");
                        System.out.println("Name: " + name);
                        System.out.println("Location: " + address);
                        System.out.println("Phone: " + phone);
                        System.out.println("Price: " + price);
                        System.out.println("Stars: " + rating);
                        System.out.println("URL: " + businessUrl);
                        System.out.println("Business Hours:\n" + operatingHours);
                        System.out.println("-----------------------------");


                        // Prepare the string with comma-separated attributes
                        String businessDetails = String.join(",", 
                            name, 
                            type, 
                            address, 
                            reservation, 
                            coatCheck, 
                            cover, 
                            price, 
                            String.valueOf(rating), 
                            businessUrl, 
                            review, 
                            operatingHours,
                            numOfReviews
                        );
                        
                        businessList.add(businessDetails);
                    }
                } else {
                    System.out.println("No businesses found.");
                }

            }
        }

        // Convert List to String array and return
        
        
        return businessList;
    }
    public static void saveToCsv(List<String> businesses, String csvFile) throws IOException {
        Path csvPath = Paths.get(csvFile);
        System.out.println("Businesses to save: " + businesses.size());
        if (businesses.isEmpty()) {
            System.out.println("No businesses found to save.");
        }
        // Create the CSV with headers if it doesn't exist
        if (!Files.exists(csvPath)) {
            try (BufferedWriter writer = Files.newBufferedWriter(csvPath)) {
                writer.write("Name,Type,Location,Reservation,Coat Check,Cover,Price,Stars,URL,Review,Business Hours");
                writer.newLine();
            }
        }

        // Load existing entries to avoid duplicates
        Set<String> existingEntries = new HashSet<>(Files.readAllLines(csvPath));

        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardOpenOption.APPEND)) {
            for (String business : businesses) {
                if (!existingEntries.contains(business)) {  // ✅ Avoid duplicates
                    writer.write(business);
                    writer.newLine();
                }
            }
        }

        System.out.println("\n✅ Yelp results saved to: " + csvFile);
    }

    /**
     * Converts 24-hour format time (e.g., "1600") to 12-hour format with AM/PM
     */
    public static String formatTime(String time) {
        if (time.length() != 4) {
            return "N/A";
        }

        try {
            int hours = Integer.parseInt(time.substring(0, 2));
            int minutes = Integer.parseInt(time.substring(2, 4));

            String amPm = (hours >= 12) ? "PM" : "AM";
            hours = (hours % 12 == 0) ? 12 : hours % 12;

            return String.format("%02d:%02d %s", hours, minutes, amPm);

        } catch (NumberFormatException e) {
            return "N/A";
        }
    }
   
}