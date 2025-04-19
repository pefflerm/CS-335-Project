package cs335_package;

import javax.swing.*;
import java.awt.*;

public class GuiChooseMenu {
   

    public static int getUserChoice() {
    	final int[] userChoice = {-1}; // ✅ allows mutation inside lambda

        JFrame frame = new JFrame("Route N Roam Menu");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 1, 10, 10));

        JLabel label = new JLabel("What do you want to do?", SwingConstants.CENTER);
        frame.add(label);

        String[] options = {
            "1. Leave a review",
            "2. Read reviews",
            "3. Add to wish list",
            "4. Schedule a visit",
            "5. See next location",
            "6. Return to main menu"
        };

        for (int i = 0; i < options.length; i++) {
            int choice = i + 1;
            JButton button = new JButton(options[i]);
            button.addActionListener(e -> {
            	userChoice[0] = choice;
                frame.dispose();
            });
            frame.add(button);
        }

        frame.setVisible(true);

        while (userChoice[0] == -1) {
            try {
                Thread.sleep(100); // Wait until a button is clicked
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return userChoice[0];
    }
}
