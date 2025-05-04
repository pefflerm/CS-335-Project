package cs335_package;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

//https://stackoverflow.com/questions/37629017/java-dialog-with-jlist-and-option-buttons
//https://examples.javacodegeeks.com/java-development/desktop-java/swing/jdialog/java-jdialog-example/

public class RestaurantListViewer extends JDialog {

    private JList<String> restaurantJList;
    private List<Location> locations; // Store the list of Location objects
    private Location selectedLocation = null; // To store the selected location
    private int selectedIndex = -1; // Added to store the selected index

    public RestaurantListViewer(Frame owner, List<Location> restaurants) {
        super(owner, "All Restaurants", true); // Modal dialog
        this.locations = restaurants; // Store the passed list

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create a JList to display restaurant names
        String[] restaurantNames = new String[restaurants.size()];
        for (int i = 0; i < restaurants.size(); i++) {
            restaurantNames[i] = locations.get(i).getName(); // Assuming getName() exists
        }
        restaurantJList = new JList<>(restaurantNames);
        restaurantJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one selection
        restaurantJList.setLayoutOrientation(JList.VERTICAL);

        // Wrap the JList in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(restaurantJList);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // Set preferred size

        // Add a select button and a close button
        JButton selectButton = new JButton("Select");
        JButton closeButton = new JButton("Close");

        // Initially disable the select button until an item is chosen
        selectButton.setEnabled(false);

        // Add listener to the list to enable the select button when an item is selected
        restaurantJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Ensure selection is final
                selectButton.setEnabled(restaurantJList.getSelectedIndex() != -1);
            }
        });

        // Add action listener to the select button
        selectButton.addActionListener(e -> {
            selectedIndex = restaurantJList.getSelectedIndex(); // Store the selected index
            if (selectedIndex != -1) {
                selectedLocation = locations.get(selectedIndex); // Get the corresponding Location object
                dispose(); // Close the dialog
            }
        });

        // Add action listener to the close button
        closeButton.addActionListener(e -> {
            selectedLocation = null; // No location selected
            selectedIndex = -1; // Reset selected index
            dispose(); // Close the dialog
        });

        // Panel to hold the list and buttons
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(closeButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        pack(); // Size the dialog to fit the content
        setLocationRelativeTo(owner); // Center the dialog
    }

    // Method to retrieve the selected location after the dialog is closed
    public Location getSelectedLocation() {
        return selectedLocation;
    }

    // Added method to retrieve the selected index
    public int getSelectedIndex() {
        return selectedIndex;
    }
}

