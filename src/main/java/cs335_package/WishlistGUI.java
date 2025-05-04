package cs335_package;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class WishlistGUI extends JDialog {
    private Location location;
    private String username; // Added field to store the username

    // Modified constructor to accept the username
    public WishlistGUI(Frame owner, Location loc, String username) {
        super(owner, "Add to Wishlist", true); // Modal dialog
        this.location = loc;
        this.username = username; // Store the username

        initComponents();
        pack(); // Adjusts the dialog size based on its components
        setLocationRelativeTo(owner); // Center the dialog relative to the owner frame
    }

    private void initComponents() {
        //https://stackoverflow.com/questions/15311316/how-to-put-two-components-to-a-jpanel-with-borderlayout sources
        //https://stackoverflow.com/questions/56287248/how-to-apply-existed-buttons-to-yes-no-buttons sources
        // Use a JPanel for better layout control
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Add some spacing
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Message label
        JLabel messageLabel = new JLabel("Do you want to add " + location.getName() + " to your wishlist?", SwingConstants.CENTER);
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.PLAIN, 14)); // Optional: make font a bit larger

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Center buttons with spacing

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToWishlistAndConfirm();
                dispose(); // Close the dialog after action
            }
        });

        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Wishlist addition cancelled by user.");
                dispose(); // Close the dialog
            }
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        // Add components to the main panel
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the panel as the content pane
        setContentPane(panel);
    }

    
    //Adds the location to the wishlist CSV file specific to the user and shows a confirmation message.
    
    private void addToWishlistAndConfirm() {
        // Construct the user-specific wishlist file path
        String userWishlistFile = "places/wishlist_" + username + ".csv";

        try (FileWriter fw = new FileWriter(userWishlistFile, true);
             PrintWriter pw = new PrintWriter(fw)) {

            // Use getLocation() for address and getType() for category
            pw.println(location.getName() + "," + location.getType() + "," + location.getLocation());

            // Show success message
            JOptionPane.showMessageDialog(this,
                    location.getName() + " added to " + username + "'s wishlist successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            System.out.println("Location added to " + username + "'s wishlist successfully.");

        } catch (IOException e) {
            // Show error message
            JOptionPane.showMessageDialog(this,
                    "Failed to add " + location.getName() + " to " + username + "'s wishlist.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.out.println("Error adding to " + username + "'s wishlist: " + e.getMessage());
    
        }
    }


}
