package cs335_package;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class GuiAddLocation {

    public static void showAddLocationForm() {
        JFrame frame = new JFrame("Add New Location");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(12, 2, 10, 5));

        // Input Fields
        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField reservationField = new JTextField();
        JTextField coatCheckField = new JTextField();
        JTextField coverField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField starsField = new JTextField();
        JTextField linkField = new JTextField();
        JTextField reviewsField = new JTextField();
        JTextField businessHoursField = new JTextField();

        // Add fields to the frame
        frame.add(new JLabel("Place Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Genre:"));
        frame.add(typeField);
        frame.add(new JLabel("Location:"));
        frame.add(locationField);
        frame.add(new JLabel("Reservation (Yes/No):"));
        frame.add(reservationField);
        frame.add(new JLabel("Coat Check (Yes/No):"));
        frame.add(coatCheckField);
        frame.add(new JLabel("Cover Charge (Yes/No):"));
        frame.add(coverField);
        frame.add(new JLabel("Price ($/$$/$$$):"));
        frame.add(priceField);
        frame.add(new JLabel("Stars (e.g. 4.5):"));
        frame.add(starsField);
        frame.add(new JLabel("Link:"));
        frame.add(linkField);
        frame.add(new JLabel("Reviews:"));
        frame.add(reviewsField);
        frame.add(new JLabel("Business Hours:"));
        frame.add(businessHoursField);

        JButton submitBtn = new JButton("Add Location");

        submitBtn.addActionListener(e -> {
            String[] inputs = {
                nameField.getText(), typeField.getText(), locationField.getText(), reservationField.getText(),
                coatCheckField.getText(), coverField.getText(), priceField.getText(), starsField.getText(),
                linkField.getText(), reviewsField.getText(), businessHoursField.getText()
            };

            try {
                String csvFile = "places/general.csv";
                File file = new File(csvFile);
                FileWriter writer = new FileWriter(file, true);

                // Create header if new file
                if (file.length() == 0) {
                    writer.append("Name,Type,Location,Reservation,Coat Check,Cover,Price,Stars,Link,Reviews,Business Hours\n");
                }

                writer.append(String.join(",", inputs));
                writer.append("\n");
                writer.close();

                JOptionPane.showMessageDialog(frame, "✅ Location added successfully!");
                frame.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "❌ Error while saving location: " + ex.getMessage());
            }
        });

        frame.add(new JLabel()); // for spacing
        frame.add(submitBtn);

        frame.setVisible(true);
    }
}
