package cs335_package;

public class ReviewObject {
    //private int num;  // If you plan to use num later, leave it, but if not, you can remove it.
    private int stars;
    private String text;
    private String reviewer;
    
    // Constructor with stars initialization
    public ReviewObject(int stars) {
        this.stars = stars;
        this.text = "";  // Initialize text as empty if no text is provided
        this.reviewer = "";
    }

    // Getters for stars and text
    public int getStars() {
        return this.stars;
    }

    public String getText() {
        return this.text;
    }

    // Adds text to the review
    public void addText(String text) {
        this.text = text;
    }
}
