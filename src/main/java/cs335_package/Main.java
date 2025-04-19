<<<<<<< HEAD

    package cs335_package;
    import java.io.IOException;
    import java.io.FileWriter;

    import java.util.Scanner;
    import java.util.*;

    import cs335_package.SearchYelp; 
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
            ArrayList<Location> selfCare = new ArrayList<>();
            ArrayList<Location> other = new ArrayList<>(); // Fixed initialization
            
           
            int locIndex = 0; // keeps track of where in the locations arrayList the user is
            Menu m = new Menu();
            s = m.Menu();
            if(!s.matches("")) {
            SearchYelp.initiateSearch(s); 
           
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
                    case "Spa":
                    case "Massage":
                    case "Skincare/Haircare":
                        selfCare = new ArrayList<Location>();
                        df = new PlacesFactory("places/selfCare/" + getCsvFile(x));
                        while (df.moreData()) {
                            selfCare.add(df.getNextPlace());
                        }
                        break;
                    default: 
                        other = new ArrayList<Location>();
                        df = new PlacesFactory("places/"+getCsvFile(x));
                        while (df.moreData()){
                            other.add(df.getNextPlace());
                        }
                        break;
                } 
            }
            }
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
                        }else if (s.equals("Spa") || s.equals("Massage") || s.equals("Skincare/Haircare")) { 
                            currentPlace = selfCare.get(locIndex);        
                        } else { currentPlace = other.get(locIndex);  }
                        
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
    
        public static String getCsvFile(String category) {
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
    
                default: return "GeneralRecommendations.csv"; // Fallback file
            }
        }
        public static void createGeneralUserChoiceCSV(String userChoice) {
            try (FileWriter writer = new FileWriter("places/usersChoice/GeneralUserChoice.csv")) {
                // Write a header or any initial content to the CSV if needed
                writer.write("User Choice\n"); // Example header
                writer.write(userChoice); // Write the user's choice to the CSV
                System.out.println("GeneralUserChoice.csv created/overwritten successfully.");
            } catch (IOException e) {
                System.out.println("Error creating/overwriting GeneralUserChoice.csv: " + e.getMessage());
            }}
    }
=======
package cs335_package;
import java.io.IOException;
import java.io.FileWriter;
import java.util.*;
import cs335_package.SearchYelp;

