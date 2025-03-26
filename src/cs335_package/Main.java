


/*package cs335_package;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Initialize Scanner here for Nada's code
        // Remove the Place initialization since it's causing errors and seems unused
        // Place place = new Place(); 
        String t = "yes";
        String s = "yes";
      
        // read in all csv files as Location objects
        String[] category = {"Night Out","Brunch","Cafes/Bakeries","Lunch/Dinner", "Shopping", "Fun Activities",
         "Outdoors","Spa","Skincare/Haircare","Massage"};
      
        //Initialize the categories with empty ArrayLists
        ArrayList<Location> nightOut = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> brunch = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> cafe = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> lunchDinner = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> shopping = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> fun = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> outdoors = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> selfCare = new ArrayList<>(); // Fixed initialization
        
        for (String x:category){
            PlacesFactory df;
            switch (x) {
                case "Night Out": 
                    nightOut = new ArrayList<Location>();
                    df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                    while (df.moreData()){
                        nightOut.add(df.getNextPlace());
                    }
                    break;
                case "Brunch": 
                    brunch = new ArrayList<Location>();
                    df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                    while (df.moreData()){
                        brunch.add(df.getNextPlace());
                    }
                    break;
                case "Cafes/Bakeries": 
                    cafe = new ArrayList<Location>();
                    df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                    while (df.moreData()){
                        cafe.add(df.getNextPlace());
                    }
                    break;
                case "Lunch/Dinner": 
                    lunchDinner = new ArrayList<Location>();
                    df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                    while (df.moreData()){
                        lunchDinner.add(df.getNextPlace());
                    }
                    break;
                case "Shopping": 
                    shopping = new ArrayList<Location>();
                    df = new PlacesFactory("places/toDo/"+getCsvFile(x));
                    while (df.moreData()){
                        shopping.add(df.getNextPlace());
                    }
                    break;
                case "Fun Activities": 
                    fun = new ArrayList<Location>();
                    df = new PlacesFactory("places/toDo/"+getCsvFile(x));
                    while (df.moreData()){
                        fun.add(df.getNextPlace());
                    }
                    break;
                case "Outdoors": 
                    outdoors = new ArrayList<Location>();
                    df = new PlacesFactory("places/toDo/"+getCsvFile(x));
                    while (df.moreData()){
                        outdoors.add(df.getNextPlace());
                    }
                    break;
                default: 
                    selfCare = new ArrayList<Location>();
                    df = new PlacesFactory("places/selfCare/"+getCsvFile(x));
                    while (df.moreData()){
                        selfCare.add(df.getNextPlace());
                    }
                    break;
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
                    // Renamed variable to avoid conflict with the removed Place object
                    Location currentPlace = null;
                    if (s.equals("Night Out")) {
                        currentPlace = nightOut.get(locIndex);
                    }else if(s.equals("Brunch") ) { 
                        currentPlace = brunch.get(locIndex);
                    }else if(s.equals("Cafes/Bakeries") ){
                        currentPlace = cafe.get(locIndex);
                    }else if(s.equals("Lunch/Dinner") ){
                        currentPlace = lunchDinner.get(locIndex);
                    }else if(s.equals("Shopping") ){
                        currentPlace = shopping.get(locIndex);
                    }else if(s.equals("Fun Activities") ){
                        currentPlace = fun.get(locIndex);
                    }else if(s.equals("Outdoors")) {
                        currentPlace = outdoors.get(locIndex);
                    }else {
                        currentPlace = selfCare.get(locIndex);        
                    }
                    locIndex += 1;
                    System.out.print(currentPlace);

                    System.out.println("\n----------------------");
                
                    System.out.println("----------------------");
                    // Ask if the user wants to leave a review
                    System.out.print("Would you like to leave a review for this place? (yes/no): ");
                    String leaveReview = sc.nextLine().trim().toLowerCase();
                    // If yes, ask for rating and a text review
                    if (leaveReview.equals("yes")) {
                        int rating = currentPlace.getRating(sc);
                        System.out.print("Enter your review: ");
                        String reviewText = sc.nextLine().trim();
                        System.out.println("Thank you for your review!");
                        // Store the review and save it to CSV
                        try {
                            currentPlace.saveReviewToCSV(rating, reviewText);
                            System.out.println("Your review has been saved!");
                        } catch (IOException e) {
                            System.out.println("Error saving review to CSV: " + e.getMessage());
                        }
                        // Display the review
                        System.out.println("Your review: " + rating + " stars");
                        System.out.println("Review: " + reviewText);
                    }

                    System.out.println("----------------------");
                    System.out.println("----------------------");
                    System.out.println("----------------------");
                    
                    System.out.println("Do you want to save this location to your wishlist (yes/no)? ");
                    String wish = sc.nextLine().trim().toLowerCase();
                    while (!wish.equals("yes") && !wish.equals("no")){
                        System.out.println("Please enter yes or no: ");
                        wish = sc.nextLine().trim().toLowerCase();
                    }
                    if (wish.equals("yes")) {
                        // Ask about scheduling first, before adding to wishlist
                        System.out.println("Would you like to schedule a time to visit " + currentPlace.getName() + "? (yes/no)");
                        String scheduleChoice = sc.nextLine().trim().toLowerCase();
                        
                        if (scheduleChoice.equals("yes")) {
                            // Using the scheduleVisit method which will handle both scheduling and adding to wishlist
                            place.scheduleVisit(sc);
                        } else {
                            // Add to wishlist without scheduling
                            place.add2Wishlist();
                            System.out.println("Location added to wishlist without scheduling.");
                        }
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
						System.out.print("GOODBYE");
						break; }}}}}// Exit the loop if the user types 'Exit'
                    
					
    
	
	
	//dont mind this. iw as trying to see if i could mkae it into a variable. we might just rmeove this. 
	/*private static String getCsvFileForPlace(Location place, ArrayList<Location> nightOut, 
                                        ArrayList<Location> brunch, ArrayList<Location> cafe,
                                        ArrayList<Location> lunchDinner, ArrayList<Location> shopping,
                                        ArrayList<Location> fun, ArrayList<Location> outdoors,
                                        ArrayList<Location> selfCare) {
		    // Check each category list and return the appropriate CSV filename
		    if (nightOut.contains(place)) {
		        return "Copy of Boston Recommendations - Going out Bars.csv";
		    } else if (brunch.contains(place)) {
		        return "Boston Recommendations - Brunch.csv";
		    } else if (cafe.contains(place)) {
		        return "Copy of Boston Recommendations - Coffee Shops _ Bakery.csv";
		    } else if (lunchDinner.contains(place)) {
		        return "Copy of Boston Recommendations - Restaurants.csv";
		    } else if (shopping.contains(place)) {
		        return "Copy of Boston Recommendations - Shopping.csv";
		    } else if (fun.contains(place)) {
		        return "Copy of Boston Recommendations - Things To Do.csv";
		    } else if (outdoors.contains(place)) {
		        return "Copy of Boston Recommendations - Summer _ Outdoor.csv";
		    } else if (selfCare.contains(place)) {
		        return "Copy of Boston Recommendations - Spa_Beauty.csv";
		    }
		    return ""; // Default case when no category matches
		}*/

	

	
