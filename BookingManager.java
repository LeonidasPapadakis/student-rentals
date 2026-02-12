import java.util.ArrayList;
import java.util.HashMap;

/* Class to manage bookings. Responsible for creating, storing, and retreiving booking objects.
Uses the singleton pattern to ensure that all components of the system have access to the same set of bookings. */
public class BookingManager {
    
    // Hashmap of booking lists for listings
    private HashMap<Listing, ArrayList<Booking>> bookings;

    // Singleton instance
    private static BookingManager instance = null;

    // Private constructor
    private BookingManager() {
        bookings = new HashMap<>();
    }

    // Get singleton instance
    public static BookingManager getInstance() {
        if (instance == null) {
            instance = new BookingManager();
        }
        return instance;
    }

    /**
    * Get bookings from the manager for a listing
    * @param listing the listing to get bookings for
    * @return an array of bookings
    */
    public Booking[] getBookings(Listing listing) {
        
        // If listing exists in hashmap, return its booking list
        if (bookings.containsKey(listing)) {
            return bookings.get(listing).toArray(new Booking[0]);
        }
        return new Booking[0];
    }

    /**
    * Request a booking for a listing
    * @param listing the listing to book
    * @param student the student making the booking
    * @param period the desired period of the booking
    * @return the created booking
    */
    public Booking requestBooking(Listing listing, UserStudent student, DateRange period) {

        Booking newBooking;

        // Check that requested period is available
        if (!listing.checkAvailability(period)) {
            return null;
        }

        // Create new booking
        newBooking = createBooking(listing, student, period);

        // If listing doesn't exist in hashmap, add it
        if (!bookings.containsKey(listing)) {
            bookings.put(listing, new ArrayList<Booking>());
        }
        
        // Add booking to listing's booking list and student's booking list
        bookings.get(listing).add(newBooking);
        student.addBooking(newBooking);

        return newBooking;
    }

    /**
    * Booking factory method
    * @param listing the listing to book
    * @param student the student making the booking
    * @param period the desired period of the booking
    * @return the created booking
    */
    private Booking createBooking(Listing listing, UserStudent student, DateRange period) {
        return new Booking(listing, student, period);
    }
}
