package cs335_package;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Location {
	private String name;
	private String type; // corresponds to type of location (i.e. bakery, bar, spa, etc.)
	private String location;
	private String reserve; // can be empty
	private String coat; // can be empty
	private String cover; // can be empty
	private String price;  // Changed from double
    private String stars;  // Changed from double
	private String web; // can be empty
	private ReviewList reviews; // can be empty
	
	
	private Review review;
	private List<String> schedule; // To store schedule info

	
    public void setPrice(String price) { this.price = price; }
    public void setStars(String stars) { this.stars = stars; }
	


	Location(String n, String t){
		this.name = n;
		this.type = t;
		this.reviews = new ReviewList();  // Add this line to initialize the reviews list
		this.schedule = new ArrayList<>(); // Initialize the schedule
	}
	
	
	
	public boolean addScheduleEvent(String eventDetails, String startTime, String endTime) {
		if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
			System.out.println("Please provide valid start and end times for your reservation.");
			return false;
		}
		
		boolean scheduled = globalSchedule.addEvent(eventDetails, startTime, endTime);
		if (scheduled) {
			String event = String.format("Reservation for %s from %s to %s", eventDetails, startTime, endTime);
			schedule.add(event);  // Add event to the local schedule list
		}
		return scheduled;
	}
    
	public void setLocation(String location) { this.location = location; }
    public void setReservation(String reserve) { this.reserve = reserve; }
    public void setCoatCheck(String coat) { this.coat = coat; }
    public void setCover(String cover) { this.cover = cover; }
  
    public void setWeb(String web) { this.web = web; }

    



    // Update toString method
