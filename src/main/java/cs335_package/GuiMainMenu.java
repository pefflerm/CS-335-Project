package cs335_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuiMainMenu {
    private static JFrame frame;
    private static String userChoice = "";

    public static String getUserChoice() {
        frame = new JFrame("Route N Roam - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton restaurantBtn = new JButton("Restaurants");
        JButton activitiesBtn = new JButton("Things to Do");
        JButton selfCareBtn = new JButton("Self-Care");
        JButton searchBtn = new JButton("Search");
        JButton addLocationBtn = new JButton("Add Location");
        JButton exitBtn = new JButton("Exit");

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

