package cs335_package;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.file.*;

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

	
	Location(String n, String t){
		this.name = n;
		this.type = t;
	}
	
	public String toString() {
		return("Name: "+this.name+
				"\nType: "+this.type+
				"\nLocation: "+this.location+
				"\nReservation: "+this.reserve+
				"\nCoat Check: "+this.coat+
				"\nCover: "+this.cover+
				"\nPrice: "+Double.toString(this.price)+
				"\nStars: "+Double.toString(this.stars)+
				"\nWebsite: "+this.web
				);
	}
	
	public String[] toArray() {
		String[] locArray = {this.name,this.type,this.location,this.reserve,this.coat,this.cover,
							Double.toString(this.price),Double.toString(this.stars),this.web};
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
	
	// main methods
	public void createReview() {
		// if user chooses to add review, stars must be entered
		// text review is optional
		
		Review r = new Review();
        r.addStars();

        boolean cont = true;
        while (cont) {
        	Scanner scan = new Scanner(System.in);
	        System.out.println("Do you want to add text to your review (yes/no)? ");
	        String addText = scan.nextLine().trim().toLowerCase();
	        if (addText.equals("yes")) {
	        	r.addText(); // might need to pass scanner scan as argument to avoid issues
	        	cont = false;
	        }else if (addText.equals("no")) {
	        	r.text = null;
	        	cont = false;
	        }else {
	        	System.out.print("Please enter yes or no");
	        }
	        scan.close();
        }
	        	
        System.out.println("Thank you for your review!");
        // Optionally store or display the review, here we just print it
        System.out.println("Your review: " + r.getStars() + " stars"); // does stars need to be converted to int?
        System.out.println("Review: " + r.getText());
        
        // add review to array list of reviews for particular location
        if (reviews.len==0) {
        	reviews = new ReviewList(); // creates new array list of reviews
        	reviews.addReview(r);
        }else { // array list already exists
        	reviews.addReview(r);
        }
	} // createReview method
	
	// public void showReviews() {}
	// 		this would be done using the method in the Reviews class
	
	//public Location createLocation() {
	//} // uses LocationFactory
	
	public void add2Wishlist() {
		String os = getOS(); // different os have different file structures
		String user = System.getProperty("user.name");
		if (os=="Windows") {
			try{
				Path wishlistFolder = Paths.get("C:/Users/"+user+"/routenroam/wishlistFolder");
				Files.createDirectories(wishlistFolder); // creates folder -- createDirectories should check if folder exists first
			} catch(IOException ex) {
				System.out.println("Unable to create folder");
			}
			File f = new File("C:/Users/"+user+"/routenroam/wishlist/wishlist.FILE");
			Path path = FileSystems.getDefault().getPath("C:\\Users"+user+"routenroam\\wishlist\\wishlist.FILE");
			if (Files.exists(path)) { // checks if file exists
				writeFile(f);				
			}else { // creates file
				f = createFile("wishlist","C:\\Users\\"+user+"\\routenroam\\wishlistFolder\\");
				writeFile(f);
			}
		}else if(os=="Linux") {
			try{
				Path wishlistFolder = Paths.get("C:/Users/"+user+"/routenroam/wishlistFolder");
				Files.createDirectories(wishlistFolder); // creates folder -- createDirectories should check if folder exists first
			}catch(IOException ex){
				System.out.println("Unable to create folder");
			}
			File f = new File("C:/Users/"+user+"/routenroam/wishlist/wishlist.FILE");
			Path path = FileSystems.getDefault().getPath("C:\\Users\\"+user+"\\routenroam\\wishlist\\wishlist.FILE");
			if (Files.exists(path)) { // checks if file exists
				writeFile(f); // save location to file		
			}else { // creates file
				f = createFile("wishlist","C:/Users/"+user+"/routenroam/wishlistFolder/");
				writeFile(f); // save location to file	
			}
		}else if(os=="MacOS") {
			try{
				Path wishlistFolder = Paths.get("/Users/"+user+"/routenroam/wishlistFolder");
				Files.createDirectories(wishlistFolder); // creates folder -- createDirectories should check if folder exists first
			}catch(IOException ex){
				System.out.println("Unable to create folder");
			}
			File f = new File("/Users/"+user+"/routenroam/wishlistFolder/wishlist.FILE");
			Path path = FileSystems.getDefault().getPath("/Users/"+user+"/routenroam/wishlist/wishlist.FILE");
			if (Files.exists(path)) { // checks if file exists
				writeFile(f); // save location to file		
			}else { // creates file
				f = createFile("wishlist","/Users/"+user+"/routenroam/wishlistFolder/");
				writeFile(f); // save location to file	
			}
		}else {
			System.out.println("Unable to save. Incompatible operating system.");
		}
	} // add2WishList method
		
	
	public void schedule() {}
		// same issue as add2Wishlist
	
	public String getOS() {
		String[] possibleSystems = {"Windows","Linux","MacOS"};
		String system = null; // OS used by user's computer
		boolean matchFound = false;
		for (String i:possibleSystems) { // goes through each os and breaks if match is found
			String os = System.getProperty("os.name"); // gets os on user's computer
			Pattern pattern = Pattern.compile(i, Pattern.CASE_INSENSITIVE); // pattern to be found
			Matcher match = pattern.matcher(os); // look for pattern in this string
			matchFound = match.find();
			if (matchFound) {
				system = i;
				break;
			}
		}
		if (matchFound) {
			return system; // returns system name
		}else{
			System.out.println("Compatible operating system not detected");
			return null;
		}
	} //getOS method
	
	public File createFile(String fileName,String filePath) {
		File file1;
		try {
			file1 = new File(filePath+fileName);
			file1.createNewFile();
		}catch(IOException ex){
			System.out.println("Failed to create file");
			file1 = null;
		}
		return file1;
	} // createFile method
	
	public void writeFile(File file1) {	
		try{
			FileWriter Writer = new FileWriter(file1);
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
		}catch(IOException ex) {
			System.out.print("Failed to save");
		}
	}
}
