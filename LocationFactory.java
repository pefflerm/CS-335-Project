package roamnroot_package;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class LocationFactory {
    private Scanner sc;
    private FileWriter writer;
    private String csvFile;

    public LocationFactory(String fileName) {
        sc = new Scanner(System.in);
        this.csvFile = fileName;
        try {
            File file = new File(csvFile);
            // Create a new file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
                writer = new FileWriter(file, true);
                // Write headers to the file if it's a new file
                writer.append("Bars,Genre,Location,Coat Check,Cover,Has Kelsey been?,TikTok/ IG Reels,Links\n");
            } else {
                writer = new FileWriter(file, true); // Append to existing file
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    public void addNewLocation() {
        System.out.println("Enter the details for the new location:");

        // Get user input for each field
        System.out.print("Enter Place Name: ");
        String barName = sc.nextLine();

        System.out.print("Enter Genre: ");
        String genre = sc.nextLine();

        System.out.print("Enter Location: ");
        String location = sc.nextLine();

        System.out.print("Enter Coat Check (Yes/No): ");
        String coatCheck = sc.nextLine();

        System.out.print("Enter Cover Charge (Yes/No): ");
        String cover = sc.nextLine();

        System.out.print("Has Kelsey been? (TRUE/FALSE): ");
        String hasKelseyBeen = sc.nextLine();

        System.out.print("Enter TikTok/IG Reels link (if any): ");
        String tikTokReels = sc.nextLine();

        System.out.print("Enter Links: ");
        String links = sc.nextLine();

        // Add the new location to the CSV file
        try {
            writer.append(barName + "," + genre + "," + location + "," + coatCheck + "," + cover + ","
                    + hasKelseyBeen + "," + tikTokReels + "," + links + "\n");
            writer.flush();
            System.out.println("Location added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the location.");
            e.printStackTrace();
        }
    }
}
