package cs335_package;
import java.io.*;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReadReviewsDialog extends JDialog implements ActionListener{
	private JButton nextPage;
	private JButton prevPage;
	private JButton back;
	private JPanel panel;
	private int currentPage;
	private int totalPages;
	private File reviewFile;
	
	// getters
	public int getCurrentPage() {return this.currentPage;}
	public int getTotalPages() {return this.totalPages;}
	public File getReviewFile() {return this.reviewFile;}
	
	// setters
	public void setCurrentPage(int p) {this.currentPage = p;}
	   
	// constructor
	public ReadReviewsDialog(Frame frame, Location place){
		// set up frame
		super(frame,"Reviews for "+place.getName(),true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(frame);
        //frame.setSize(300,400);

		// add information to frame
		if (!place.getReviewsFile().isEmpty()) {
			this.reviewFile = new File(place.getReviewsFile());
			this.totalPages = (place.getNumOfReviews()/10);
			newPage();
		}else { // message displayed if no reviews exist
			JLabel message = new JLabel("No reviews to display. Be the first!");
			add(message);
			pack();
		}		
		
    } // ReadReviewsDialog constructor
	   
	public void newPage(){
		this.panel = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.BOTH;
        
		// Header
		gbc.gridx = 0; gbc.gridy = 0;
	    this.panel.add(new JLabel("Stars"),gbc);
	    gbc.gridx = 1; gbc.gridy = 0;
	    this.panel.add(new JLabel("Review"),gbc);
	    
	    // Read in stars and reviews to GridLayout panels
	    int start = getStartRow(this.getCurrentPage());
	    List<String[]> csvBody = readReviewCSV();
	    JPanel stars;
	    JPanel reviews;
	    if (hasNextPage()) {
	    	// adds a Grid Layout panel with 10 reviews
	    	stars = new JPanel(new GridLayout(10,1));
    		reviews = new JPanel(new GridLayout(10,1));
	    	for (int i=start;i<start+10;i++) {	    		
		    	stars.add(new JLabel(csvBody.get(i)[1]));
		    	reviews.add(new JLabel(csvBody.get(i)[2]));
		    }	    	
	    } else {
	    	// adds a Grid Layout panel with less than 10 reviews
	    	int numEntries = csvBody.size() - start;
	    	stars = new JPanel(new GridLayout(numEntries,1));
    		reviews = new JPanel(new GridLayout(numEntries,1));
	    	for (int i=start;i<csvBody.size();i++) {
	    		stars.add(new JLabel(csvBody.get(i)[1]));
		    	reviews.add(new JLabel(csvBody.get(i)[2]));
	    	}
	    }
	    gbc.gridx = 0; gbc.gridy = 1;
	    panel.add(stars,gbc);
	    gbc.gridx = 1; gbc.gridy = 1;
	    panel.add(reviews,gbc);

	    // Buttons
	    if (hasPrevPage()) {
	    	this.prevPage = new JButton("Previous Page");
	    	gbc.gridx = 0; gbc.gridy = 2; //gbc.ipadx = 30;
	    	panel.add(prevPage,gbc);	    	
	    	this.prevPage.addActionListener(this);
	    	this.prevPage.setActionCommand("go to prev");
	    }
	    if (hasNextPage()) {
	    	this.nextPage = new JButton("Next Page");
	    	gbc.gridx = 1; gbc.gridy = 2;
	    	panel.add(nextPage,gbc);
	    	this.nextPage.addActionListener(this);
	    	this.nextPage.setActionCommand("go to next");
	    }    
	    gbc.gridx = 2; gbc.gridy = 3;
	    this.back = new JButton("Back");
	    this.panel.add(back,gbc);
	    
	    // add formatted panel to frame
	    add(panel);

	    // Action Listener for exit button
	    this.back.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		dispose();
	    	}
	    });
	    pack();
	    
	} // initComponents function
	
	// Action Listener for previous page and next page buttons
	public void actionPerformed(ActionEvent e) {
		if ("go to prev".equals(e.getActionCommand())) {
			setCurrentPage(getCurrentPage()-1);												
		} else {
			setCurrentPage(getCurrentPage()+1);
		}
		this.getContentPane().removeAll();
		newPage();
		this.revalidate();
		this.repaint();		
	} // actionPerformed function
	
	public int getStartRow(int pageNum) {
		// returns row to start in CSV for a given page number
		// ex. page 3 corresponds to rows 31-40
		return pageNum*10 + 1;
	} // getStartRow function
	
	public List<String[]> readReviewCSV(){
		List<String[]> csvBody = null;
		try {
			CSVReader reader = new CSVReader(new FileReader(this.getReviewFile()));
			csvBody = reader.readAll();
			reader.close();
		} catch(IOException|CsvException e) {
			e.printStackTrace();
		}
		return csvBody;
	} // readReviewCSV function
	
	public boolean hasNextPage() {
		return this.currentPage!=this.totalPages;
	}
	
	public boolean hasPrevPage() {
		return this.currentPage>0;
	}
} // ReadReviewsDialog class
