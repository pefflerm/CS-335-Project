package cs335_package;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;



public class Schedule {
    private List<ScheduledEvent> events;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public Schedule() {
        this.events = new ArrayList<>();
    }
    
    public static class ScheduledEvent {
        private String locationName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        
        public ScheduledEvent(String locationName, LocalDateTime startTime, LocalDateTime endTime) {
            this.locationName = locationName;
            this.startTime = startTime;
            this.endTime = endTime;
        }
        
        public String getLocationName() {
            return locationName;
        }
        
        public LocalDateTime getStartTime() {
            return startTime;
        }
        
        public LocalDateTime getEndTime() {
            return endTime;
        }
        
        @Override
        public String toString() {
            return locationName + " from " + startTime.format(formatter) + " to " + endTime.format(formatter);
        }
    }
    
    public boolean addEvent(String locationName, String startTimeStr, String endTimeStr) {
        try {
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
            
            // Check if the end time is after the start time
            if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
                System.out.println("End time must be after start time.");
                return false;
            }
            
            // Check for conflicts with existing events
            for (ScheduledEvent event : events) {
                if ((startTime.isBefore(event.endTime) && endTime.isAfter(event.startTime))) {
                    System.out.println("Time conflict with existing event: " + event);
                    return false;
                }
            }
            
            // No conflicts, add the event
            events.add(new ScheduledEvent(locationName, startTime, endTime));
            System.out.println("Event scheduled successfully: " + locationName + " from " + 
                              startTime.format(formatter) + " to " + endTime.format(formatter));
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Please use yyyy-MM-dd HH:mm format.");
            return false;
        }
    }
    
    public List<ScheduledEvent> getEvents() {
        return new ArrayList<>(events);
    }
    
    public String toCSVString() {
        StringBuilder sb = new StringBuilder();
        for (ScheduledEvent event : events) {
            sb.append(event.locationName).append(",")
              .append(event.startTime.format(formatter)).append(",")
              .append(event.endTime.format(formatter)).append("\n");
        }
        return sb.toString();
    }
    
    public static Schedule fromCSVString(String csvContent) {
        Schedule schedule = new Schedule();
        if (csvContent == null || csvContent.trim().isEmpty()) {
            return schedule;
        }
        
        String[] lines = csvContent.split("\n");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                try {
                    String locationName = parts[0];
                    LocalDateTime startTime = LocalDateTime.parse(parts[1], formatter);
                    LocalDateTime endTime = LocalDateTime.parse(parts[2], formatter);
                    schedule.events.add(new ScheduledEvent(locationName, startTime, endTime));
                } catch (DateTimeParseException e) {
                    System.out.println("Error parsing schedule entry: " + line);
                }
            }
        }
        return schedule;
    }
}


 
