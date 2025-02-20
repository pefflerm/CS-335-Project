package roamnroot_package;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String [] args){
        String t="yes";
        while(t.equals("yes")){
        menu m =new menu();
        String s = m.Menu();
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
           

        }
        Scanner sc=new Scanner(System.in);
        System.out.println(" would you like to checkout other places and catergories? yes/no");
        t=sc.nextLine();
    }
    }
                   
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
}
