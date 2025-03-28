package cs335_package;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlacesFactory {
    private Scanner sc;
    private File placeFile;
    private String firstRow;
   
    public PlacesFactory() {
        sc = new Scanner(System.in);
    }

    public PlacesFactory(String source) {
        try {
            placeFile = new File(source);
            sc = new Scanner(placeFile);
            if (sc.hasNextLine()) {
               firstRow = sc.nextLine(); // Read the first row (headers)
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Enter info from keyboard.");
            e.printStackTrace();
            sc = new Scanner(System.in); // Fallback to user input
        }
    }

    public boolean moreData() {
        return sc.hasNextLine();
    }

    // Get the next place from the file
    public Location getNextPlace() {
        if (!sc.hasNextLine()) {
            return null;
        }
        
        String line = sc.nextLine();
        String[] parts = line.split(",");
        
        if (parts.length < 2) {
            System.out.println("Warning: Invalid line format in CSV: " + line);
            return getNextPlace(); // Skip this line and try the next one
        }
        
        String name = parts[0].trim();
        String type = parts[1].trim();
        
        Location location = new Location(name, type);
        
        // Set other properties if they exist in the CSV
        if (parts.length > 2 && !parts[2].isEmpty()) {
            location.setLocation(parts[2].trim());
        }
        
        if (parts.length > 3 && !parts[3].isEmpty()) {
            location.setReservation(parts[3].trim());
        }
        
        if (parts.length > 4 && !parts[4].isEmpty()) {
            location.setCoatCheck(parts[4].trim());
        }
        
        if (parts.length > 5 && !parts[5].isEmpty()) {
            location.setCover(parts[5].trim());
        }
        
        // Update the price handling
if (parts.length > 6 && !parts[6].isEmpty()) {
    location.setPrice(parts[6].trim());
}

// Update the stars handling
if (parts.length > 7 && !parts[7].isEmpty()) {
    location.setStars(parts[7].trim());
}
        
        if (parts.length > 8 && !parts[8].isEmpty()) {
            location.setWeb(parts[8].trim());
        }
        
        return location;
    }
    
    // Add a method to close the scanner when done
    public void close() {
        if (sc != null) {
            sc.close();
        }
    }
}
