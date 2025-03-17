package cs335_package;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
               firstRow = sc.nextLine(); // Read the first row
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
    	Location place;
        if (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] arrOfStr = data.split(",", 10); 
            place = new Location(arrOfStr[0], arrOfStr[1]);
        }else {
        	place = new Location("None","None");
        }
		return place;
    }
    public String getHeaders() {
        return(firstRow);
    }
}
