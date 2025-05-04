package cs335_package;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class GuiMainMenu {
    private static JFrame frame;
    private static String userChoice = "";

    // Define colors
   private static final Color BROWN = new Color(189, 148, 121); // Sienna Brown
    private static final Color TAN = new Color(189, 148, 121); // Tan

    public static String getUserChoice() {
         // --- Set default colors for JOptionPane permanently ---
        UIManager.put("OptionPane.background", new ColorUIResource(189, 148, 121)); // #BD9479
        UIManager.put("Panel.background", new ColorUIResource(189, 148, 121));     // #BD9479
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(0, 0, 0)); // Black
        // --- End of permanent color change ---


        frame = new JFrame("Route N Roam - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BROWN);

        // Changed GridLayout to accommodate the new button
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10)); // Increased rows to 7
        panel.setBackground(BROWN); // Set panel background to Brown
        // Add padding around the panel content
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton restaurantBtn = new JButton("Restaurants");
        JButton activitiesBtn = new JButton("Things to Do");
        JButton selfCareBtn = new JButton("Self-Care");
        JButton searchBtn = new JButton("Search");
        JButton addLocationBtn = new JButton("Add Location");
        JButton viewWishlistBtn = new JButton("View Wishlist"); // New button
        JButton exitBtn = new JButton("Exit");

        // --- ADDED: Set button text color ---
        restaurantBtn.setForeground(TAN);
        activitiesBtn.setForeground(TAN);
        selfCareBtn.setForeground(TAN);
        searchBtn.setForeground(TAN);
        addLocationBtn.setForeground(TAN);
        viewWishlistBtn.setForeground(TAN); // Set color for new button
        exitBtn.setForeground(TAN);
        // ------------------------------------

        restaurantBtn.addActionListener(e -> {
            userChoice = showRestaurantMenu();
            frame.dispose();
        });

        activitiesBtn.addActionListener(e -> {
            userChoice = showActivitiesMenu();
            frame.dispose();
        });

        selfCareBtn.addActionListener(e -> {
            userChoice = showSelfCareMenu();
            frame.dispose();
        });

        searchBtn.addActionListener(e -> {
            // Handle cancellation of the input dialog for custom search
            String customSearchTerm = JOptionPane.showInputDialog(frame, "Enter your custom search:");
            if (customSearchTerm == null) {
                userChoice = "Go Back"; // Return "Go Back" if cancelled
            } else {
                userChoice = customSearchTerm; // Otherwise, use the entered term
            }
            frame.dispose();
        });


        addLocationBtn.addActionListener(e -> {
            GuiAddLocation.showAddLocationForm(); // Launch the full form
        });

        // Action listener for the new button
        viewWishlistBtn.addActionListener(e -> {
            userChoice = "VIEW_WISHLIST"; // Set a specific indicator
            frame.dispose();
        });


        exitBtn.addActionListener(e -> {
            userChoice = "";
            frame.dispose();
            System.out.println("Bye Bye");
        });

        panel.add(restaurantBtn);
        panel.add(activitiesBtn);
        panel.add(selfCareBtn);
        panel.add(searchBtn);
        panel.add(addLocationBtn);
        panel.add(viewWishlistBtn); // Add the new button to the panel
        panel.add(exitBtn);

        frame.add(panel);
        frame.setVisible(true);

        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }

        return userChoice;
    }

    private static String showRestaurantMenu() {
        String result = "";
        
        Object oldMsgBg = UIManager.get("OptionPane.messageForeground");
        Object oldPaneBg = UIManager.get("OptionPane.background");
        Object oldPanelBg = UIManager.get("Panel.background");

        try {
            // Set custom colors: #BD9479 background, Black text
            UIManager.put("OptionPane.background", new ColorUIResource(189, 148, 121));
            UIManager.put("Panel.background", new ColorUIResource(189, 148, 121));
            UIManager.put("OptionPane.messageForeground", new ColorUIResource(0, 0, 0));

            // ADDED: New option "View All Restaurants"
            String[] options = {"Night Out", "Brunch", "Cafes/Bakeries", "Lunch/Dinner", "View All Restaurants", "Other", "Go Back"};
            int optionResult = JOptionPane.showOptionDialog(frame, "Choose a restaurant type:",
                    "Restaurant Types", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            // Adjust indices based on the new option
            if (optionResult == 5) { // Index 5 is now "Other"
                 // --- Temporarily set colors for the input dialog ---
                Object oldInputMsgBg = UIManager.get("OptionPane.messageForeground");
                Object oldInputPaneBg = UIManager.get("OptionPane.background");
                Object oldInputPanelBg = UIManager.get("Panel.background");

                try {
                    // Set custom colors for input dialog: #BD9479 background, Black text
                    UIManager.put("OptionPane.background", new ColorUIResource(189, 148, 121));
                    UIManager.put("Panel.background", new ColorUIResource(189, 148, 121));
                    UIManager.put("OptionPane.messageForeground", new ColorUIResource(0, 0, 0));

                    // Handle cancellation of the input dialog for "Other" restaurant search
                    String otherRestaurantSearch = JOptionPane.showInputDialog(frame, "Enter a restaurant you like:");
                    if (otherRestaurantSearch == null) {
                        result = "Go Back"; // Return "Go Back" if cancelled
                    } else {
                        result = otherRestaurantSearch; // Otherwise, use the entered term
                    }

                } finally {
                    // --- Restore original colors for input dialog ---
                    UIManager.put("OptionPane.messageForeground", oldInputMsgBg);
                    UIManager.put("OptionPane.background", oldInputPaneBg);
                    UIManager.put("Panel.background", oldInputPanelBg);
                }
                

            } else if (optionResult == 4) { // Index 4 is now "View All Restaurants"
                 result = "VIEW_ALL_RESTAURANTS"; // Return a specific string
            }
            else if (optionResult == 6 || optionResult == JOptionPane.CLOSED_OPTION) { // Index 6 is now "Go Back" or dialog closed
                result = "Go Back";
            } else if (optionResult >= 0) {
                result = options[optionResult]; // Return the selected category name
            }

        } finally {
            // --- Restore original colors for the menu dialog ---
            UIManager.put("OptionPane.messageForeground", oldMsgBg);
            UIManager.put("OptionPane.background", oldPaneBg);
            UIManager.put("Panel.background", oldPanelBg);
        }
        
        return result;
    }


    private static String showActivitiesMenu() {
        String[] options = {"Shopping", "Fun Activities", "Outdoors", "Other", "Go Back"};
        int result = JOptionPane.showOptionDialog(frame, "Choose an activity:",
                "Things to Do", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (result == 3) {
            // Handle cancellation of the input dialog for "Other" activity search
            String otherActivitySearch = JOptionPane.showInputDialog(frame, "Enter an activity:");
            if (otherActivitySearch == null) {
                return "Go Back"; // Return "Go Back" if cancelled
            } else {
                return otherActivitySearch; // Otherwise, use the entered term
            }
        } else if (result == 4 || result == JOptionPane.CLOSED_OPTION) {
            return "Go Back";
        } else if (result >= 0) {
            return options[result];
        }
        return ""; // Should not happen with "Go Back" handling
    }

    private static String showSelfCareMenu() {
        String[] options = {"Spa", "Massage", "Skincare/Haircare", "Other", "Go Back"};
        int result = JOptionPane.showOptionDialog(frame, "Choose a self-care activity:",
                "Self-Care", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (result == 3) {
            // Handle cancellation of the input dialog for "Other" self-care search
            String otherSelfCareSearch = JOptionPane.showInputDialog(frame, "Enter a self-care activity:");
            if (otherSelfCareSearch == null) {
                return "Go Back"; // Return "Go Back" if cancelled
            } else {
                return otherSelfCareSearch; // Otherwise, use the entered term
            }
        } else if (result == 4 || result == JOptionPane.CLOSED_OPTION) {
            return "Go Back";
        } else if (result >= 0) {
            return options[result];
        }
        return ""; // Should not happen with "Go Back" handling
    }
}
