package roamnroot_package;

import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String [] args){
    	Scanner sc = new Scanner(System.in); // Initialize Scanner here for my Nada's code
        String t="yes";
        String s="yes";
       
        while(t.equals("yes") && s!=null){
        menu m =new menu();
        s = m.Menu();
        if(s!=null){
            System.out.println(s);
            System.out.println(s);
            System.out.println("");
        
        
        while(s.equals("Go Back")){
            s=m.Menu();
            
        }
        
        if(!s.isEmpty()){

        Placesfactory df = new Placesfactory("places/"+getCsvFile(s));
        System.out.println(df.getHeaders());
        String[] firstRow = df.getHeaders().split(",");
        System.out.println(firstRow);

        

        System.out.println("");
        ArrayList<String> Listofplaces  = new ArrayList<String>();

        while (df.moreData()) {
            ArrayList<String> placeDetails = df.getNextPlace(); // Read once and store
        
            for(int i=0;i<(firstRow.length);i++) {  // Restaurants ,location,Notes,Reservation,Stars,Price Point,Has Kelsey been?,TikTok/ IG Reels,Links: ,,
                if((placeDetails.size())>i){
                    System.out.println(firstRow[i].trim()+":"+ placeDetails.get(i));}

           
            }
            System.out.println("----------------------");
         // Ask if the user wants to leave a review
            System.out.print("Would you like to leave a review for this place? (yes/no): ");
            String leaveReview = sc.nextLine().trim().toLowerCase();
            // If yes, ask for rating and a text review
            if (leaveReview.equals("yes")) {
                int rating = getRating(sc);
                System.out.print("Enter your review: ");
                String reviewText = sc.nextLine().trim();
                System.out.println("Thank you for your review!");
                // Optionally store or display the review, here we just print it
                System.out.println("Your review: " + rating + " stars");
                System.out.println("Review: " + reviewText);
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
        // Ask if the user wants to explore other categories
        System.out.print("Would you like to check out other categories? (yes/no): ");
        t = sc.nextLine().trim();
        }
        System.out.println("----------------------");
        System.out.println("Goodbye. Thanks For Using The Program");
        
        }
      //  Scanner sc1=new Scanner(System.in); //DO NOT REMOVE YET PLEASE 
     //   System.out.println(" would you like to checkout other places and catergories? (yes/no) ");//DO NOT REMOVE YET PLEASE 
        //t=sc1.nextLine();//DO NOT REMOVE YET PLEASE 
    }
        
    }
       private static int getRating(Scanner sc) {
		// TODO Auto-generated method stub
		return 0;
	}
	private static String getCsvFile(String category) {
        switch (category) {
            case "Night Out": return "Copy of Boston Recommendations  - Going out Bars.csv";
            case "Brunch": return "Boston Recommendations  - Brunch.csv";
            case "Cafes/Bakeries": return "Copy of Boston Recommendations  - Coffee Shops _ Bakery.csv";
            case "lunch/dinner": return "Copy of Boston Recommendations  - Restaurants.csv";

            case "Shopping": return "Copy of Boston Recommendations  - Shopping.csv";
            case "Fun Activities": return "Copy of Boston Recommendations  - Things To Do.csv";
            case "Outdoors": return "Copy of Boston Recommendations  - Summer _ Outdoor.csv";

            case "Spa": return "Copy of Boston Recommendations  - Spa_Beauty.csv";
            case "Skincare/Haircare": return "Copy of Boston Recommendations  - Spa_Beauty.csv";
            case "Massage": return "Copy of Boston Recommendations  - Spa_Beauty.csv";

            default: return "";
        }
	} 

	 // Method to get a valid star rating (1-5)
	   private static int getRating1(Scanner sc) {
	       int rating;
	       while (true) {
	           System.out.print("Enter your rating (1-5 stars): ");
	           if (sc.hasNextInt()) {
	               rating = sc.nextInt();
	               sc.nextLine();  // Consume the newline character
	               if (rating >= 1 && rating <= 5) {
	                   return rating;
	               } else {
	                   System.out.println("Please enter a rating between 1 and 5.");
	               }
	           } else {
	               sc.next(); // Consume the invalid input
	               System.out.println("Invalid input! Please enter a number between 1 and 5.");
	           }
	       }
	   }
	}





