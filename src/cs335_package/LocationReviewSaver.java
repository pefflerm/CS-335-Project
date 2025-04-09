package cs335_package;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LocationReviewSaver {
    
    // Example lists for locations, initialized as empty
    static List<Location> nightOut = new ArrayList<>();
    static List<Location> brunch = new ArrayList<>();
    static List<Location> cafe = new ArrayList<>();
    static List<Location> lunchDinner = new ArrayList<>();
    static List<Location> shopping = new ArrayList<>();
    static List<Location> fun = new ArrayList<>();
    static List<Location> outdoors = new ArrayList<>();
    static List<Location> selfCare = new ArrayList<>();
    
    // Location class
    public static class Location {
        String name;
        String review;
        
        public Location(String name, String review) {
            this.name = name;
            this.review = review;
        }

        public String getName() {
            return name;
        }

        public String getReview() {
            return review;
        }
    }
    
    // Method to return the correct CSV filename based on the location category
    private static String getCsvFileForLocation(cs335_package.Location place) {
        if (nightOut.contains(place)) {
            return "Copy of Boston Recommendations - Going out Bars.csv";
        } else if (brunch.contains(place)) {
            return "Boston Recommendations - Brunch.csv";
        } else if (cafe.contains(place)) {
            return "Copy of Boston Recommendations - Coffee Shops _ Bakery.csv";
        } else if (lunchDinner.contains(place)) {
            return "Copy of Boston Recommendations - Restaurants.csv";
        } else if (shopping.contains(place)) {
            return "Copy of Boston Recommendations - Shopping.csv";
        } else if (fun.contains(place)) {
            return "Copy of Boston Recommendations - Things To Do.csv";
        } else if (outdoors.contains(place)) {
            return "Copy of Boston Recommendations - Summer _ Outdoor.csv";
        } else if (selfCare.contains(place)) {
            return "Copy of Boston Recommendations - Spa_Beauty.csv";
        } else {
            return "";  // Default case if no match found
        }
    }

    // Method to save the review to the corresponding CSV file
    public static void saveReview(cs335_package.Location place) {
        String fileName = getCsvFileForLocation(place);
        
        if (fileName.isEmpty()) {
            System.out.println("No matching category for this location.");
            return;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(place.getName() + ", " + place.getReview());
            System.out.println("Review saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving the review: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // Example usage:
        Location place = new Location("Cafe XYZ", "Great coffee and cozy ambiance!");
        
        // Add place to one of the lists (e.g., cafe)
        cafe.add(place);
        
        // Save the review for the location
        saveReview(place); // This will save the review to the correct CSV
    }

	private static void saveReview(Location place) {
		// TODO Auto-generated method stub
		
	}
}
