package cs335_package;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;


public class Main {
private static String loggedInUsername = null; // Store the logged-in username -nada 

public static void main(String[] args) {

                // Load the logo image
                ImageIcon logoIcon = new ImageIcon("/Users/nadahijou/Downloads/C.png"); //this would mean the logo is located in the same place mine was. so 
                // Create and show the custom WelcomeDialog
                WelcomeDialog welcomeDialog = new WelcomeDialog(null, "Welcome to Roam and Root!", "Welcome", logoIcon);
                welcomeDialog.setVisible(true); // This will block until the dialog is closed by clicking

                

// --- Authentication Flow --- added by nada //https://www.youtube.com/watch?v=gBtuj_MjgtY&themeRefresh=1 soruce 
UserManager userManager = new UserManager();
boolean isAuthenticated = false;
while (!isAuthenticated) {
AuthDialog authDialog = new AuthDialog(null);
authDialog.setVisible(true);

String username = authDialog.getEnteredUsername();
String password = authDialog.getEnteredPassword();
boolean isLogin = authDialog.isLoginAttempt();

if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
continue; // Show dialog again
}

if (isLogin) {
User user = userManager.loginUser(username, password);
if (user != null) {
loggedInUsername = user.getUsername();
isAuthenticated = true;
JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + loggedInUsername + "!");
} else {
JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.");
}
} else {
if (userManager.registerUser(username, password)) {
JOptionPane.showMessageDialog(null, "Registration successful! You can now log in.");
} else {
JOptionPane.showMessageDialog(null, "Registration failed. Username already exists.");
}
}
}
// --- End Authentication Flow ---


