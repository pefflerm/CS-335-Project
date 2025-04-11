package cs335_package;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LocationFactory {
	private static Scanner sc;
    private FileWriter writer;
    private String csvFile;
    public LocationFactory() {
    	 sc = new Scanner(System.in);
    	 System.out.println();
    }
    public LocationFactory(String fileName) {
        sc = new Scanner(System.in);
        this.csvFile = fileName;
        try {
            File file = new File(csvFile);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // ensures "places/" exists
            }

            if (!file.exists()) {
                file.createNewFile();
                writer = new FileWriter(file, true);
                writer.append("Name,Type,Location,Reservation,Coat Check,Cover,Price,Stars,Link,Reviews,Business Hours\n");
            } else {
                writer = new FileWriter(file, true);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }

    }

    public void addNewLocation() {
        System.out.println("Enter the details for the new location:");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Type: ");
        String type = sc.nextLine();

        System.out.print("Enter Location: ");
        String location = sc.nextLine();

        System.out.print("Reservation (Yes/No): ");
        String reservation = sc.nextLine();

        System.out.print("Coat Check (Yes/No): ");
        String coatCheck = sc.nextLine();

        System.out.print("Cover (Yes/No): ");
        String cover = sc.nextLine();

        System.out.print("Price (e.g. $, $$, $$$): ");
        String price = sc.nextLine();

        System.out.print("Stars (e.g. 4.5): ");
        String stars = sc.nextLine();

        System.out.print("Link (Yelp/website): ");
        String link = sc.nextLine();

        System.out.print("Reviews: ");
        String reviews = sc.nextLine();

        System.out.print("Business Hours: ");
        String businessHours = sc.nextLine();

        try {
            writer.append(String.join(",", name, type, location, reservation, coatCheck, cover, price, stars, link, reviews, businessHours));
            writer.append("\n");
            writer.flush();
            System.out.println("✅ Location added successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error while saving the location.");
            e.printStackTrace();
        }
    }

}
