package cs335_package;

public class ReviewObject {
	private int num;
	private int stars;
	private String text;
	
	ReviewObject(int s){
		this.stars = s;
	}
	
	public int getStars() {return this.stars;}
	public String getText() {return this.text;}
	
	public void addText(String t) {
		this.text = t;
	}
}
