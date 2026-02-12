/* Class to represent an approved booking. Approved bookings can only be cancelled. */
public class BookingApproved implements BookingState {

    private Booking booking;

    public BookingApproved(Booking booking) {
        this.booking = booking;
    }

    @Override
    public Booking.BookingStateType getStateType() {
        return Booking.BookingStateType.APPROVED;
    }

    @Override
    public String getStatus() {
        return "Approved.";
    }

    @Override
    public void approve() {
        throw new IllegalStateException("Booking is already approved.");
    }

    @Override
    public void reject() {
        throw new IllegalStateException("Can't reject an approved booking.");
    }

    @Override
    public void cancel() {
        // Add booking period back to listing's availabilities
        booking.getListing().addAvailability(booking.getPeriod());

        // Close booking
        booking.setState(new BookingClosed());
    }
}