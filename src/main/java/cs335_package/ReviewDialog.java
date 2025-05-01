
//sources: https://stackoverflow.com/questions/8705188/actionlistener-event-in-java
//this helped me figure out how to add the action listener to the button and how to do the review 

package cs335_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewDialog extends JDialog {
    private JComboBox<Integer> ratingComboBox;
    private JTextArea reviewTextArea;
    private JButton submitButton;
    private JButton cancelButton;
    private boolean submitted = false;
    private int rating = 0; // Default rating
    private String reviewText = "";

    public ReviewDialog(Frame owner, String placeName) {
        super(owner, "Leave a Review for " + placeName, true); // Modal dialog
        initComponents();
        pack(); // Adjusts dialog size to fit components
        setLocationRelativeTo(owner); // Center relative to the owner frame (can be null)
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout with gaps

        // --- Rating Panel ---
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.add(new JLabel("Rating (1-5 stars):"));
        Integer[] ratings = {1, 2, 3, 4, 5};
        ratingComboBox = new JComboBox<>(ratings);
        ratingComboBox.setSelectedIndex(4); // Default to 5 stars
        ratingPanel.add(ratingComboBox);

        // --- Review Text Area ---
        reviewTextArea = new JTextArea(5, 30); // Rows, Columns
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reviewTextArea); // Add scroll bars

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // --- Add components to dialog ---
        add(ratingPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitted = true;
                rating = (Integer) ratingComboBox.getSelectedItem();
                reviewText = reviewTextArea.getText().trim();
                dispose(); // Close the dialog
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitted = false;
                dispose(); // Close the dialog
            }
        });
    }

    // --- Getters for the results ---
    public boolean isSubmitted() {
        return submitted;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }
}