package cs335_package;
import java.util.*;

public class Main {
	public static void main(String [] args){
		Scanner sc = new Scanner(System.in); // Initialize Scanner here for Nada's code
        String t = "yes";
        String s = "yes";
      
        // read in all csv files as Location objects
        String[] category = {"Night Out","Brunch","Cafes/Bakeries","Lunch/Dinner", "Shopping", "Fun Activities",
    		    			 "Outdoors","Spa","Skincare/Haircare","Massage"};
        ArrayList<Location> nightOut = null;
        ArrayList<Location> brunch = null;
        ArrayList<Location> cafe = null;
        ArrayList<Location> lunchDinner = null;
        ArrayList<Location> shopping = null;
        ArrayList<Location> fun = null;
        ArrayList<Location> outdoors = null;
        ArrayList<Location> selfCare = null;
        for (String x:category){
        	PlacesFactory df;
            switch (x) {
         		case "Night Out": 
         			nightOut = new ArrayList<Location>();
         			df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
         			while (df.moreData()){
         				nightOut.add(df.getNextPlace());
         			}break;
         		case "Brunch": 
         			brunch = new ArrayList<Location>();
         			df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
         			while (df.moreData()){
         				brunch.add(df.getNextPlace());
         			}break;
	         	case "Cafes/Bakeries": 
	         		cafe = new ArrayList<Location>();
	         		df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
	         		while (df.moreData()){
	                    cafe.add(df.getNextPlace());
	                }break;
	         	case "Lunch/Dinner": 
	         		lunchDinner = new ArrayList<Location>();
	         		df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
	         		while (df.moreData()){
	                    lunchDinner.add(df.getNextPlace());
	                }break;
	         	case "Shopping": 
	         		shopping = new ArrayList<Location>();
	         		df = new PlacesFactory("places/toDo/"+getCsvFile(x));
	         		while (df.moreData()){
	                    shopping.add(df.getNextPlace());
	                }break;
	         	case "Fun Activities": 
	         		fun = new ArrayList<Location>();
	         		df = new PlacesFactory("places/toDo/"+getCsvFile(x));
	         		while (df.moreData()){
	                    fun.add(df.getNextPlace());
	                }break;
	         	case "Outdoors": 
	         		outdoors = new ArrayList<Location>();
	         		df = new PlacesFactory("places/toDo/"+getCsvFile(x));
	         		while (df.moreData()){
	                    outdoors.add(df.getNextPlace());
	                }break;
	         	default: 
	         		selfCare = new ArrayList<Location>();
	         		df = new PlacesFactory("places/selfCare/"+getCsvFile(x));
	         		while (df.moreData()){
	                    selfCare.add(df.getNextPlace());
	                }break;
            } 
        }
        
    	int locIndex = 0; // keeps track of where in the locations arrayList the user is
	    Menu m = new Menu();
        s = m.Menu();
        while(t.equals("yes") && s!=null){

	        if(s!=null){
	            System.out.println("");
		          
		        while(s.equals("Go Back")){
		        	s=m.Menu();           
		        }
		        
		        if(!s.isEmpty()){
		    	    System.out.println("");
		    	    Location place = null;
		    	    if (s=="Night Out") {
		    	    	place = nightOut.get(locIndex);
		    	    }else if(s=="Brunch") {
		    	    	place = brunch.get(locIndex);
		    	    }else if(s=="Cafe/Bakeries") {
		    	    	place = cafe.get(locIndex);
		    	    }else if(s=="Lunch/Dinner") {
		    	    	place = lunchDinner.get(locIndex);
		    	    }else if(s=="Shopping") {
		    	    	place = shopping.get(locIndex);
		    	    }else if(s=="Fun Activities") {
		    	    	place = fun.get(locIndex);
		    	    }else if(s=="Outdoors") {
		    	    	place = outdoors.get(locIndex);
		    	    }else {
		    	    	place = selfCare.get(locIndex);    	    
		    	    }
		    	    locIndex =+ 1;
		    	    System.out.print(place);

			        System.out.println("\n----------------------");
			            
			        // Ask if the user wants to leave a review
			        System.out.print("Would you like to leave a review for this place (yes/no)? ");
			        String leaveReview = sc.nextLine().trim().toLowerCase();
			        // If yes, get rating
			        if (leaveReview.equals("yes")) {
			    	    place.createReview();
			        }
                     
			        System.out.println("----------------------");
			        
			        // Ask the user if they want to add to wishlist
			        System.out.println("Do you want to save this location to your wishlist (yes/no)? ");
			        String wish = sc.nextLine().trim().toLowerCase();
			        while (!wish.equals("yes") && !wish.equals("no")){
			        	System.out.println("Please enter yes or no: ");
			        	wish = sc.nextLine().trim().toLowerCase();
			        }
			        if (wish.equals("yes")) {
			        	place.add2Wishlist();
			        }
			        
			        System.out.println("----------------------");
			        
			        // Ask the user if they want to see the next place or stop
			        System.out.print("Type 'Next' to see the next place or 'Exit' to stop: ");
			        String input = sc.nextLine().trim().toLowerCase();  // Convert input to lowercase to avoid case issues
			        // Loop until user types "next" or "exit"
			        while (!input.equals("next") && !input.equals("exit")) {
			    	    // Just keep asking the user without showing the "Invalid input" message
			            input = sc.nextLine().trim().toLowerCase();  // Keep reading until "Next" or "Exit" is typed
			        }
			        if (input.equals("exit")) {
			    	    break; // Exit the loop if the user types 'Exit'
			        }
		        }
	        }
        }
        
	  // Ask if the user wants to explore other categories
	  Scanner sc1=new Scanner(System.in);
	  System.out.println("Would you like to checkout other places and categories (yes/no)? ");
	  t=sc1.nextLine();
	  
	} // main method
    
	private static String getCsvFile(String category) {
        switch (category) {
            case "Night Out": return "Copy of Boston Recommendations  - Going out Bars.csv";
            case "Brunch": return "Boston Recommendations  - Brunch.csv";
            case "Cafes/Bakeries": return "Copy of Boston Recommendations  - Coffee Shops _ Bakery.csv";
            case "Lunch/Dinner": return "Copy of Boston Recommendations  - Restaurants.csv";

            case "Shopping": return "Copy of Boston Recommendations  - Shopping.csv";
            case "Fun Activities": return "Copy of Boston Recommendations  - Things To Do.csv";
            case "Outdoors": return "Copy of Boston Recommendations  - Summer _ Outdoor.csv";

            case "Spa": return "Copy of Boston Recommendations  - Spa_Beauty.csv";
            case "Skincare/Haircare": return "Copy of Boston Recommendations  - Spa_Beauty.csv";
            case "Massage": return "Copy of Boston Recommendations  - Spa_Beauty.csv";

            default: return "";
        }
	} // getCsvFile method
}
