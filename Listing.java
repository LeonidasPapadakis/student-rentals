import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/* Class to represent a room listing */
public class Listing {

    // Attributes
    private UserHomeOwner homeOwner;
    private String title;
    private String city;
    private String streetAddress;
    private double pricePerMonth;
    private String description;
    private ArrayList<DateRange> availabilities;
    private String[] images;

    // Constructor
    public Listing(UserHomeOwner homeOwner, String title, String city, String streetAddress, double pricePerMonth, 
                    String description, DateRange[] availabilities, String[] images) {
        this.homeOwner = homeOwner;
        this.title = title;
        setCity(city); // Store city in lowercase
        this.streetAddress = streetAddress;
        this.pricePerMonth = pricePerMonth;
        this.description = description;
        setAvailabilities(availabilities); // Convert array to arrayList
        this.images = images;
    }

    // Getters
    public UserHomeOwner getHomeOwner() {
        return homeOwner;
    }
    public String getTitle() {
        return title;
    }
    public String getCity() {
        return city;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public double getPricePerMonth() { 
        return pricePerMonth; 
    }
    public String getDescription() {
        return description;
    }
    public DateRange[] getAvailabilities() {
        return availabilities.toArray(new DateRange[0]);
    }
    public String[] getImages() {
        return images;
    }

    // Setters
    public void setHomeOwner(UserHomeOwner homeOwner) {
        this.homeOwner = homeOwner;
    }
    public void setTitle(String title) { 
        this.title = title; 
    }
    public void setCity(String city) { 
        
        // Store city in lowercase
        this.city = city.toLowerCase();
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public void setPricePerMonth(double pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAvailabilities(DateRange[] availabilities) {

        // Convert array to arrayList
        this.availabilities = new ArrayList<> (Arrays.asList(availabilities));
    }
    public void setImages(String[] images) {
        this.images = images;
    }

    /**
    * Remove availability for a booking period
    * @param period the period to remove from availabilities
    */
    public void removeAvailability(DateRange period) {

        DateRange availability;

        // Loop through availabilities
        for(int index = 0; index < availabilities.size(); index++) {

            availability = availabilities.get(index);

            // If period fits inside availability, update availability to exclude period
            if (period.fitsIn(availability)) {
                
                // If start dates align, update start date
                if (availability.getStart().equals(period.getStart())) {
                    availability.setStart(period.getEnd());
                }

                // If end dates align, update end date
                else if (availability.getEnd().equals(period.getEnd())) {
                    availability.setEnd(period.getStart());
                }

                // If period is within availability, split availability
                else {
                    availabilities.add(new DateRange(period.getEnd(), availability.getEnd()));
                    availability.setEnd(period.getStart());
                }
            }
        }
    }

    /**
    * Add availability for a booking period
    * @param period the period to insert back into availabilities
    */
    public void addAvailability(DateRange period) {

        DateRange availability;
        LocalDate periodStart = period.getStart();
        LocalDate periodEnd = period.getEnd();
        LocalDate availabilityStart;
        LocalDate availabilityEnd;
        DateRange startRange = null;
        DateRange endRange = null;

        // Loop through availabilities
        for(int i = 0; i < availabilities.size(); i++) {
            availability = availabilities.get(i);

            // Get availability start and end
            availabilityStart = availability.getStart();
            availabilityEnd = availability.getEnd();

            // If period is not already in covered by availability
            if (!period.fitsIn(availability)) {

                // If period start is within availability, record as start range
                if ((availabilityStart.isBefore(periodStart)
                        && availabilityEnd.isAfter(periodStart))
                        || availabilityEnd.equals(periodStart)) {
                    startRange = availability;
                }

                // If period end is within availability, record as end range
                else if ((availabilityStart.isBefore(periodEnd)
                        && availabilityEnd.isAfter(periodStart))
                        || availabilityStart.equals(periodEnd)) {
                    endRange = availability;
                }
            }
        }
        // If only start range found, update its end
        if (startRange != null && endRange == null) {
            startRange.setEnd(periodEnd);
        }

        // If only end range found, update its start
        else if (startRange == null && endRange != null) {
            endRange.setStart(periodStart);
        }

        // If both ranges found, join into one availability
        else if (startRange != null && endRange != null) {
            startRange.setEnd(endRange.getEnd());
            availabilities.remove(endRange);
        }

        // If period doesn't overlap with any availability, add to the end of availabilities
        else {
            System.out.println("none");
            availabilities.add(period);
        }
    }

    /**
    * Check availability for a start and end date
    * @param start the start date
    * @param end the end date
    * @return true if available, false otherwise
    */
    public boolean checkAvailability(LocalDate start, LocalDate end) {

        // If no date filter, return true
        if (start == null && end == null) {
            return true;
        }

        // Repeat date if one is missing
        else if (start == null) {
            start = end;
        }
        else if (end == null) {
            end = start;
        }
    
        // Check availability for date range
        return checkAvailability(new DateRange(start, end));        
    }

    /**
    * Check availability for a date range
    * @param period the date range
    * @return true if available, false otherwise
    */
    public boolean checkAvailability(DateRange period) {
        
        // Loop through availability date ranges
        for (int i = 0; i < availabilities.size(); i++) {

            // Check if date range is available
            if (period.fitsIn(availabilities.get(i))) {
                return true;
            }
        }

        // Date range not available
        return false;
    }
}
