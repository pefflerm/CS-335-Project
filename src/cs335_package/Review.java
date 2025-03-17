package cs335_package;
import java.util.Scanner;

public class Review {
	private int stars;
	public String text;
	
	
	public int getStars() {return this.stars;}
	public String getText() {return this.text;}
	
	// Method to get a valid star rating (1-5)
	public void addStars() {
		Scanner sc = new Scanner(System.in);
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
	    }sc.close();
	}		

	// Gets a text review
	public void addText() {
		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter your review: ");
        this.text = sc.nextLine().trim();
        sc.close();
	}
}
