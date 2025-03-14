package roamnroot_package;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Placesfactory { 
    private Scanner sc;
    private File placeFile;
    private String firstRow;
    
    public Placesfactory() {
        sc = new Scanner(System.in);
    }

    // Constructor for reading the file
    public Placesfactory(String source) {
        try {
            placeFile = new File(source);
            sc = new Scanner(placeFile);
            
            if (sc.hasNextLine()) {
                firstRow = sc.nextLine(); // Read the first row (headers)
                System.out.println("First row: " + firstRow);
            }
            
            // Skip the second line (if available)
            if (sc.hasNextLine()) {
                sc.nextLine(); // Skip the second line
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Enter info from keyboard.");
            e.printStackTrace();
            sc = new Scanner(System.in); // Fallback to user input if file is not found
        }
    }

    // Check if more data is available
    public boolean moreData() {
        return sc.hasNextLine();
    }

    // Get the next place from the file (starting from the 3rd line)
    public ArrayList<String> getNextPlace() {
        ArrayList<String> placeDetails = new ArrayList<>();

        if (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] arrOfStr = data.split(",", -1); 

            for (String value : arrOfStr) {
                placeDetails.add(value.trim()); // Add to list after trimming spaces
            }
        }

        return placeDetails;
    }

    // Get headers (first row)
    public String getHeaders() {
        return firstRow;
    }
}


