package cs335_package;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Location {
	private String name;
	private String type; // corresponds to type of location (i.e. bakery, bar, spa, etc.)
	private String location;
	private String reserve; // can be empty
	private String coat; // can be empty
	private String cover; // can be empty
	private double price;
	private double stars;
	private String web; // can be empty
	private ReviewList reviews; // can be empty
	
	
	private Review review;
	private List<String> schedule; // To store schedule info

	

	
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
	

   /* // Method to save the schedule to a file
    private void writeSchedule(File scheduleFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scheduleFile))) {
            for (String event : schedule) {
                writer.write(event);  // Write each event to the file
                writer.newLine();  // Write a new line after each event
            }
        }
    } */
    
    public String toString() {
        return("Name: " + this.name +
               "\nType: " + this.type +
               "\nLocation: " + this.location +
               "\nReservation: " + this.reserve +
               "\nCoat Check: " + this.coat +
               "\nCover: " + this.cover +
               "\nPrice: " + Double.toString(this.price) +
               "\nStars: " + Double.toString(this.stars) +
               "\nWebsite: " + this.web
        );
    }

    public String[] toArray() {
        String[] locArray = {this.name, this.type, this.location, this.reserve, this.coat, this.cover,
                            Double.toString(this.price), Double.toString(this.stars), this.web};
        return locArray;
    }

    public String getName() {return this.name;}
    public String getType() {return this.type;}
    public String getLocation() {return this.location;}
    public String getReservationStatus() {return this.reserve;}
    public String getCoatCheckStatus() {return this.coat;}
    public String getCoverStatus() {return this.cover;}
    public String getWeb() {return this.web;}
    public double getPrice() {return this.price;}
    public double getStars() {return this.stars;}
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

// Add these methods to the Location class
public boolean scheduleVisit(Scanner scanner) {
    System.out.println("Schedule a visit to " + this.name);
    
    System.out.println("Enter start date and time (yyyy-MM-dd HH:mm): ");
    String startTime = scanner.nextLine().trim();
    
    System.out.println("Enter end date and time (yyyy-MM-dd HH:mm): ");
    String endTime = scanner.nextLine().trim();
    
    boolean scheduled = globalSchedule.addEvent(this.name, startTime, endTime);
    if (scheduled) {
        // If successfully scheduled, add to wishlist
        try {
            add2Wishlist();
            System.out.println("Location added to wishlist with schedule.");
        } catch (IOException e) {
            System.out.println("Failed to add to wishlist: " + e.getMessage());
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
    
    // Save the wishlist
    File wishlistFile = new File(basePath + "/wishlist.FILE");
    writeFile(wishlistFile);
    
    // Save the schedule
    File scheduleFile = new File(basePath + "/schedule.FILE");
    writeSchedule(scheduleFile);
    
    System.out.println("Location added to wishlist successfully.");
}


// Add this method to write the schedule to a file
private void writeSchedule(File scheduleFile) throws IOException {
    try (FileWriter writer = new FileWriter(scheduleFile);
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
        
        // Write the schedule data
        bufferedWriter.write(globalSchedule.toCSVString());
        
        System.out.println("Schedule saved successfully to: " + scheduleFile.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("Error saving schedule: " + e.getMessage());
        throw e;
    }
}

// Add this method to load the schedule from a file
public static void loadSchedule(File scheduleFile) {
    if (!scheduleFile.exists()) {
        System.out.println("Schedule file does not exist. Starting with empty schedule.");
        return;
    }
    
    try {
        String content = new String(Files.readAllBytes(scheduleFile.toPath()));
        globalSchedule = Schedule.fromCSVString(content);
        System.out.println("Schedule loaded successfully from: " + scheduleFile.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("Error loading schedule: " + e.getMessage());
    }
}

 

    

	



	
	/*public void add2Wishlist() throws IOException {
		
		String os = getOS(); // Get OS this is early on knowing if we can support a systeom
	    if (os == null) {
	        System.out.println("Unable to determine operating system.");
	        return; // Exit the method early if OS is null
	    }
	    
	    String user = System.getProperty("user.name");  // This should correctly get the username
		String basePath = "";
		//String userHome = System.getProperty("user.home"); //lets try for home directory it was user.name
		
		
		//if (os=="Windows") {
	    if (os.equals("Windows")) {
	        try {
	            Path wishlistFolder = Paths.get("C:/Users/" + user + "/Desktop/routenroam/wishlistFolder");
	            Files.createDirectories(wishlistFolder); // Create folder if it doesn't exist
	        } catch (IOException ex) {
	            System.out.println("Unable to create folder on Windows");
	        }

	        File f = new File("C:/Users/" + user + "/routenroam/wishlist/wishlist.FILE");
	        Path path = Paths.get("C:/Users/" + user + "/routenroam/wishlist/wishlist.FILE");

	        if (Files.exists(path)) {
	            writeFile(f); // If file exists, write to it
	        } else {
	            f = createFile("wishlist", "C:/Users/" + user + "/routenroam/wishlistFolder/");
	            writeFile(f); // Create and write to file if it doesn't exist
	        }
			
		//linux uses a different path it will do /home/user instead of c:/users/	
			
		//}else if(os=="Linux") {
		//} else if (os.equals("Linux")) {

		//	try{
		//		Path wishlistFolder = Paths.get("C:/Users/"+user+"/routenroam/wishlistFolder");
		//		Files.createDirectories(wishlistFolder); // creates folder -- createDirectories should check if folder exists first
		//	}catch(IOException ex){
			//	System.out.println("Unable to create folder");
		//	}
		//	File f = new File("C:/Users/"+user+"/routenroam/wishlist/wishlist.FILE");
		//	Path path = FileSystems.getDefault().getPath("C:\\Users\\"+user+"\\routenroam\\wishlist\\wishlist.FILE");
		//	if (Files.exists(path)) { // checks if file exists
			//	writeFile(f); // save location to file		
		//	}else { // creates file
		//		f = createFile("wishlist","C:/Users/"+user+"/routenroam/wishlistFolder/");
			//	writeFile(f); // save location to file	
		//	}
			
			
	        
	        
	        
		} else if (os.equals("Linux")) {
	        try {
	            Path wishlistFolder = Paths.get("/home/" + user + "/routenroam/wishlistFolder");
	            Files.createDirectories(wishlistFolder); // Create folder if it doesn't exist
	        } catch (IOException ex) {
	            System.out.println("Unable to create folder on Linux");
	        }
	
	        File f = new File("/home/" + user + "/routenroam/wishlist/wishlist.FILE");
	        Path path = Paths.get("/home/" + user + "/routenroam/wishlist/wishlist.FILE");
	
	        if (Files.exists(path)) {
	            writeFile(f); // If file exists, write to it
	        } else {
	            f = createFile("wishlist", "/home/" + user + "/routenroam/wishlistFolder/");
	            writeFile(f); // Create and write to file if it doesn't exist
	        }
				
	        
	        
	        
	        
				
			//}else if(os=="MacOS") {
		} else if (os.equals("MacOS")) {
	        try {
	            Path wishlistFolder = Paths.get("/Users/" + user + "/Desktop/routenroam/wishlistFolder");
	            if (!Files.exists(wishlistFolder)) {
	                Files.createDirectories(wishlistFolder); // Create folder if it doesn't exist
	            }
	        } catch (IOException ex) {
	            System.out.println("Unable to create folder on MacOS");
	            ex.printStackTrace(); // Log exception for debugging
	        }
	
	        File f1 = new File("/Users/" + user + "/Desktop/routenroam/wishlistFolder/wishlist.FILE");
	        Path path1 = Paths.get("/Users/" + user + "/Desktop/routenroam/wishlistFolder/wishlist.FILE");
	
	        if (Files.exists(path1)) {
	            writeFile(f1); // If file exists, write to it
	        } else {
	            f1 = createFile("wishlist", "/Users/" + user + "/Desktop/routenroam/wishlistFolder/");
	            writeFile(f1); // Create and write to file if it doesn't exist
	        }
	        
	     
			// Now save the schedule
            File scheduleFile = new File(basePath + "/schedule.FILE");
            Path schedulePath = Paths.get(basePath + "/schedule.FILE");

            if (Files.exists(schedulePath)) {
                writeSchedule(scheduleFile); // If schedule file exists, write to it
            } else {
                scheduleFile = createFile("schedule", basePath);
                writeSchedule(scheduleFile); // Create and write to file if it doesn't exist
            }
            
	
	    } else {
	        System.out.println("Unable to save. Incompatible operating system.");
	    }
	} // add2WishList method
			
*/
    
	
	public void schedule() {}
		// same issue as add2Wishlist
	
	
	
	
	
	
	//public String getOS() { (this was rachels code) howveer this code was creating this to fail. So i took parts of it and did ifs and elses
	//	String[] possibleSystems = {"Windows","Linux","MacOS"};
	//	String system = null; // OS used by user's computer
	//	boolean matchFound = false;
	//	for (String i:possibleSystems) { // goes through each os and breaks if match is found
	//		String os = System.getProperty("os.name"); // gets os on user's computer
	//		Pattern pattern = Pattern.compile(i, Pattern.CASE_INSENSITIVE); // pattern to be found
	//		Matcher match = pattern.matcher(os); // look for pattern in this string
	//		matchFound = match.find();
	//		if (matchFound) {
	//			system = i;
	//			break;
	//		}
		//}
	
	
	
	
	
	
	
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

	
	
	
	
	
		//if (matchFound) {
	//		return system; // returns system name
	//	}else{
	//		System.out.println("Compatible operating system not detected");
	//		return null;
	//	}
//	} //getOS method
	
	
	
	
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
	
	
	/* public File createFile(String fileName,String filePath) {
		File file1;
		try {
			file1 = new File(filePath+fileName);
			file1.createNewFile();
		}catch(IOException ex){
			System.out.println("Failed to create file");
			file1 = null;
		}
		return file1;
	} // createFile method */
	
	public void writeFile(File file1) {	
		if (file1 == null) {
	        System.out.println("File object is null. Unable to write to file.");
	        return;
		}
		/* try{
			String file = null; // making this a lowkey variable - nada
			FileWriter Writer = new FileWriter(file, true);
			Writer.write(this.name+","+
						this.type+","+
						this.location+","+
						this.reserve+","+
						this.coat+","+
						this.cover+","+
						this.price+","+
						this.stars+","+
						this.web);
			Writer.close();
			 System.out.println("Location added to wishlist.");
		}catch(IOException ex) {
			System.out.print("Failed to save"); */
	        
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

