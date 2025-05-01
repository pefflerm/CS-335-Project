package cs335_package;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;


public class Main {
	public static void main(String[] args) {
		
                // Load the logo image
            ImageIcon logoIcon = new ImageIcon("/Users/nadahijou/Downloads/C.png");

                // Create and show the custom WelcomeDialog
                WelcomeDialog welcomeDialog = new WelcomeDialog(null, "Welcome to Roam and Root!", "Welcome", logoIcon);
                welcomeDialog.setVisible(true); // This will block until the dialog is closed by clicking
        
                // After the welcome dialog is closed, the program continues here
        
            
		
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
        
        
        int locIndex = 0; // keeps track of where in the locations arrayList the user is
        s = GuiMainMenu.getUserChoice();

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
              
                while(s.equals("Go Back")){
                	s = GuiMainMenu.getUserChoice();
                	locIndex = 0;
                }
                
                if(!s.isEmpty()){
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
                    }else {
                    	newGenRecs(); // clears General Recommendation csv
                    	other = new ArrayList<Location>(); // clears any Location objects in the other ArrayList
                    	SearchYelp.initiateSearch(s); // pulls in data from Yelp to GeneralRecommendations csv
                    	
                    	// creates Location objects with results from new search
                    	int rowIndex = 1;
	                	String filePath = "places/GeneralRecommendations.csv";
	                    other = new ArrayList<Location>();
	                    PlacesFactory df = new PlacesFactory(filePath);
	                    while (df.moreData()){
	                        other.add(df.getNextPlace(rowIndex,filePath));
	                        rowIndex += 1;
	                    }
	                    
                    	currentPlace = other.get(locIndex);
                    }
                    
                    locIndex += 1;

                    LocationDialog ld = new LocationDialog(null, currentPlace);
                    int choice = ld.getNext();
                    
                    switch (choice) {
                    	case 3:
                    		// Create and show the new WishlistGUI dialog
                            WishlistGUI wishlistDialog = new WishlistGUI(null, currentPlace);
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
                    		s = "Go Back";
                    		break;
                    	default:
                    		System.out.println("");
                    }
                    
                    
                   
                }
            }
        } // while loop
        
                UIManager.put("OptionPane.background", new ColorUIResource(189, 148, 121)); // #BD9479
        UIManager.put("Panel.background", new ColorUIResource(189, 148, 121));      // #BD9479
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(0, 0, 0)); // Black

        // Show the dialog with the custom colors and logo
        JOptionPane.showMessageDialog(null, "Thank you for using Roam and Root!\nHope you had fun exploring 💕", "Goodbye", JOptionPane.INFORMATION_MESSAGE, logoIcon);


       
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

    