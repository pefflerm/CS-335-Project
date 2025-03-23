package cs335_package;
import java.util.*;

public class ReviewList {
	private final ArrayList<Review> allReviews = new ArrayList<>();
	
	// Adds a review to the list
	public void addReview(Review r) {
		allReviews.add(r);
	}
	
	// Prints all reviews in a readable format
	public void printReviewList() {
		for (Review i : allReviews) {
			System.out.printf("   %2d  %s\n", i.getStars(), i.getText());
		}
	}
	// Add this method to ReviewList class
	public ArrayList<Review> getReviewArray() {
    return allReviews;
}




	// Returns a string representation of all reviews
	public String getReviews() {
		StringBuilder sb = new StringBuilder();
		for (Review i : allReviews) {
			sb.append(String.format("   %2d  %s\n", i.getStars(), i.getText()));
		}
		return sb.toString();
	}
}
