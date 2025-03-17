package cs335_package;
import java.util.*;

public class Menu {
	public static String Menu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        String subChoice;
     
        
            System.out.println("\n===== MENU =====");
            System.out.println("1. Restaurants");
            System.out.println("2. Things to Do");
            System.out.println("3. Self-Care");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = getValidChoice(scanner, 4);

            switch (choice) {
                case 1: {
                    String selectedRestaurant = restaurantMenu(scanner);
                    if (!selectedRestaurant.isEmpty()) {
                        System.out.println("You selected: " + selectedRestaurant);
                        subChoice=selectedRestaurant;
                        return selectedRestaurant;
                    }
                    break;
                }
                case 2: {
                    String selectedActivity = activitiesMenu(scanner);
                    if (!selectedActivity.isEmpty()) {
                        System.out.println("You selected: " + selectedActivity);
                        subChoice=selectedActivity;
                        return selectedActivity;
                    }
                    break;
                }
                case 3: {
                    String selectedSelfCare = selfCareMenu(scanner);
                    if (!selectedSelfCare.isEmpty()) {
                        System.out.println("You selected: " + selectedSelfCare);
                        subChoice=selectedSelfCare;
                        return selectedSelfCare;
                    }
                    break;
                }
                
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    return "";
                    
                   
            }

        

        scanner.close();
        return "";
        
   }

    private static String restaurantMenu(Scanner scanner) {
        int subChoice;
        
            System.out.println("\n===== Restaurant Types =====");
            System.out.println("1. Night Out");
            System.out.println("2. Brunch");
            System.out.println("3. Cafes/Bakeries/study friendly");
            System.out.println("4. Lunch/Dinner");
            System.out.println("5. Go Back");
            System.out.print("Enter your choice: ");

            subChoice = getValidChoice(scanner, 5);

            
            return getRestaurantType(subChoice);
            

    }

    private static String activitiesMenu(Scanner scanner) {
        int subChoice;
       
            System.out.println("\n===== Things to Do =====");
            System.out.println("1. Shopping");
            System.out.println("2. Fun Activities");
            System.out.println("3. Outdoors");
            System.out.println("4. Go Back");
            System.out.print("Enter your choice: ");

            subChoice = getValidChoice(scanner, 4);

           
                return getActivityType(subChoice);
            

        

   
    }

    private static String selfCareMenu(Scanner scanner) {
        int subChoice;
     
            System.out.println("\n===== Self-Care Activities =====");
            System.out.println("1. Spa");
            System.out.println("2. Massage");
            System.out.println("3. Skincare/Haircare");
            System.out.println("4. Go Back");
            System.out.print("Enter your choice: ");

            subChoice = getValidChoice(scanner, 5);

            return getSelfCareType(subChoice);
             
    }

    
    private static int getValidChoice(Scanner scanner, int maxOption) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= maxOption) {
                    return choice;
                }
            } else {
                scanner.next();
            }
            System.out.print("Invalid input! Please enter a number between 1 and " + maxOption + ": ");
        }
    }

    private static String getRestaurantType(int choice) {
        switch (choice) {
            case 1: return "Night Out";
            case 2: return "Brunch";
            case 3: return "Cafes/Bakeries";
            case 4: return "Lunch/Dinner";
            case 5: return "Go Back";
            default: return ""; 
        }
    }

    private static String getActivityType(int choice) {
        switch (choice) {
            case 1: return "Shopping";
            case 2: return "Fun Activities";
            case 3: return "Outdoors";
            case 4: return "Go Back";
            default: return ""; 
        }
    }

    private static String getSelfCareType(int choice) {
        switch (choice) {
            case 1: return "Spa";
            case 2: return "Massage";
            case 3: return "Skincare/Haircare";
            
            case 4: return "Go Back";
            default: return ""; 
        }
    }
}
