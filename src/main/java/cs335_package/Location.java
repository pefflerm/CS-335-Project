package cs335_package;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId; // Import LocalDateTime
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; // Import ZoneId

import com.opencsv.CSVReader; // Import DateTimeFormatter
import com.opencsv.CSVWriter;
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
    public String getHours() {return this.BusinessHours;}
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
    } // updateStars function
    
    public String getReviewsFile() {
    	String reviewFileName = "reviews/" + this.name.replaceAll("[^a-zA-Z0-9]", "_") + "_reviews.csv";
    	File reviewPath = new File(reviewFileName);
    	if (reviewPath.exists()) {
    		return reviewFileName;
    	}else {
    		return "";
    	}
    } // getReviewsFile function

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

	    // Static getter for the global schedule, needed by ScheduleDialog
    public static Schedule getGlobalSchedule() {
        // Ensure globalSchedule is never null
        if (globalSchedule == null) {
            globalSchedule = new Schedule();
        }
        return globalSchedule;
    }

	
	// Add these fields to the Location class
	private static Schedule globalSchedule = new Schedule();
    private static final DateTimeFormatter scheduleFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	// Modified scheduleVisit to use ScheduleDialog
	public boolean scheduleVisit() { // Removed Scanner parameter
	    System.out.println("Opening schedule dialog for " + this.name);

	    // Create and show the dialog
	    // Pass null for the owner frame if you don't have a main application frame
	    ScheduleDialog dialog = new ScheduleDialog(null, this.name);
	    dialog.setVisible(true);

	    boolean scheduled = false;
	    if (dialog.isConfirmed()) {
	        Date startDate = dialog.getSelectedStartDate();
	        Date endDate = dialog.getSelectedEndDate();

	        // Convert Date objects to LocalDateTime
	        LocalDateTime startDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	        LocalDateTime endDateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

	        // Format LocalDateTime objects to strings
	        String startTimeStr = startDateTime.format(scheduleFormatter);
	        String endTimeStr = endDateTime.format(scheduleFormatter);

	        System.out.println("Attempting to schedule from " + startTimeStr + " to " + endTimeStr);

	        // Use the existing addEvent logic which handles conflicts
	        scheduled = globalSchedule.addEvent(this.name, startTimeStr, endTimeStr);

	        if (scheduled) {
	            System.out.println("Visit scheduled successfully via GUI.");
	            // If successfully scheduled, add to wishlist
	            try {
	                add2Wishlist(); // This method now also saves the schedule
	                System.out.println("Location added to wishlist with schedule.");
	            } catch (IOException e) {
	                System.out.println("Failed to add to wishlist: " + e.getMessage());
	            }
	        } else {
	            // The addEvent method in Schedule already prints conflict messages
	            System.out.println("Failed to schedule visit via GUI. The time slot may be already booked or invalid.");
	        }
	    } else {
	        System.out.println("Scheduling cancelled by user.");
	    }

	    dialog.dispose(); // Clean up the dialog resources
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
	        System.out.println("File object is null. Unable to write to wishlist file.");
	        return;
	    }

	    boolean fileExists = file1.exists();
        List<String> existingLines = new ArrayList<>();

        // Read existing lines to check for duplicates
        if (fileExists) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file1))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    existingLines.add(line);
                }
            } catch (IOException ex) {
                System.out.println("Error reading existing wishlist file: " + ex.getMessage());
                // Continue anyway, might overwrite or append duplicates
            }
        }

        // Check if this location is already in the wishlist
        String currentLocationLine = escapeCSV(this.name) + "," +
                                   escapeCSV(this.type) + "," +
                                   escapeCSV(this.location) + "," +
                                   escapeCSV(this.reserve) + "," +
                                   escapeCSV(this.coat) + "," +
                                   escapeCSV(this.cover) + "," +
                                   escapeCSV(this.price) + "," +
                                   escapeCSV(this.starsStr) + "," +
                                   escapeCSV(this.web);

        boolean alreadyExists = false;
        for (String line : existingLines) {
            // Simple check: assumes name is the first column and unique identifier
            if (line.startsWith(escapeCSV(this.name) + ",")) {
                alreadyExists = true;
                break;
            }
        }

        if (alreadyExists) {
             System.out.println("Location '" + this.name + "' is already in the wishlist CSV.");
             return; // Don't add it again
        }


	    try (FileWriter writer = new FileWriter(file1, true); // Append mode
	         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

	        // If the file is new (or was empty), write a header row
	        if (!fileExists || file1.length() == 0) {
	            bufferedWriter.write("Name,Type,Location,Reservation,CoatCheck,Cover,Price,Stars,Website");
	            bufferedWriter.newLine();
	        }

	        // Write data to the file with proper CSV escaping
	        bufferedWriter.write(currentLocationLine);
	        bufferedWriter.newLine(); // Adds a newline after writing
	        System.out.println("Location added to wishlist CSV.");

	    } catch (IOException ex) {
	        System.out.println("Failed to save to wishlist CSV file: " + ex.getMessage());
	    }
	} 




		// Modified method to write only the latest schedule entry to CSV
		private void writeScheduleCSV(File scheduleFile) throws IOException {
			if (scheduleFile == null) {
				System.out.println("Schedule file object is null. Unable to write.");
				return;
			}
	
			// Find the most recently added event for THIS location in the global schedule
			List<Schedule.ScheduledEvent> allEvents = globalSchedule.getEvents(); // Assuming getEvents() returns a safe copy
			Schedule.ScheduledEvent latestEventForThisLocation = null;
			for (int i = allEvents.size() - 1; i >= 0; i--) {
				Schedule.ScheduledEvent event = allEvents.get(i);
				if (event.getLocationName().equals(this.name)) {
					latestEventForThisLocation = event;
					break; // Found the latest one for this location
				}
			}
	
			// If no event was found for this location recently, nothing to write
			if (latestEventForThisLocation == null) {
				System.out.println("No new schedule entry found for '" + this.name + "' to save to schedule.csv.");
				return;
			}
	
			boolean fileExists = scheduleFile.exists();
			List<String> existingLines = new ArrayList<>();
			boolean entryAlreadyExists = false;
	
			 // Read existing schedule to prevent duplicates
			if (fileExists) {
				try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFile))) {
					String line;
					while ((line = reader.readLine()) != null) {
						existingLines.add(line);
						// Check if this specific event is already saved
						String[] parts = parseCSVLine(line);
						 if (parts.length >= 3 &&
							 parts[0].equals(latestEventForThisLocation.getLocationName()) &&
							 parts[1].equals(latestEventForThisLocation.getStartTime().format(scheduleFormatter)) &&
							 parts[2].equals(latestEventForThisLocation.getEndTime().format(scheduleFormatter))) {
							entryAlreadyExists = true;
							// No need to read further if duplicate found
							// break; // Optimization: If duplicates are frequent, uncomment break
						}
					}
				} catch (IOException ex) {
					System.out.println("Error reading existing schedule file: " + ex.getMessage());
					// Decide whether to proceed or stop
				}
			}
	
			if (entryAlreadyExists) {
				System.out.println("Schedule entry for '" + this.name + "' from " +
								   latestEventForThisLocation.getStartTime().format(scheduleFormatter) + " to " +
								   latestEventForThisLocation.getEndTime().format(scheduleFormatter) +
								   " already exists in schedule.csv.");
				return; // Don't write the duplicate entry
			}
	
	
			try (FileWriter writer = new FileWriter(scheduleFile, true); // Append mode
				 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
	
				// If the file is new or empty, write a header row
				if (!fileExists || scheduleFile.length() == 0) {
					bufferedWriter.write("Location,StartTime,EndTime");
					bufferedWriter.newLine();
				}
	
				// Write the latest schedule entry to the CSV
				bufferedWriter.write(
					escapeCSV(latestEventForThisLocation.getLocationName()) + "," +
					escapeCSV(latestEventForThisLocation.getStartTime().format(scheduleFormatter)) + "," +
					escapeCSV(latestEventForThisLocation.getEndTime().format(scheduleFormatter))
				);
				bufferedWriter.newLine();
				System.out.println("Latest schedule entry saved successfully to: " + scheduleFile.getAbsolutePath());
	
			} catch (IOException e) {
				System.out.println("Error saving schedule: " + e.getMessage());
				throw e; // Re-throw exception
			}
		} // writeScheduleCSV function
	
	
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
		} // escapeCSV function
	
		// Add this method to load the schedule from a CSV file
		public static void loadScheduleFromCSV(File scheduleFile) {
			if (scheduleFile == null || !scheduleFile.exists()) {
				System.out.println("Schedule CSV file does not exist or is null. Starting with empty schedule.");
				globalSchedule = new Schedule(); // Ensure schedule is initialized even if file doesn't exist
				return;
			}
	
			// Ensure globalSchedule is initialized before loading
			globalSchedule = new Schedule();
	
			try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFile))) {
				String line = reader.readLine(); // Read header or first line
	
				// Check if the first line is a header and skip it
				if (line != null && line.startsWith("Location,StartTime,EndTime")) {
					// It's a header, read the next line
					line = reader.readLine();
				}
	
				// Process remaining lines
				while (line != null) {
					String[] parts = parseCSVLine(line);
	
					if (parts.length >= 3) {
						String location = parts[0];
						String startTime = parts[1];
						String endTime = parts[2];
	
						// Use addEvent to add to the schedule (includes conflict checking)
						boolean added = globalSchedule.addEvent(location, startTime, endTime);
						if (!added) {
							 System.out.println("Warning: Could not load schedule entry due to conflict or format issue: " + line);
						}
					} else if (!line.trim().isEmpty()){
						 System.out.println("Warning: Skipping malformed line in schedule CSV: " + line);
					}
					line = reader.readLine(); // Read next line
				}
	
				System.out.println("Schedule loaded successfully from: " + scheduleFile.getAbsolutePath());
	
			} catch (IOException e) {
				System.out.println("Error loading schedule from CSV: " + e.getMessage());
				// Consider initializing an empty schedule here too, depending on desired behavior on error
				globalSchedule = new Schedule();
			}
		} // loadScheduleFromCSV function
	
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
					result.add(currentValue.toString().trim()); // Trim whitespace
					currentValue = new StringBuilder();
				} else {
					// Otherwise, add the character to the current value
					currentValue.append(c);
				}
			}
	
			// Add the last value
			result.add(currentValue.toString().trim()); // Trim whitespace
	
			return result.toArray(new String[0]);
		} // parseCSVLine method

		

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
	} // getOS function		
	
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
