package cs335_package;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Review {
	private String starsStr;
	private double starsDouble;
	public String text;
	private String reviewer; // Field for the reviewer's name
	
	// Constructor
    public Review() {
        this.text = ""; // Default empty string instead of null
        this.reviewer = "";
    }
    
    // getters
	public String getStarsStr() {return this.starsStr;}
	public double getStarsDouble() {return this.starsDouble;}
	public String getText() {return this.text;}
	public String getReviewer() { return this.reviewer; }
	
	// setters
	public void setText(String text) {
	    this.text = (text == null) ? "" : text.trim();
	}
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;    
    }

    public void createReview(Scanner scan) { 
        //Review r = new Review(); // Initialize a new Review object
        
        // Get star rating from user
        this.addStars(scan);
        
        /* get this information in the addStars method
        System.out.print("Enter your rating (1-5): ");
        while (!scan.hasNextInt()) {  // Ensure valid integer input
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scan.next(); // Consume invalid input
        }
        int stars = scan.nextInt();
        scan.nextLine(); // Consume the leftover newline
        r.setStars(stars); // Set stars in the Review object
		*/

        // Ask user if they want to add text to the review
        boolean cont = true;
        while (cont) {
            System.out.println("Do you want to add text to your review (yes/no)? ");
            String wantText = scan.nextLine().trim().toLowerCase();
            if (wantText.equals("yes")) {
            	this.addText(scan);
            	/* get this information in the addText method
                System.out.println("Enter your review text: ");
                String reviewText = scan.nextLine();
                r.setText(reviewText);
                */
                cont = false; // Exit loop after getting review text
            } else if (wantText.equals("no")) {
                this.setText(""); // No text review provided
                cont = false; // Exit loop
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
        
        // Ask user if they want to add a name
        /*
        boolean cont2 = true;
        while (cont2) {
        	System.out.println("Do you want to add a name to your review (yes/no)? ");
        	String wantName = scan.nextLine().trim().toLowerCase();
        	if (wantName.equals("yes")) {
        		r.addName(scan);
        		cont2 = false;
        	} else if (wantName.equals("no")) {
        		r.setReviewer("");
        		cont2 = false;
        	} else {
        		System.out.println("Please enter yes or no.");
        	}
        }
        */

        // Print review details
        System.out.println("\nThank you for your review!");
        System.out.println("Your review: " + this.getStarsStr() + " stars");
        System.out.println("Review: " + (this.getText().isEmpty() ? "No text review provided." : this.getText()));
    } // createReview method
    
    
	// Gets a valid star rating
	public void addStars(Scanner sc) { //i put the scanner in there so it wont cause closure issues 
		boolean cont = true;
	    while (cont) {
	    	System.out.print("Enter your rating (1-5): ");
	        if (sc.hasNextInt()) {
	            int rating = sc.nextInt();
	            sc.nextLine();  // Consume the newline character
	            if (rating >= 1 && rating <= 5) {
	                this.starsStr = Integer.toString(rating);
	                this.starsDouble = rating;
	                cont = false;
	            } else {
	                System.out.println("Please enter a rating between 1 and 5.");
	              }
	        }else {
	            sc.next(); // Consume the invalid input
	            System.out.println("Invalid input! Please enter a number between 1 and 5.");
	        }
	    }
	} // addStars method		

	// Gets a text review
	public void addText(Scanner sc) {// passed it in here so doesn't cause closure issues		
    	System.out.print("Enter your review: ");
        this.text = sc.nextLine().trim();        
	}
	
	// Gets a user name
	public void addName(Scanner sc) {
		System.out.println("Enter your name: ");
		this.reviewer = sc.nextLine().trim();
	}

	/* Same functionality as addStars method
	public void setStars(int stars) {
		if (stars >= 1 && stars <= 5) {
			this.stars = stars;
		} else { 
			System.out.println("Invalid star raiting. Must be between 1 and 5.");
		}		
	}
	*/         
        
    public void saveReviewToCSV(String fileName) throws IOException {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));  // Open file in append mode
            pw.println(this.reviewer + "," + this.starsStr + "," + this.text); // Write the review data
            pw.close();  // Close the writer
        } catch (IOException e) {
            System.out.println("Error saving review to CSV: " + e.getMessage());
            throw e;  // Rethrow if you want to propagate the exception
        }
    }
} // Review class
    

