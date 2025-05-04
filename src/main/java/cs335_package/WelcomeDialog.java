package cs335_package;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class WelcomeDialog extends JDialog {
//https://www.geeksforgeeks.org/java-swing-jdialog-examples/ sources
//https://stackoverflow.com/questions/11623777/how-to-implement-a-java-swing-application-to-touch-screen sources
//https://stackoverflow.com/questions/6810581/how-to-center-the-text-in-a-jlabel
//https://andrewbridge.wordpress.com/2012/03/26/java-styling-a-swing-jlabel/
//https://www.youtube.com/watch?v=U3ACpPu9_kE
//https://www.geeksforgeeks.org/java-swing-creating-custom-message-dialogs/
	
    public WelcomeDialog(Frame owner, String message, String title, ImageIcon icon) {
        super(owner, title, true); // Modal dialog

        // Set custom colors that worked with the logo
        UIManager.put("OptionPane.background", new ColorUIResource(189, 148, 121)); // #BD9479
        UIManager.put("Panel.background", new ColorUIResource(189, 148, 121));     // #BD9479
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(0, 0, 0)); // Black

        // Create a panel to hold the content
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        panel.setBackground(UIManager.getColor("Panel.background")); // Use the custom background color

        // Add the logo
        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(iconLabel, BorderLayout.NORTH);
        }

        // Add the message
        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setBackground(UIManager.getColor("Panel.background")); // Use the custom background color
        messageArea.setForeground(UIManager.getColor("OptionPane.messageForeground")); // Use the custom text color
        messageArea.setFont(messageArea.getFont().deriveFont(Font.PLAIN, 16)); // Make font a bit larger
        messageArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center text horizontally

        // Wrap the JTextArea in a JScrollPane just in case the message is long
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border
        scrollPane.getViewport().setBackground(UIManager.getColor("Panel.background")); // Match background

        panel.add(scrollPane, BorderLayout.CENTER);

        // Add a label or instruction for the user
        JLabel instructionLabel = new JLabel("Click anywhere to begin...", SwingConstants.CENTER);
        instructionLabel.setFont(instructionLabel.getFont().deriveFont(Font.ITALIC, 12));
        instructionLabel.setForeground(UIManager.getColor("OptionPane.messageForeground"));
        panel.add(instructionLabel, BorderLayout.SOUTH);


        // Add a mouse listener to the panel to close the dialog on click
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Close the dialog
            }
        });

        // Add the same mouse listener to the message area and icon label
        // This ensures clicking directly on the text or image also closes the dialog
        messageArea.addMouseListener(new MouseAdapter() {
             @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Close the dialog
            }
        });
         if (icon != null) {
             // Find the icon label component and add listener
             for (Component comp : panel.getComponents()) {
                 if (comp instanceof JLabel && ((JLabel) comp).getIcon() == icon) {
                     comp.addMouseListener(new MouseAdapter() {
                         @Override
                         public void mouseClicked(MouseEvent e) {
                             dispose(); // Close the dialog
                         }
                     });
                     break; // Found the icon label
                 }
             }
         }


        setContentPane(panel);
        pack(); // Size the dialog to fit the content
        setLocationRelativeTo(owner); // Center the dialog
    }

    
}
