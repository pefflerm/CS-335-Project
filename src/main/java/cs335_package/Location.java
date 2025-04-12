package cs335_package;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;


public class Location {
	private String name;
	private String type; // corresponds to type of location (i.e. bakery, bar, spa, etc.)
	private String location;
	private String reserve; // can be empty
	private String coat; // can be empty
	private String cover; // can be empty
	private String price;  // Changed from double
    private String starsStr;  // Changed from double
    private double starsDouble;
	private String web; // can be empty
	private ReviewList reviews;
	private String BusinessHours;// can be empty
	private List<String> schedule; // To store schedule info
	private int id; // row of the csv
	private String filePath;
	private int numOfReviews;

	// setters
    public void setPrice(String price) { this.price = price; }
    public void setStarsStr(String stars) { this.starsStr = stars; }
    public void setStarsDouble(double stars) {this.starsDouble = stars;}
	public void setLocation(String location) { this.location = location; }
	public void setType(String type) { this.type = type; }
    public void setReservation(String reserve) { this.reserve = reserve; }
    public void setCoatCheck(String coat) { this.coat = coat; }
    public void setCover(String cover) { this.cover = cover; }  
    public void setWeb(String web) { this.web = web; }
    public void setID(int id) {this.id = id;}
    public void setFilePath(String path) {this.filePath = path;}
    public void setNumOfReviews(int num) {this.numOfReviews = num;}
    
    // getters
    public String getName() {return this.name;}
    public String getType() {return this.type;}
    public String getLocation() {return this.location;}
    public String getReservationStatus() {return this.reserve;}
    public String getCoatCheckStatus() {return this.coat;}
    public String getCoverStatus() {return this.cover;}
    public String getWeb() {return this.web;}
    public String getPrice() { return this.price; }
    public ReviewList getReviewList() {return this.reviews;}
    public String getStarsStr() {return this.starsStr;}
    public int getID() {return this.id;}
    public String getFilePath() {return this.filePath;}
    public int getNumOfReviews() {return this.numOfReviews;}
    
    // constructor
	Location(String n, String t){
		this.name = n;
		this.type = t;
		this.reviews = new ReviewList();  // Add this line to initialize the reviews list
		this.schedule = new ArrayList<>(); // Initialize the schedule
		this.starsDouble = 0;
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


    // Update toString method
	public String toString() {
	    return("Name: " + this.name +	      
	           "\nLocation: " + this.location +
	           "\nReservation: " + this.reserve +	           
	           "\nPrice: " + this.price +  // No need for Double.toString()
	           "\nStars: " + this.starsStr +  // No need for Double.toString()
	           "\nWebsite: " + this.web+
	           "\nBusiness Hours: " + this.BusinessHours
	    );
	}

    public String[] toArray() {String[] locArray = {this.name, this.location, this.reserve,
        this.price, this.starsStr, this.web};
	return locArray;
	}

    public void updateStars(Review r) {
    	// CSV file path is now stored as an attribute
	    String csvFilePath = this.getFilePath();
	    
	    // Calculate new average
	    this.starsDouble = ((this.starsDouble*this.getNumOfReviews()) + r.getStarsDouble())/(this.getNumOfReviews()+1);
	    this.starsStr = Double.toString(this.starsDouble);
	    this.numOfReviews += 1;
	    
	    try {
	    	// gets the cell to be updated
	    	CSVReader reader = new CSVReader(new FileReader(csvFilePath));
	    	List<String[]> csvBody = reader.readAll();
	    	csvBody.get(this.id)[7] = this.starsStr;
	    	reader.close();
	    	
	    	// writes to that cell
	    	CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
	    	writer.writeAll(csvBody);
	    	writer.flush();
	    	writer.close();	    	
	    } catch(IOException|CsvException e){
	    	e.printStackTrace();
	    }
    }
    
    public void readReviews() {
    	String reviewFileName = "reviews/" + this.name.replaceAll("[^a-zA-Z0-9]", "_") + "_reviews.csv";
    	try{
    		CSVReader reader = new CSVReader(new FileReader(reviewFileName));
    		List<String[]> csvBody = reader.readAll();
    		System.out.println(csvBody.get(0)[1]+"\t"+csvBody.get(0)[2]); // header row
    		int currentRow = 1;
    		while (currentRow<csvBody.size()) {
    			System.out.println(csvBody.get(currentRow)[1]+"\t"+csvBody.get(currentRow)[2]);
    			currentRow += 1;
    		}
    		reader.close();
    	}catch(CsvException|IOException e) {
    		e.printStackTrace();
    	}
    }

	public void saveReviewToCSV(Review r) throws IOException {	    
	    // Add the review to the location's review list
	    if (this.reviews == null) {
	        this.reviews = new ReviewList();
	    }
	    this.reviews.addReview(r); // addReview is a ReviewList method
	    
	    
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
	        bufferedWriter.write(this.name + "," + r.getStarsStr() + ",\"" + r.getText().replace("\"", "\"\"") + "\"");
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
	} // saveReviewToCSV method


	
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
	    
	    /*
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
	    */
	    
	    return scheduled;
	} // scheduleVisit method



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
	} // add2Wishlist method
	
	
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
	            escapeCSV(this.starsStr) + "," +
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
	                                   
	                                    this.location + "," +
	                                    this.reserve + "," +
	                              
	                                    this.price + "," +
	                                    this.starsStr + "," +
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