public String toString() {
    return("Name: " + this.name +
           "\nType: " + this.type +
           "\nLocation: " + this.location +
           "\nReservation: " + this.reserve +
           "\nCoat Check: " + this.coat +
           "\nCover: " + this.cover +
           "\nPrice: " + this.price +  // No need for Double.toString()
           "\nStars: " + this.stars +  // No need for Double.toString()
           "\nWebsite: " + this.web
    );
}

    public String[] toArray() {String[] locArray = {this.name, this.type, this.location, this.reserve, this.coat, this.cover,
        this.price, this.stars, this.web};
return locArray;
}
 

    public String getName() {return this.name;}
    public String getType() {return this.type;}
    public String getLocation() {return this.location;}
    public String getReservationStatus() {return this.reserve;}
    public String getCoatCheckStatus() {return this.coat;}
    public String getCoverStatus() {return this.cover;}
    public String getWeb() {return this.web;}
    public String getPrice() { return this.price; }
    public String getStars() { return this.stars; }
    public ReviewList getReviewList() {return this.reviews;}

    public void createReview(Scanner scan) { 
        Review r = new Review(); // Initialize a new Review object

        // Get star rating from user
        System.out.print("Enter your rating (1-5): ");
        while (!scan.hasNextInt()) {  // Ensure valid integer input
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scan.next(); // Consume invalid input
        }
        int stars = scan.nextInt();
        scan.nextLine(); // Consume the leftover newline
        r.setStars(stars); // Set stars in the Review object

        // Ask user if they want to add text to the review
        boolean cont = true;
        while (cont) {
            System.out.println("Do you want to add text to your review (yes/no)? ");
            String addText = scan.nextLine().trim().toLowerCase();

            if (addText.equals("yes")) {
                System.out.println("Enter your review text: ");
                String reviewText = scan.nextLine();
                r.setText(reviewText);
                cont = false; // Exit loop after getting review text
            } else if (addText.equals("no")) {
                r.setText(""); // No text review provided
                cont = false; // Exit loop
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }

        // Print review details
        System.out.println("Thank you for your review!");
        System.out.println("Your review: " + r.getStars() + " stars");
        System.out.println("Review: " + (r.getText().isEmpty() ? "No text review provided." : r.getText()));

        // Add review to the list
        if (reviews == null) {
            reviews = new ReviewList(); // Initialize ReviewList if it's null
        }
        reviews.addReview(r); // Add the review to the list
    }


	// Add this method to the Location class
// Make it public so it can be called from Main
public int getRating(Scanner scan) {

    int rating = 0;
    boolean validInput = false;
    
    while (!validInput) {
        System.out.print("Enter your rating (1-5 stars): ");
        try {
            rating = Integer.parseInt(scan.nextLine().trim());
            if (rating >= 1 && rating <= 5) {
                validInput = true;
            } else {
                System.out.println("Please enter a number between 1 and 5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    return rating;
}

public void saveReviewToCSV(int rating, String reviewText) throws IOException {
    // Determine which CSV file to update based on the location's type
    String csvFilePath = "";
    String category = this.getType();
    
    // Get the path to the CSV file
    if (category.equals("Night Out")) {
        csvFilePath = "places/restaurants/Copy of Boston Recommendations  - Going out Bars.csv";
    } else if (category.equals("Brunch")) {
        csvFilePath = "places/restaurants/Boston Recommendations  - Brunch.csv";
    } else if (category.equals("Cafes/Bakeries")) {
        csvFilePath = "places/restaurants/Copy of Boston Recommendations  - Coffee Shops _ Bakery.csv";
    } else if (category.equals("Lunch/Dinner")) {
        csvFilePath = "places/restaurants/Copy of Boston Recommendations  - Restaurants.csv";
    } else if (category.equals("Shopping")) {
        csvFilePath = "places/toDo/Copy of Boston Recommendations  - Shopping.csv";
    } else if (category.equals("Fun Activities")) {
        csvFilePath = "places/toDo/Copy of Boston Recommendations  - Things To Do.csv";
    } else if (category.equals("Outdoors")) {
        csvFilePath = "places/toDo/Copy of Boston Recommendations  - Summer _ Outdoor.csv";
    } else {
        csvFilePath = "places/selfCare/Copy of Boston Recommendations  - Spa_Beauty.csv";
    }
    
    // Create a review object and add it to the review list
    Review review = new Review();
    review.setStars(rating);
    review.setText(reviewText);
    
    // Add the review to the location's review list
    if (this.reviews == null) {
        this.reviews = new ReviewList();
    }
    this.reviews.addReview(review);
    
    // Update the average star rating for this location
    double totalStars = 0;
    int reviewCount = this.reviews.getReviewArray().size();
    for (Review r : this.reviews.getReviewArray()) {
        totalStars += r.getStars();
    }
    this.stars = reviewCount > 0 ? totalStars / reviewCount : 0;
    
    // Create a reviews directory if it doesn't exist
    File reviewsDir = new File("reviews");
    if (!reviewsDir.exists()) {
        boolean created = reviewsDir.mkdir();
        if (!created) {
            System.out.println("Failed to create reviews directory");
            throw new IOException("Could not create reviews directory");
        }
    }
    
    // Append the review to a reviews file
    String reviewFileName = "reviews/" + this.name.replaceAll("[^a-zA-Z0-9]", "_") + "_reviews.csv";
    File reviewFile = new File(reviewFileName);
    boolean isNewFile = !reviewFile.exists();
    
    try (FileWriter writer = new FileWriter(reviewFile, true);
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
        
        // If the file is new, add a header
        if (isNewFile) {
            bufferedWriter.write("Location,Rating,Review");
            bufferedWriter.newLine();
        }
        
        // Write the review data
        bufferedWriter.write(this.name + "," + rating + ",\"" + reviewText.replace("\"", "\"\"") + "\"");
        bufferedWriter.newLine();
        
        System.out.println("Review saved successfully to: " + reviewFile.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("Error saving review: " + e.getMessage());
        throw e;
    }
    
    // Verify the file was created and contains data
    if (reviewFile.exists()) {
        System.out.println("Review file exists at: " + reviewFile.getAbsolutePath());
        System.out.println("File size: " + reviewFile.length() + " bytes");
    } else {
        System.out.println("Review file does not exist after saving!");
    }
}



	
// Add these fields to the Location class
private static Schedule globalSchedule = new Schedule();

public boolean scheduleVisit(Scanner scanner) {
    System.out.println("Schedule a visit to " + this.name);
    
    String startTime = "";
    boolean validStartFormat = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    dateFormat.setLenient(false);
    Date startDate = null;
    
    // Loop until valid start date/time format is entered
    while (!validStartFormat) {
        System.out.println("Enter start date and time (yyyy-MM-dd HH:mm): ");
        startTime = scanner.nextLine().trim();
        
        if (startTime.isEmpty()) {
            System.out.println("Start time cannot be empty. Please try again.");
            continue;
        }
        
        try {
            // Validate the format - remove any closing parenthesis that might have been copied
            startTime = startTime.replace(")", "");
            
            // Try to format the time properly if it has single-digit minutes
            if (startTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{1,2}:\\d{1}")) {
                String[] parts = startTime.split(":");
                String beforeColon = parts[0];
                String afterColon = parts[1];
                if (afterColon.length() == 1) {
                    startTime = beforeColon + ":0" + afterColon;
                }
            }
            
            startDate = dateFormat.parse(startTime);
            validStartFormat = true; // Format is valid, exit the loop
        } catch (ParseException e) {
            System.out.println("Invalid date/time format. Please use the format yyyy-MM-dd HH:mm (e.g., 2023-05-20 14:30)");
            System.out.println("Make sure to use two digits for hours and minutes (e.g., 09:05, not 9:5)");
            // Loop will continue
        }
    }
    
    String endTime = "";
    boolean validEndFormat = false;
    boolean validEndTime = false;
    Date endDate = null;
    
    // Loop until valid end date/time format is entered AND end time is different from start time
    while (!validEndFormat || !validEndTime) {
        System.out.println("Enter end date and time (yyyy-MM-dd HH:mm): ");
        endTime = scanner.nextLine().trim();
        
        if (endTime.isEmpty()) {
            System.out.println("End time cannot be empty. Please try again.");
            continue;
        }
        
        try {
            // Validate the format - remove any closing parenthesis that might have been copied
            endTime = endTime.replace(")", "");
            
            // Try to format the time properly if it has single-digit minutes
            if (endTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{1,2}:\\d{1}")) {
                String[] parts = endTime.split(":");
                String beforeColon = parts[0];
                String afterColon = parts[1];
                if (afterColon.length() == 1) {
                    endTime = beforeColon + ":0" + afterColon;
                }
            }
            
            endDate = dateFormat.parse(endTime);
            
            validEndFormat = true; // Format is valid
            
            // Check if end time is different from start time
            if (endTime.equals(startTime)) {
                System.out.println("End time must be different from start time. Please enter a different time.");
                validEndTime = false;
            } 
            // Check if end time is after start time
            else if (endDate.before(startDate)) {
                System.out.println("End time must be after start time. Please enter a valid end time.");
                validEndTime = false;
            }
            else {
                validEndTime = true; // End time is valid, exit the loop
            }
        } catch (ParseException e) {
            System.out.println("Invalid date/time format. Please use the format yyyy-MM-dd HH:mm (e.g., 2023-05-20 16:30)");
            System.out.println("Make sure to use two digits for hours and minutes (e.g., 09:05, not 9:5)");
            validEndFormat = false;
            // Loop will continue
        }
    }
    
    boolean scheduled = globalSchedule.addEvent(this.name, startTime, endTime);
    if (scheduled) {
        // If successfully scheduled, add to wishlist
        try {
            add2Wishlist();
            System.out.println("Location added to wishlist with schedule.");
        } catch (IOException e) {
            System.out.println("Failed to add to wishlist: " + e.getMessage());
        }
    } else {
        System.out.println("Failed to schedule visit. The time slot may be already booked.");
    }
    
    // Always ask if the user wants to continue or exit, regardless of scheduling success
    boolean validChoice = false;
    while (!validChoice) {
        System.out.println("\nWould you like to see the NEXT location or STOP? (NEXT/STOP): ");
        String choice = scanner.nextLine().trim().toUpperCase();
        
        if (choice.equals("NEXT")) {
            validChoice = true;
            return true; // Continue to next location
        } else if (choice.equals("STOP")) {
            validChoice = true;
            System.out.println("Thank you for using the application. Goodbye!");
            System.exit(0); // Exit the application
            return false;
        } else {
            System.out.println("Invalid choice. Please enter NEXT or STOP.");
        }
    }
    
    return scheduled;
}



public void add2Wishlist() throws IOException {
    String os = getOS();
    if (os == null) {
        System.out.println("Unable to determine operating system.");
        return;
    }
    
    String user = System.getProperty("user.name");
    String basePath = "";
    
    // Set the base path based on OS
    if (os.equals("Windows")) {
        basePath = "C:/Users/" + user + "/Desktop/routenroam/wishlistFolder";
    } else if (os.equals("Linux")) {
        basePath = "/home/" + user + "/Desktop/routenroam/wishlistFolder";
    } else if (os.equals("MacOS")) {
        basePath = "/Users/" + user + "/Desktop/routenroam/wishlistFolder";
    } else {
        System.out.println("Unable to save. Incompatible operating system.");
        return;
    }
    
    // Create the directory if it doesn't exist
    try {
        Path wishlistFolder = Paths.get(basePath);
        if (!Files.exists(wishlistFolder)) {
            Files.createDirectories(wishlistFolder);
        }
    } catch (IOException ex) {
        System.out.println("Unable to create folder: " + ex.getMessage());
        throw ex;
    }
    
    // Save the wishlist to CSV
    File wishlistFile = new File(basePath + "/wishlist.csv");
    writeFileCSV(wishlistFile);
    
    // Save the schedule to CSV
    File scheduleFile = new File(basePath + "/schedule.csv");
    writeScheduleCSV(scheduleFile);
    
    System.out.println("Location added to wishlist successfully.");
}

// New method to write wishlist to CSV
private void writeFileCSV(File file1) {
    if (file1 == null) {
        System.out.println("File object is null. Unable to write to file.");
        return;
    }
    
    try (FileWriter writer = new FileWriter(file1, true); 
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
        
        // If the file is empty, write a header row
        if (file1.length() == 0) {
            bufferedWriter.write("Name,Type,Location,Reservation,CoatCheck,Cover,Price,Stars,Website");
            bufferedWriter.newLine();
        }
        
        // Write data to the file with proper CSV escaping
        bufferedWriter.write(
            escapeCSV(this.name) + "," + 
            escapeCSV(this.type) + "," +
            escapeCSV(this.location) + "," +
            escapeCSV(this.reserve) + "," +
            escapeCSV(this.coat) + "," +
            escapeCSV(this.cover) + "," +
            escapeCSV(this.price) + "," +
            escapeCSV(this.stars) + "," +
            escapeCSV(this.web)
        );
        bufferedWriter.newLine(); // Adds a newline after writing
        System.out.println("Location added to wishlist CSV.");
        
    } catch (IOException ex) {
        System.out.println("Failed to save to CSV file: " + ex.getMessage());
    }
}

// New method to write schedule to CSV
private void writeScheduleCSV(File scheduleFile) throws IOException {
    try (FileWriter writer = new FileWriter(scheduleFile, true);
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
        
        // If the file is empty, write a header row
        if (scheduleFile.length() == 0) {
            bufferedWriter.write("Location,StartTime,EndTime");
            bufferedWriter.newLine();
        }
        
        // Get the schedule entries for this location
        List<ScheduleEntry> entries = globalSchedule.getEntriesForLocation(this.name);
        
        // Write each schedule entry to the CSV
        for (ScheduleEntry entry : entries) {
            bufferedWriter.write(
                escapeCSV(entry.getLocation()) + "," +
                escapeCSV(entry.getStartTime()) + "," +
                escapeCSV(entry.getEndTime())
            );
            bufferedWriter.newLine();
        }
        
        System.out.println("Schedule saved successfully to: " + scheduleFile.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("Error saving schedule: " + e.getMessage());
        throw e;
    }
}

// Helper method to properly escape CSV values
private String escapeCSV(String value) {
    if (value == null) {
        return "";
    }
    
    // If the value contains commas, quotes, or newlines, wrap it in quotes and escape any quotes
    if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
    return value;
}

// Add this method to load the schedule from a CSV file
public static void loadScheduleFromCSV(File scheduleFile) {
    if (!scheduleFile.exists()) {
        System.out.println("Schedule CSV file does not exist. Starting with empty schedule.");
        return;
    }
    
    try {
        List<String> lines = Files.readAllLines(scheduleFile.toPath());
        
        // Skip the header row if it exists
        boolean hasHeader = !lines.isEmpty() && lines.get(0).startsWith("Location,");
        int startIndex = hasHeader ? 1 : 0;
        
        // Clear the existing schedule
        globalSchedule = new Schedule();
        
        // Process each line
        for (int i = startIndex; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = parseCSVLine(line);
            
            if (parts.length >= 3) {
                String location = parts[0];
                String startTime = parts[1];
                String endTime = parts[2];
                
                globalSchedule.addEvent(location, startTime, endTime);
            }
        }
        
        System.out.println("Schedule loaded successfully from: " + scheduleFile.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("Error loading schedule from CSV: " + e.getMessage());
    }
}

// Helper method to parse CSV lines correctly (handling quoted values)
private static String[] parseCSVLine(String line) {
    List<String> result = new ArrayList<>();
    StringBuilder currentValue = new StringBuilder();
    boolean inQuotes = false;
    
    for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        
        if (c == '\"') {
            // If we see a quote, toggle the inQuotes flag
            if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                // This is an escaped quote (two quotes in a row)
                currentValue.append('\"');
                i++; // Skip the next quote
            } else {
                inQuotes = !inQuotes;
            }
        } else if (c == ',' && !inQuotes) {
            // If we see a comma and we're not in quotes, end the current value
            result.add(currentValue.toString());
            currentValue = new StringBuilder();
        } else {
            // Otherwise, add the character to the current value
            currentValue.append(c);
        }
    }
    
    // Add the last value
    result.add(currentValue.toString());
    
    return result.toArray(new String[0]);
}


    
	
	public void schedule() {}
		// same issue as add2Wishlist
	
	
	
	public String getOS() { //legit the simpler version of what rachel did 
	    String os = System.getProperty("os.name").toLowerCase();
	    if (os.contains("win")) {
	        return "Windows";
	    } else if (os.contains("mac")) {
	        return "MacOS";
	    } else if (os.contains("nix") || os.contains("nux")) {
	        return "Linux";
	    } else {
	        System.out.println("Compatible operating system not detected");
	        return null;
	    }
	}

	
	
	
	
	
	
	
	public File createFile(String fileName, String filePath) {
	    File file1;
	    try {
	        file1 = new File(filePath + fileName);
	        if (file1.createNewFile()) {
	            System.out.println("File created: " + file1.getName());
	        } else {
	            System.out.println("File already exists.");
	        }
	    } catch (IOException ex) {
	        System.out.println("Failed to create file: " + ex.getMessage());
	        file1 = null;
	    }
	    return file1;
	} // createFile method
	
	
	
	
	public void writeFile(File file1) {	
		if (file1 == null) {
	        System.out.println("File object is null. Unable to write to file.");
	        return;
		}
		
	        
	        try (FileWriter writer = new FileWriter(file1, true); 
	                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

	               // Write data to the file
	               bufferedWriter.write(this.name + "," + 
	                                    this.type + "," +
	                                    this.location + "," +
	                                    this.reserve + "," +
	                                    this.coat + "," +
	                                    this.cover + "," +
	                                    this.price + "," +
	                                    this.stars + "," +
	                                    this.web);
	               bufferedWriter.newLine(); // Adds a newline after writing
	               System.out.println("Location added to wishlist.");

	           } catch (IOException ex) {
	               System.out.println("Failed to save to file: " + ex.getMessage());
	           }
	        
		}

	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public Review getReview() {
		// TODO Auto-generated method stub
		return null;
	}
	}

