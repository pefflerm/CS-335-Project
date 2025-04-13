package cs335_package;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class ScheduleDialog extends JDialog {
    private JDateChooser startDateChooser;
    private JSpinner startHourSpinner;
    private JSpinner startMinuteSpinner;
    private JDateChooser endDateChooser;
    private JSpinner endHourSpinner;
    private JSpinner endMinuteSpinner;
    private Date selectedStartDate;
    private Date selectedEndDate;
    private boolean confirmed = false;

    public ScheduleDialog(Frame owner, String locationName) {
        super(owner, "Schedule Visit to " + locationName, true); // true for modal
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Start Date/Time ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        add(new JLabel("Start Date:"), gbc);

        startDateChooser = new JDateChooser();
        startDateChooser.setDate(new Date()); // Default to today
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3;
        add(startDateChooser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Start Time (HH:mm):"), gbc);

        startHourSpinner = new JSpinner(new SpinnerNumberModel(12, 0, 23, 1)); // 0-23 hours
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 1;
        add(startHourSpinner, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel(":"), gbc);

        startMinuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1)); // 0-59 minutes
        // Set custom editor to format minutes with leading zero
        JSpinner.NumberEditor startMinuteEditor = new JSpinner.NumberEditor(startMinuteSpinner, "00");
        startMinuteSpinner.setEditor(startMinuteEditor);
        gbc.gridx = 3; gbc.gridy = 1; gbc.gridwidth = 1;
        add(startMinuteSpinner, gbc);


        // --- End Date/Time ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        add(new JLabel("End Date:"), gbc);

        endDateChooser = new JDateChooser();
        endDateChooser.setDate(new Date()); // Default to today
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 3;
        add(endDateChooser, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        add(new JLabel("End Time (HH:mm):"), gbc);

        endHourSpinner = new JSpinner(new SpinnerNumberModel(13, 0, 23, 1)); // Default to 1 hour later
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        add(endHourSpinner, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1;
        add(new JLabel(":"), gbc);

        endMinuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        // Set custom editor to format minutes with leading zero
        JSpinner.NumberEditor endMinuteEditor = new JSpinner.NumberEditor(endMinuteSpinner, "00");
        endMinuteSpinner.setEditor(endMinuteEditor);
        gbc.gridx = 3; gbc.gridy = 3; gbc.gridwidth = 1;
        add(endMinuteSpinner, gbc);

        // --- Buttons ---
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAndSetDates()) {
                    confirmed = true;
                    setVisible(false); // Close the dialog
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                setVisible(false); // Close the dialog
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        pack(); // Size the dialog based on components
        setLocationRelativeTo(owner); // Center relative to the owner window (can be null)
    }

    private boolean validateAndSetDates() {
        Date startDate = startDateChooser.getDate();
        Date endDate = endDateChooser.getDate();

        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int startHour = (Integer) startHourSpinner.getValue();
        int startMinute = (Integer) startMinuteSpinner.getValue();
        int endHour = (Integer) endHourSpinner.getValue();
        int endMinute = (Integer) endMinuteSpinner.getValue();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.HOUR_OF_DAY, startHour);
        startCal.set(Calendar.MINUTE, startMinute);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        selectedStartDate = startCal.getTime();

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.HOUR_OF_DAY, endHour);
        endCal.set(Calendar.MINUTE, endMinute);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        selectedEndDate = endCal.getTime();

        // --- Add this check ---
        Date now = new Date(); // Get the current date and time
        if (selectedStartDate.before(now)) {
            JOptionPane.showMessageDialog(this, "Start date/time cannot be in the past.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // --- End of added check ---

        if (!selectedEndDate.after(selectedStartDate)) {
            JOptionPane.showMessageDialog(this, "End date/time must be after start date/time.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    // Public methods to get the results
    public boolean isConfirmed() {
        return confirmed;
    }

    public Date getSelectedStartDate() {
        return selectedStartDate;
    }

    public Date getSelectedEndDate() {
        return selectedEndDate;
    }
}