//JOptionPane.showMessageDialog(null, "Welcome to Roam and Root!");
newGenRecs(); // clears General Recommendation csv
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


        // --- Data Loading for all categories (Moved outside the main loop) ---
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
                    df.close(); // Close scanner after reading
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
                    df.close(); // Close scanner after reading
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
                    df.close(); // Close scanner after reading
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
                    df.close(); // Close scanner after reading
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
                    df.close(); // Close scanner after reading
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
                    df.close(); // Close scanner after reading
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
                    df.close(); // Close scanner after reading
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
                    df.close(); // Close scanner after reading
                    break;
                default:
                    // The "Other" category is populated by Yelp search later, so no need to load here
                    break;
            } // switch statement
        } // for loop
        // --- End of Data Loading ---


        int locIndex = 0; // keeps track of where in the locations arrayList the user is

        // Main application loop
        while(true){ // Changed to an infinite loop, will exit when frame is closed or exit button is pressed
             s = GuiMainMenu.getUserChoice(); // Get user choice from the main menu

             if (s == null || s.isEmpty()) {
                 // User closed the main menu or clicked Exit
                 break; // Exit the main application loop
             }

            if(s.equals("Go Back")){
                locIndex = 0; // Reset index when going back to main menu
                continue; // Go back to the start of the loop to show the main menu again
            }

            if (s.equals("VIEW_WISHLIST")) {
                System.out.println("User selected VIEW_WISHLIST. Opening WishlistViewerGUI for user: " + loggedInUsername);
                // Open the WishlistViewerGUI for the logged-in user
                WishlistViewerGUI wishlistViewer = new WishlistViewerGUI(null, loggedInUsername);
                wishlistViewer.setVisible(true);

                // Add a small delay to allow the dialog to fully close visually
                try {
                    Thread.sleep(100); // Increased sleep to 100 milliseconds
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.out.println("WishlistViewerGUI closed.");
                continue; // Go back to the start of the loop to show the main menu again
            }


            // This  handles category selection and subsequent location display
            if(!s.equals("VIEW_WISHLIST") && !s.equals("VIEW_ALL_RESTAURANTS") && !s.equals("Go Back") && !s.isEmpty()) {
                 System.out.println("User selected category: " + s + ". Initiating Yelp search.");
                 newGenRecs(); // Clear previous search results
                 SearchYelp.initiateSearch(s); // Perform new search

                 // Re-load 'other' category with new search results
                 int rowIndex = 1;
                 String filePath = "places/GeneralRecommendations.csv";
                 other = new ArrayList<Location>();
                 PlacesFactory df = new PlacesFactory(filePath);
                 while (df.moreData()){
                     other.add(df.getNextPlace(rowIndex,filePath));
                     rowIndex += 1;
                 }
                 df.close(); // Close the PlacesFactory scanner


                 // Now display locations for the selected category
                 Location currentPlace = null;
                 ArrayList<Location> currentCategoryList = null;

                 if (s.equals("Night Out")) {
                     currentCategoryList = nightOut;
                 } else if(s.equals("Brunch") ) {
                     currentCategoryList = brunch;
                 } else if(s.equals("Cafes/Bakeries") ){
                     currentCategoryList = cafe;
                 } else if(s.equals("Lunch/Dinner") ){
                     currentCategoryList = lunchDinner;
                 } else if(s.equals("Shopping") ){
                     currentCategoryList = shopping;
                 } else if(s.equals("Fun Activities") ){
                     currentCategoryList = fun;
                 } else if(s.equals("Outdoors")) {
                     currentCategoryList = outdoors;
                 } else if (s.equals("Spa") || s.equals("Massage") || s.equals("Skincare/Haircare")) {
                     currentCategoryList = selfCare;
                 } else { // Handle "Other" search results
                     currentCategoryList = other;
                 }

                 // Iterate through locations in the selected category
                 locIndex = 0; // Reset index for the new category
                 while (locIndex < currentCategoryList.size()) {
                     currentPlace = currentCategoryList.get(locIndex);
                     System.out.println("Showing LocationDialog for: " + currentPlace.getName());
                     LocationDialog ld = new LocationDialog(null, currentPlace);
                     int choice = ld.getNext();
                     System.out.println("LocationDialog closed. User choice: " + choice);

                     switch (choice) {
                         case 3:
                             // Create and show the new WishlistGUI dialog
                             WishlistGUI wishlistDialog = new WishlistGUI(null, currentPlace, loggedInUsername);
                             wishlistDialog.setVisible(true); // This will block until the dialog is closed
                             // The action (add to wishlist and confirmation message) happens inside the dialog
                             printLns(); // Print separator lines after the dialog is closed testing 
                             break;
                         case 4:
                             currentPlace.scheduleVisit(); //
                             printLns();
                             break;
                         case 6:
                             System.out.println("Returning to main menu");
                             s = "Go Back"; // Set s to "Go Back" to exit this inner loop and return to main menu
                             break;
                         default:
                             System.out.println("");
                     }

                     if (s.equals("Go Back")) {
                         break; // Exit the inner loop
                     }

                     locIndex++; // Move to the next location in the category
                 }

                 if (!s.equals("Go Back")) {
                      // If the inner loop finished without going back, it means all locations in the category were viewed
                      JOptionPane.showMessageDialog(null, "No more locations in this category.");
                      System.out.println("No more locations in category. Returning to main menu.");
                 }
                 continue; // Go back to the start of the main loop to show the main menu again

				} else if (s.equals("VIEW_ALL_RESTAURANTS")) {
					System.out.println("User selected VIEW_ALL_RESTAURANTS. Opening RestaurantListViewer.");
					// Combine all restaurant lists (data is already loaded)
					List<Location> allRestaurants = new ArrayList<>();
					allRestaurants.addAll(nightOut);
					allRestaurants.addAll(brunch);
					allRestaurants.addAll(cafe);
					allRestaurants.addAll(lunchDinner);
   
					// Open the RestaurantListViewer
					RestaurantListViewer listViewer = new RestaurantListViewer(null, allRestaurants);
					listViewer.setVisible(true); // This will block until the dialog is closed
					System.out.println("RestaurantListViewer closed.");
   
					// Get the selected location and index after the dialog is closed
					Location currentPlace = listViewer.getSelectedLocation();
					int startIndex = listViewer.getSelectedIndex(); // Get the selected index
   
					// If a location was selected, iterate through the list from that index
					if (currentPlace != null && startIndex != -1) {
						System.out.println("Location selected from list viewer: " + currentPlace.getName() + " at index " + startIndex);
   
						// Iterate through the combined list starting from the selected index
						for (int i = startIndex; i < allRestaurants.size(); i++) {
							currentPlace = allRestaurants.get(i);
							System.out.println("Showing LocationDialog for: " + currentPlace.getName());
							LocationDialog ld = new LocationDialog(null, currentPlace);
							int choice = ld.getNext();
							System.out.println("LocationDialog closed. User choice: " + choice);
   
							switch (choice) {
								case 3:
									// Create and show the new WishlistGUI dialog
									WishlistGUI wishlistDialog = new WishlistGUI(null, currentPlace, loggedInUsername);
									wishlistDialog.setVisible(true); // This will block until the dialog is closed
									// The action (add to wishlist and confirmation message) happens inside the dialog
									printLns(); // Print separator lines after the dialog is closed
									break;
								case 4:
									currentPlace.scheduleVisit(); //
									printLns();
									break;
								case 6:
									System.out.println("Returning to main menu");
									// If "Go Back" is chosen, break the inner loop
									i = allRestaurants.size(); // Set i to size to exit the for loop
									s = "Go Back"; // Set s to "Go Back" to return to the main menu loop
									break;
								default:
									System.out.println("");
							}
   
							if (s.equals("Go Back")) {
								break; // Exit the for loop if Go Back was selected
							}
						}
   
					} else {
						System.out.println("No location selected from list viewer or selection cancelled. Returning to main menu.");
						// If no location was selected or selection was cancelled, return to main menu
						s = "Go Back";
					}
					continue; // Go back to the start of the loop to show the main menu again
			   }
   
		
        } // End of main application loop

        UIManager.put("OptionPane.background", new ColorUIResource(189, 148, 121)); // #BD9479
        UIManager.put("Panel.background", new ColorUIResource(189, 148, 121));      // #BD9479
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(0, 0, 0)); // Black

        // Show the dialog with the custom colors
        JOptionPane.showMessageDialog(null, "Thank you for using Roam and Root!\nHope you had fun exploring 💕" );


        //JOptionPane.showMessageDialog(null, "Thank you for using Roam and Root!\nHope you had fun exploring 💕");
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
            System.out.println("GeneralRecommendations.csv created/overwritten successfully.");
        } catch (IOException e) {
            System.out.println("Error creating/overwriting GeneralRecommendations.csv: " + e.getMessage());
        }
    } // createGeneralUserChoiceCSV function

    public static void newGenRecs() {
    // function to delete whatever entries are in the General Recommendations CSV from previous user
    File f = new File("places/GeneralRecommendations.csv");
    if (f.exists()) {
    f.delete();
    }
    try {
    f.createNewFile();
    } catch(IOException e) {
    e.printStackTrace();
    }
    } // newGenRecs function

    public static void printLns() {
    System.out.println("\n----------------------");
        System.out.println("----------------------");
        System.out.println("----------------------");
    } // printLns function

} // Main class
