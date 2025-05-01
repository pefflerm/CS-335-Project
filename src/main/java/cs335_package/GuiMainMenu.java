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

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBackground(BROWN); // Set panel background to Brown
        // Add padding around thi e panel content
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton restaurantBtn = new JButton("Restaurants");
        JButton activitiesBtn = new JButton("Things to Do");
        JButton selfCareBtn = new JButton("Self-Care");
        JButton searchBtn = new JButton("Search");
        JButton addLocationBtn = new JButton("Add Location");
        JButton exitBtn = new JButton("Exit");
        
        // --- ADDED: Set button text color ---
        restaurantBtn.setForeground(TAN);
        activitiesBtn.setForeground(TAN);
        selfCareBtn.setForeground(TAN);
        searchBtn.setForeground(TAN);
        addLocationBtn.setForeground(TAN);
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
            userChoice = JOptionPane.showInputDialog(frame, "Enter your custom search:");
            frame.dispose();
        });

        addLocationBtn.addActionListener(e -> {
            GuiAddLocation.showAddLocationForm(); // Launch the full form
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
        String[] options = {"Night Out", "Brunch", "Cafes/Bakeries", "Lunch/Dinner", "Other", "Go Back"};
        int result = JOptionPane.showOptionDialog(frame, "Choose a restaurant type:",
                "Restaurant Types", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (result == 4) {
            return JOptionPane.showInputDialog(frame, "Enter a restaurant you like:");
        } else if (result == 5 || result == JOptionPane.CLOSED_OPTION) {
            return "Go Back";
        } else if (result >= 0) {
            return options[result];
        }
        return "";
    }

    private static String showActivitiesMenu() {
        String[] options = {"Shopping", "Fun Activities", "Outdoors", "Other", "Go Back"};
        int result = JOptionPane.showOptionDialog(frame, "Choose an activity:",
                "Things to Do", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (result == 3) {
            return JOptionPane.showInputDialog(frame, "Enter an activity:");
        } else if (result == 4 || result == JOptionPane.CLOSED_OPTION) {
            return "Go Back";
        } else if (result >= 0) {
            return options[result];
        }
        return "";
    }

    private static String showSelfCareMenu() {
        String[] options = {"Spa", "Massage", "Skincare/Haircare", "Other", "Go Back"};
        int result = JOptionPane.showOptionDialog(frame, "Choose a self-care activity:",
                "Self-Care", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (result == 3) {
            return JOptionPane.showInputDialog(frame, "Enter a self-care activity:");
        } else if (result == 4 || result == JOptionPane.CLOSED_OPTION) {
            return "Go Back";
        } else if (result >= 0) {
            return options[result];
        }
        return "";
    }
}

