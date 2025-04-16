
    package cs335_package;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.Scanner;

    import javax.swing.JOptionPane; 
    public class Main {
        public static void main(String[] args) {
            JOptionPane.showMessageDialog(null, "Welcome to Roam & Root!", "Roam & Root", JOptionPane.INFORMATION_MESSAGE);
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
                                               // System.out.println("DEBUG: About to ask for review..."); // <-- DEBUG LINE
                                                System.out.print("Would you like to leave a review for this place? (yes/no): ");
                                                String leaveReview = sc.nextLine().trim().toLowerCase();
                                               // System.out.println("DEBUG: User entered: '" + leaveReview + "'"); // <-- DEBUG LINE
                        
                                                // If yes, show the review dialog
                                                if (leaveReview.equals("yes")) {
                                               //     System.out.println("DEBUG: User entered 'yes'. Creating ReviewDialog..."); // <-- DEBUG LINE
                                                    // --- Use the GUI Dialog for Review ---
                                                    // Pass null as the owner frame if you don't have a main JFrame
                                                    ReviewDialog reviewDialog = new ReviewDialog(null, currentPlace.getName());
                                                   // System.out.println("DEBUG: ReviewDialog created. Calling setVisible(true)..."); // <-- DEBUG LINE
                                                    reviewDialog.setVisible(true); // Show the dialog and wait
                                                   // System.out.println("DEBUG: ReviewDialog closed."); // <-- DEBUG LINE
                        
                                                    // Check if the user submitted the review
                                                    if (reviewDialog.isSubmitted()) {
                                                        //System.out.println("DEBUG: Review was submitted."); // <-- DEBUG LINE
                                                        int rating = reviewDialog.getRating();
                                                        String reviewText = reviewDialog.getReviewText();
                        
                                                        System.out.println("Thank you for your review!");
                        
                                                        // Store the review and save it to CSV - with IOException handling
                                                        try {
                                                           
                                                            currentPlace.saveReviewToCSV(rating, reviewText);
                                                            System.out.println("Your review has been saved!");
                                                        } catch (IOException e) {
                                                            System.out.println("Error saving review to CSV: " + e.getMessage());
                                                        }
                        
                                                        // Display the review (optional, could be removed)
                                                        System.out.println("Your review: " + rating + " stars");
                                                        System.out.println("Review: " + reviewText);
                        
                                                    } else {
                                                       // System.out.println("DEBUG: Review was cancelled."); // <-- DEBUG LINE
                                                       // System.out.println("Review cancelled by user.");
                                                    }
                                                } else {
                                                     System.out.println("DEBUG: User did not enter 'yes'. Skipping review dialog."); // <-- DEBUG LINE
                                                } // End if leaveReview.equals("yes")
                        
                                                System.out.println("----------------------"); // <<< This separator should remain
                                                System.out.println("----------------------"); // <<< This separator should remain
                                                System.out.println("----------------------"); // <<< This separator should remain
                        
                                                // Add these imports at the top of your Main.java file if they aren't already there:
                                                // import java.util.Date;
                                                // import javax.swing.JFrame; // Needed if you pass 'null' to ScheduleDialog
                        
                                                System.out.println("Do you want to save this location to your wishlist (yes/no)? "); // <<< Wishlist part starts here
                                         
                        
                        String wish = sc.nextLine().trim().toLowerCase();
                        while (!wish.equals("yes") && !wish.equals("no")){
                            System.out.println("Please enter yes or no: ");
                            wish = sc.nextLine().trim().toLowerCase();
                        }

                                                if (wish.equals("yes")) {
                            // Ask about scheduling first
                            System.out.println("Would you like to schedule a time to visit " + currentPlace.getName() + "? (yes/no)");
                            String scheduleChoice = sc.nextLine().trim().toLowerCase();

                            if (scheduleChoice.equals("yes")) {
                                // --- Use the GUI Dialog for Scheduling ---
                                ScheduleDialog dialog = new ScheduleDialog(null, currentPlace.getName());
                                dialog.setVisible(true); // This blocks until the dialog is closed

                                // Check if the user clicked OK and provided valid dates
                                if (dialog.isConfirmed()) {
                                    Date startDate = dialog.getSelectedStartDate();
                                    Date endDate = dialog.getSelectedEndDate();

                                    // Call the new scheduleVisit method in Location
                                    currentPlace.scheduleVisit(startDate, endDate);
                                    // Note: scheduleVisit(Date, Date) handles adding to wishlist on success.

                                } else { // Dialog was cancelled
                                    System.out.println("Scheduling cancelled by user.");
                                    // Optionally, ask if they still want to add to wishlist without schedule
                                    System.out.println("Do you still want to add " + currentPlace.getName() + " to your wishlist without scheduling? (yes/no)");
                                    String addToWishlistAnyway = sc.nextLine().trim().toLowerCase();
                                    if (addToWishlistAnyway.equals("yes")) {
                                        try {
                                            currentPlace.add2Wishlist(); // Add to wishlist directly
                                            System.out.println("Location added to wishlist without scheduling.");
                                        } catch (IOException e) {
                                            System.out.println("Error adding to wishlist: " + e.getMessage());
                                        }
                                    }
                                } // End of else (dialog cancelled)

                            } else { // User answered "no" to scheduling initially
                                // Add to wishlist without scheduling
                                try {
                                    currentPlace.add2Wishlist(); // Add to wishlist directly
                                    System.out.println("Location added to wishlist without scheduling.");
                                } catch (IOException e) {
                                    System.out.println("Error adding to wishlist: " + e.getMessage());
                                }
                            } // End of else (scheduleChoice was "no")

                        } // End of if (wish.equals("yes"))

                               
                    
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
                            JOptionPane.showMessageDialog(null, "Thanks for using Roam and Root!\nWe hope to see you again 🌿", "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
                            break; 
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
    