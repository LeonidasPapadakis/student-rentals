/* Class to represent a requested booking. Requested bookings can only be approved or rejected. */
public class BookingRequested implements BookingState {

    private Booking booking;

    public BookingRequested(Booking booking) {
        this.booking = booking;
    }

    @Override
    public Booking.BookingStateType getStateType() {
        return Booking.BookingStateType.REQUESTED;
    }

    @Override
    public String getStatus() {
        return "Requested, awaiting Home Owner's approval.";
    }

    @Override
    public void approve() {
        
        // Remove booking period from listing's availabilities
        booking.getListing().removeAvailability(booking.getPeriod());

        // Approve booking
        booking.setState(new BookingApproved(booking));
    }

    @Override
    public void reject() {

        // Close booking
        booking.setState(new BookingClosed());
    }

    @Override
    public void cancel() {
        throw new IllegalStateException("Can't cancel a requested booking.");        
    }
}