public class Main {
	public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in); // Initialize Scanner here for Nada's code
        String t = "yes";
        String s = "yes";
        
        String[] category = {"Night Out","Brunch","Cafes/Bakeries","Lunch/Dinner", "Shopping", "Fun Activities",
        					 "Outdoors","Spa","Skincare/Haircare","Massage","Other"};
          
        // Initialize the categories with empty ArrayLists
        ArrayList<Location> nightOut = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> brunch = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> cafe = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> lunchDinner = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> shopping = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> fun = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> outdoors = new ArrayList<>(); // Fixed initialization
        ArrayList<Location> selfCare = new ArrayList<>();
        ArrayList<Location> other = new ArrayList<>(); // Fixed initialization
        
        
        int locIndex = 0; // keeps track of where in the locations arrayList the user is
        Menu m = new Menu();
        s = m.Menu();
        if(!s.matches("")) {
            SearchYelp.initiateSearch(s);
	        // read in all csv files as Location objects
	        for (String x:category){
	            PlacesFactory df;
	            switch (x) {
	                case "Night Out":
	                	int rowIndex = 1;
	                	String filePath = "places/restaurants/"+getCsvFile(x);
	                    nightOut = new ArrayList<Location>();    
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        nightOut.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                case "Brunch":
	                	rowIndex = 1;
	                	filePath = "places/restaurants/"+getCsvFile(x);
	                    brunch = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        brunch.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                case "Cafes/Bakeries":
	                	rowIndex = 1;
	                	filePath = "places/restaurants/"+getCsvFile(x);
	                    cafe = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        cafe.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                case "Lunch/Dinner":
	                	rowIndex = 1;
	                	filePath = "places/restaurants/"+getCsvFile(x);
	                    lunchDinner = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        lunchDinner.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                case "Shopping":
	                	rowIndex = 1;
	                	filePath = "places/toDo/"+getCsvFile(x);
	                    shopping = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        shopping.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                case "Fun Activities":
	                	rowIndex = 1;
	                	filePath = "places/toDo/"+getCsvFile(x);
	                    fun = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        fun.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                case "Outdoors":
	                	rowIndex = 1;
	                	filePath = "places/toDo/"+getCsvFile(x);
	                    outdoors = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        outdoors.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                case "Spa":
	                case "Massage":
	                case "Skincare/Haircare":
	                	rowIndex = 1;
	                	filePath = "places/selfCare/" + getCsvFile(x);
	                    selfCare = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()) {
	                        selfCare.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	                default:
	                	rowIndex = 1;
	                	filePath = "places/"+getCsvFile(x);
	                    other = new ArrayList<Location>();
	                    df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        other.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    break;
	            } // switch statement
	        } // for loop
        }
          
        while(t.equals("yes") && !s.equals("")){
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
                    }else if (s.equals("Spa") || s.equals("Massage") || s.equals("Skincare/Haircare")) { 
                        currentPlace = selfCare.get(locIndex);        
                    }else {currentPlace = other.get(locIndex);}
                    
                    locIndex += 1;
                    System.out.print(currentPlace);

                    printLns();
                    
                    System.out.println("\nWhat do you want to do?");
                    System.out.println("1. Leave a review");
                    System.out.println("2. Read reviews");
                    System.out.println("3. Add to wish list");
                    System.out.println("4. Schedule a visit");
                    System.out.println("5. See next location");
                    System.out.println("6. Return to main menu");
                                        
                    int choice = GuiChooseMenu.getUserChoice();
                  
                    while (choice!=5&&choice!=6) { // keep going until the user chooses to exit this location type                            
                        if (choice==1) {
                        	Review r = new Review();
                    		r.createReview(sc);
                    		try {
                                currentPlace.saveReviewToCSV(r);
                                currentPlace.updateStars(r);
                                System.out.println("Your review has been saved!");
                            } catch (IOException e) {
                                System.out.println("Error saving review to CSV: " + e.getMessage());
                            }  
                    		printLns();;
                        }else if (choice==2) {
                        	currentPlace.readReviews();
                        	printLns();
                        } else if (choice==3) {
                    		try {
                                currentPlace.add2Wishlist();
                            } catch (IOException e) {
                                System.out.println("Error adding to wishlist: " + e.getMessage());
                            }
                    		printLns();
                        } else {
                    		currentPlace.scheduleVisit(sc);
                    		printLns();
                        }
                        
                        System.out.println("\nWhat do you want to do?");
                        System.out.println("1. Leave a review");
                        System.out.println("2. Read reviews");
                        System.out.println("3. Add to wish list");
                        System.out.println("4. Schedule a visit");
                        System.out.println("5. See next location");
                        System.out.println("6. Return to main menu");
                        System.out.println("Enter your choice: ");                           
                        choice = GuiChooseMenu.getUserChoice();
                        
                    } // while loop
                    if (choice==6) {
                    	s = "Go Back";
                    }
                }
            }
        } // while loop
    } // main function
    
    public static String getCsvFile(String category) {
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

            default: return "GeneralRecommendations.csv"; // Fallback file
        }
    } // getCsvFile function
    
    public static void createGeneralUserChoiceCSV(String userChoice) {
        try (FileWriter writer = new FileWriter("places/GeneralUserChoice.csv")) {
            // Write a header or any initial content to the CSV if needed
            //writer.write("User Choice\n"); // Example header
            writer.write(userChoice); // Write the user's choice to the CSV
            System.out.println("GeneralUserChoice.csv created/overwritten successfully.");
        } catch (IOException e) {
            System.out.println("Error creating/overwriting GeneralUserChoice.csv: " + e.getMessage());
        }
    } // createGeneralUserChoiceCSV function
    
    public static void printLns() {
    	System.out.println("\n----------------------");
        System.out.println("----------------------");
        System.out.println("----------------------");
    } // printLns function
    
} // Main class
>>>>>>> f45e573 (Add working GUI and backend integration)
    