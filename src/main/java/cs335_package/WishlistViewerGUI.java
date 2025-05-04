package cs335_package;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WishlistViewerGUI extends JDialog {

    private JTextArea wishlistArea;
    private String username;

    public WishlistViewerGUI(Frame owner, String username) {
        super(owner, username + "'s Wishlist", true); // Modal dialog with user's name in title
        this.username = username;
        System.out.println("WishlistViewerGUI initialized for user: " + username);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(owner);

        // Use a JTextArea inside a JScrollPane to display the wishlist
        wishlistArea = new JTextArea();
        wishlistArea.setEditable(false);
        wishlistArea.setWrapStyleWord(true);
        wishlistArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(wishlistArea);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // Set preferred size
        // Add a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            System.out.println("Close button triggered.");
            dispose();
        }); // Close the dialog when clicked

        // Panel to hold the list and button
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);

        loadAndDisplayWishlist(); // Load and display the wishlist when the dialog is created

        pack(); // Size the dialog to fit the content
    }

    private void loadAndDisplayWishlist() {
        String userWishlistFile = "places/wishlist_" + username + ".csv";
        File file = new File(userWishlistFile);
        StringBuilder wishlistText = new StringBuilder("--- " + username + "'s Wishlist ---\n\n");

        System.out.println("Attempting to load wishlist from: " + file.getAbsolutePath());

        if (!file.exists()) {
            wishlistText.append("Your wishlist is empty.");
            System.out.println("Wishlist file not found: " + file.getAbsolutePath());
        } else {
            System.out.println("Wishlist file found. Reading from: " + file.getAbsolutePath());
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Assuming each line is Name,Type,Location
                    String[] parts = line.split(",");
                    if (parts.length >= 1) {
                        wishlistText.append(parts[0]); // Display the name
                        if (parts.length >= 3) {
                            wishlistText.append(" (").append(parts[2]).append(")"); // Display location if available
                        }
                        wishlistText.append("\n");
                        System.out.println("Read wishlist entry: " + line);
                    }
                }
                System.out.println("Finished reading wishlist file.");
            } catch (IOException e) {
                wishlistText.append("Error loading wishlist: ").append(e.getMessage());
                System.err.println("Error reading wishlist from " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }

        wishlistArea.setText(wishlistText.toString());
        wishlistArea.setCaretPosition(0); // Scroll to the top
    }
}
