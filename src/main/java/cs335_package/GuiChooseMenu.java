package cs335_package;

import javax.swing.*;
import java.awt.*;

public class GuiChooseMenu {
   

    public static int getUserChoice() {
    	final int[] userChoice = {-1}; // ✅ allows mutation inside lambda

        JFrame frame = new JFrame("Route N Roam Menu");
        //frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("What do you want to do?", SwingConstants.CENTER);
        frame.add(label);

        String[] options = {
            "Add to wish list",
            "Schedule a visit"
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
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
        frame.pack();

        /*
        while (userChoice[0] == -1) {
            try {
                Thread.sleep(100); // Wait until a button is clicked
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		*/
        if (userChoice[0]==0) {
        	return 3;
        }else {
        	return 4;
        }
        //return userChoice[0];
    }
}
