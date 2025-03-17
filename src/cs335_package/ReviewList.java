package cs335_package;
import java.util.*;

public class ReviewList {
	private final ArrayList<Review> allReviews = new ArrayList<>();
	public int len = 0;
	
	public void addReview(Review r) {
		allReviews.add(r);
		len += 1;
	}
	
	public void printReviewList() {
		for (Review i : allReviews) {
			System.out.printf("   %2d  %s\n", i.getStars(), i.getText());
		}
	}
}
