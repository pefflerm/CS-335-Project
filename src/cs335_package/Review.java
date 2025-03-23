package cs335_package;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Review {
	private int stars;
	public String text;
	private String reviewer; // Field for the reviewer's name
	
	// Constructor
    public Review() {
        this.text = ""; // Default empty string instead of null
        this.reviewer = "";
    }
	
	
	public int getStars() {return this.stars;}
	public String getText() {return this.text;}
	public String getReviewer() { return this.reviewer; }
	
	// Method to get a valid star rating (1-5)
	public void addStars(Scanner sc) { //i put the scanner in there so it wont cause closure issues 
		boolean cont = true;
	    while (cont) {
	    	System.out.print("Enter your rating (1-5): ");
	        if (sc.hasNextInt()) {
	            int rating = sc.nextInt();
	            sc.nextLine();  // Consume the newline character
	            if (rating >= 1 && rating <= 5) {
	                this.stars = rating;
	                cont = false;
	            } else {
	                System.out.println("Please enter a rating between 1 and 5.");
	              }
	        }else {
	            sc.next(); // Consume the invalid input
	            System.out.println("Invalid input! Please enter a number between 1 and 5.");
	        }
	    }
	}		

	// Gets a text review
	public void addText(Scanner sc) {// passed it in here so doesn't cause closure issues
		
    	System.out.print("Enter your review: ");
        this.text = sc.nextLine().trim();
        
	}

	
	public void setStars(int stars) {
		if (stars >= 1 && stars <= 5) {
			this.stars = stars;
		} else { 
			System.out.println("Invalid star raiting. Must be between 1 and 5.");
		}
		
	}
	public void setText(String text) {
	    this.text = (text == null) ? "" : text.trim();
	}

	
	// Set reviewer name
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
        
    }
        
        
   /* } // Save the review to the CSV file
    public void saveReviewToCSV(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName, true); // Open the file in append mode
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.reviewer + "," + this.stars + "," + this.text);
            bw.newLine();
            bw.close();
         //catch (IOException e) {
            System.out.println("Error saving review to CSV: " + e.getMessage());
        
    } */
        
    public void saveReviewToCSV(String fileName) throws IOException {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));  // Open file in append mode
            pw.println(this.reviewer + "," + this.stars + "," + this.text); // Write the review data
            pw.close();  // Close the writer
        } catch (IOException e) {
            System.out.println("Error saving review to CSV: " + e.getMessage());
            throw e;  // Rethrow if you want to propagate the exception
        }
    }

        }
    

