/* Class to represent a student's booking of a listing. Using the state pattern, behavior is
determined by the current booking state. On creation, the booking is in the requested state. */
public class Booking {

    public enum BookingStateType {REQUESTED, APPROVED, CLOSED};
    
    // Attributes
    private BookingState state;
    private UserStudent student;
    private Listing listing;
    private DateRange period;
    
    // Constructor
    public Booking(Listing listing, UserStudent student, DateRange period) {
        this.state = new BookingRequested(this);
        this.student = student;
        this.listing = listing;
        this.period = period;
    }

    // Getters
    public BookingState getState() {
        return state;
    }
    public UserStudent getStudent() {
        return student;
    }
    public Listing getListing() {
        return listing;
    }
    public DateRange getPeriod() {
        return period;
    }

    // Setters
    public void setState(BookingState state) {
        this.state = state;
    }

    // Method to approve a booking
    public void approve() {
        state.approve();
    }

    // Method to reject a booking
    public void reject() {
        state.reject();
    }

    // Method to cancel a booking
    public void cancel() {
        state.cancel();
    }
}
