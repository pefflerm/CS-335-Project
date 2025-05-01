package cs335_package;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class SwipeRightDialog extends JDialog implements ActionListener{
	private int choice;
	private static final Color BROWN = new Color(160, 82, 45); // Sienna Brown
    private static final Color TAN = new Color(210, 180, 140); // Tan
    
	public int getChoice() {return this.choice;}
	public void setChoice(int x) {this.choice = x;}
	
	public SwipeRightDialog(Frame frame) {
		super(frame,"",true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// --- ADDED: Get content pane and set colors/layout ---
        Container contentPane = this.getContentPane();
        contentPane.setBackground(BROWN);
        contentPane.setLayout(new GridLayout(7, 1, 10, 10));
        // Add padding
        ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // -----------------------------------------------------
		
		initComponents();
	} // SwipeRightDialog constructor
	
	public void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JButton wish = new JButton("Add to wish list");
		wish.setForeground(TAN);
		wish.addActionListener(this);
		wish.setActionCommand("wishlist");
		
		JButton schedule = new JButton("Schedule a visit");
		schedule.setForeground(TAN);
		schedule.addActionListener(this);
		schedule.setActionCommand("schedule");
		
		gbc.gridx = 0; gbc.gridy = 0;
		add(wish,gbc);
		
		gbc.gridx = 0; gbc.gridy = 1;
		add(schedule,gbc);
		pack();
	} // initComponents function
	
	public void actionPerformed(ActionEvent e) {
		if ("wishlist".equals(e.getActionCommand())) {
			setChoice(3);
			dispose();
		}else {
			setChoice(4);
			dispose();
		}
	} // actionPerformed function
} // SwipeRightDialog class
