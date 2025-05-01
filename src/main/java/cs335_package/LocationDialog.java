package cs335_package;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LocationDialog extends JDialog implements ActionListener{
	private int doNext = 0; // keeps track of what the user selects to do
	// 1 = write a review
	// 2 = read reviews
	// 3 = swipe right (add to wish list)
	// 4 = swipe right (schedule visit)
	// 5 = swipe left (see next location)
	// 6 = return to main menu
	private boolean reviewed = false;
	public Location place;
	
	// getters
	public int getNext() {return this.doNext;}
	public boolean getReviewed() {return this.reviewed;}
	
	// setters
	public void setNext(int x) {this.doNext = x;}
	public void setReviewed(boolean x) {this.reviewed = x;}
	
	// constructor
	public LocationDialog(Frame frame, Location place) {
		// set up frame
		super(frame, "Now viewing: "+place.getName(), true);			
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		this.place = place;
		initComponents();
	}
	
	public void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// add text components
		JPanel textPanel = new JPanel(new GridLayout(8,1));	
		textPanel.add(new JLabel("Name: "+place.getName()));
		textPanel.add(new JLabel("Location: "+place.getLocation()));
		textPanel.add(new JLabel("Reservations: "+place.getReservationStatus()));
		textPanel.add(new JLabel("Price: "+place.getPrice()));
		textPanel.add(new JLabel("Stars: "+place.getStarsStr()));
		textPanel.add(new JLabel("Website: "+place.getWeb()));
		textPanel.add(new JLabel("Business Hours: "+place.getHours()));
		textPanel.add(new JLabel(""));
		gbc.gridx = 0; gbc.gridy = 0;
		add(textPanel,gbc);
				
		// add review button components
		JPanel reviewPanel = new JPanel(new GridLayout(2,1));
		if (!this.reviewed) {			
			JButton write = new JButton("Write a review");
			write.addActionListener(this);
			write.setActionCommand("write");
			reviewPanel.add(write);
		}else {
			reviewPanel.add(new JLabel(""));
		}
		JButton read = new JButton("Read reviews");
		read.addActionListener(this);
		read.setActionCommand("read");
		reviewPanel.add(read);
		
		gbc.gridx = 0; gbc.gridy = 1;
		add(reviewPanel,gbc);
		
		// add swipe button components
		JPanel swipePanel = new JPanel(new GridLayout(1,2));
		JButton left = new JButton("Swipe left");
		left.setActionCommand("swipe left");
		left.addActionListener(this);
		swipePanel.add(left);
		JButton right = new JButton("Swipe right");
		right.setActionCommand("swipe right");
		right.addActionListener(this);
		swipePanel.add(right);
		gbc.gridx = 0; gbc.gridy = 2;
		add(swipePanel,gbc);
		
		// add exit button components
		JButton exit = new JButton("Return to main menu");
		gbc.gridx = 0; gbc.gridy = 4; 
		add(exit,gbc);
		exit.addActionListener(this);
		exit.setActionCommand("exit");		
		
		pack();
		setVisible(true);
	} // initComponents function
	
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "write":
				ReviewDialog reviewDialog = new ReviewDialog(null, this.place.getName());
                reviewDialog.setVisible(true); // Show the dialog and wait

                if (reviewDialog.isSubmitted()) {
                    int rating = reviewDialog.getRating();
                    String reviewText = reviewDialog.getReviewText();

                    // Create a Review object with data from the dialog
                    Review r = new Review();
                    r.setRating(rating); // *** CORRECTED METHOD CALL ***
                    r.setText(reviewText); // This method exists in Review.java

                    try {
                        this.place.saveReviewToCSV(r);
                        this.place.updateStars(r);
                        System.out.println("Your review has been saved!");
                    } catch (IOException ex) {
                        System.out.println("Error saving review to CSV: " + ex.getMessage());
                    }
                } else {
                    System.out.println("Review cancelled by user.");
                }
				this.setNext(1);
				this.getContentPane().removeAll();
				this.reviewed = true;
				initComponents();
				this.revalidate();
				this.repaint();
				break;
			case "read":
				ReadReviewsDialog readDialog = new ReadReviewsDialog(null,place);
            	readDialog.setVisible(true);
				this.setNext(2);
				break;			
			case "swipe right":					
				SwipeRightDialog sw = new SwipeRightDialog(null);
				sw.setVisible(true);
				int choice = sw.getChoice();
				this.setNext(choice); // choice should be 3 or 4
				this.dispose();
				break;
			case "swipe left":
				this.setNext(5);
				this.dispose();
				break;
			case "exit":
				this.dispose();
				this.setNext(6);
				break;
		}
	} // actionPerformed function
} // LocationDialog class
