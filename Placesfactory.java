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


    public Placesfactory(String source) {
        try {
            placeFile = new File(source);
            sc = new Scanner(placeFile);
            if (sc.hasNextLine()) {
               firstRow = sc.nextLine(); // Read the first row
                System.out.println("First row: " + firstRow);
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
    public String getHeaders() {
        return(firstRow);
    }
}