/* 
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
	} 
}
	*/
	
	




    package cs335_package;
    import java.io.IOException;
    import java.util.*;
    
    public class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in); // Initialize Scanner here for Nada's code
            String t = "yes";
            String s = "yes";
          
            // read in all csv files as Location objects
            String[] category = {"Night Out","Brunch","Cafes/Bakeries","Lunch/Dinner", "Shopping", "Fun Activities",
             "Outdoors","Spa","Skincare/Haircare","Massage"};
          
            //Initialize the categories with empty ArrayLists
            ArrayList<Location> nightOut = new ArrayList<>(); // Fixed initialization
            ArrayList<Location> brunch = new ArrayList<>(); // Fixed initialization
            ArrayList<Location> cafe = new ArrayList<>(); // Fixed initialization
            ArrayList<Location> lunchDinner = new ArrayList<>(); // Fixed initialization
            ArrayList<Location> shopping = new ArrayList<>(); // Fixed initialization
            ArrayList<Location> fun = new ArrayList<>(); // Fixed initialization
            ArrayList<Location> outdoors = new ArrayList<>(); // Fixed initialization
            ArrayList<Location> selfCare = new ArrayList<>(); // Fixed initialization
            
            for (String x:category){
                PlacesFactory df;
                switch (x) {
                    case "Night Out": 
                        nightOut = new ArrayList<Location>();
                        df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                        while (df.moreData()){
                            nightOut.add(df.getNextPlace());
                        }
                        break;
                    case "Brunch": 
                        brunch = new ArrayList<Location>();
                        df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                        while (df.moreData()){
                            brunch.add(df.getNextPlace());
                        }
                        break;
                    case "Cafes/Bakeries": 
                        cafe = new ArrayList<Location>();
                        df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                        while (df.moreData()){
                            cafe.add(df.getNextPlace());
                        }
                        break;
                    case "Lunch/Dinner": 
                        lunchDinner = new ArrayList<Location>();
                        df = new PlacesFactory("places/restaurants/"+getCsvFile(x));
                        while (df.moreData()){
                            lunchDinner.add(df.getNextPlace());
                        }
                        break;
                    case "Shopping": 
                        shopping = new ArrayList<Location>();
                        df = new PlacesFactory("places/toDo/"+getCsvFile(x));
                        while (df.moreData()){
                            shopping.add(df.getNextPlace());
                        }
                        break;
                    case "Fun Activities": 
                        fun = new ArrayList<Location>();
                        df = new PlacesFactory("places/toDo/"+getCsvFile(x));
                        while (df.moreData()){
                            fun.add(df.getNextPlace());
                        }
                        break;
                    case "Outdoors": 
                        outdoors = new ArrayList<Location>();
                        df = new PlacesFactory("places/toDo/"+getCsvFile(x));
                        while (df.moreData()){
                            outdoors.add(df.getNextPlace());
                        }
                        break;
                    default: 
                        selfCare = new ArrayList<Location>();
                        df = new PlacesFactory("places/selfCare/"+getCsvFile(x));
                        while (df.moreData()){
                            selfCare.add(df.getNextPlace());
                        }
                        break;
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
                        // Renamed variable to avoid conflict with the removed Place object
                        Location currentPlace = null;
                        if (s.equals("Night Out")) {
                            currentPlace = nightOut.get(locIndex);
                        }else if(s.equals("Brunch") ) { 
                            currentPlace = brunch.get(locIndex);
                        }else if(s.equals("Cafes/Bakeries") ){
                            currentPlace = cafe.get(locIndex);
                        }else if(s.equals("Lunch/Dinner") ){
                            currentPlace = lunchDinner.get(locIndex);
                        }else if(s.equals("Shopping") ){
                            currentPlace = shopping.get(locIndex);
                        }else if(s.equals("Fun Activities") ){
                            currentPlace = fun.get(locIndex);
                        }else if(s.equals("Outdoors")) {
                            currentPlace = outdoors.get(locIndex);
                        }else {
                            currentPlace = selfCare.get(locIndex);        
                        }
                        locIndex += 1;
                        System.out.print(currentPlace);
    
                        System.out.println("\n----------------------");
                    
                        System.out.println("----------------------");
                        System.out.println("----------------------");
                        System.out.println("----------------------");
                        System.out.println("----------------------");
                        // Ask if the user wants to leave a review
                        System.out.print("Would you like to leave a review for this place? (yes/no): ");
                        String leaveReview = sc.nextLine().trim().toLowerCase();
                        // If yes, ask for rating and a text review
                        if (leaveReview.equals("yes")) {
                            int rating = currentPlace.getRating(sc);
                            System.out.print("Enter your review: ");
                            String reviewText = sc.nextLine().trim();
                            System.out.println("Thank you for your review!");
                            
                            // Store the review and save it to CSV - with IOException handling
                            try {
                                currentPlace.saveReviewToCSV(rating, reviewText);
                                System.out.println("Your review has been saved!");
                            } catch (IOException e) {
                                System.out.println("Error saving review to CSV: " + e.getMessage());
                            }
                            
                            // Display the review
                            System.out.println("Your review: " + rating + " stars");
                            System.out.println("Review: " + reviewText);
                        }
    
                        System.out.println("----------------------");
                        System.out.println("----------------------");
                        System.out.println("----------------------");
                        
                        System.out.println("Do you want to save this location to your wishlist (yes/no)? ");
                        String wish = sc.nextLine().trim().toLowerCase();
                        while (!wish.equals("yes") && !wish.equals("no")){
                            System.out.println("Please enter yes or no: ");
                            wish = sc.nextLine().trim().toLowerCase();
                        }
                        if (wish.equals("yes")) {
                            // Ask about scheduling first, before adding to wishlist
                            System.out.println("Would you like to schedule a time to visit " + currentPlace.getName() + "? (yes/no)");
                            String scheduleChoice = sc.nextLine().trim().toLowerCase();
                            
                            if (scheduleChoice.equals("yes")) {
                                // Using the scheduleVisit method which will handle both scheduling and adding to wishlist
                                // Removed try-catch since scheduleVisit doesn't throw IOException
                                currentPlace.scheduleVisit(sc);
                            } else {
                                // Add to wishlist without scheduling
                                try {
                                    currentPlace.add2Wishlist();
                                    System.out.println("Location added to wishlist without scheduling.");
                                } catch (IOException e) {
                                    System.out.println("Error adding to wishlist: " + e.getMessage());
                                }
                            }
                        }
                        
                        System.out.println("----------------------");
                        System.out.println("----------------------");
                        System.out.println("----------------------");
    
                        // Ask the ur if they want to see the next place or stop
                        System.out.print("Type 'Next' to see the next place or 'Exit' to stop: ");
                        String input = sc.nextLine().trim().toLowerCase();  // Convert input to lowercase to avoid case issues
                        // Loop until user types "next" or "exit"
                        while (!input.equals("next") && !input.equals("exit")) {
                            // Just keep asking the user without showing the "Invalid input" message
                            input = sc.nextLine().trim().toLowerCase();  // Keep reading until "Next" or "Exit" is typed
                        }
                        if (input.equals("exit")) {
                            System.out.print("GOODBYE");
                            break; 
                        }
                    }
                }
            }
        }
    
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
        }
    }
    