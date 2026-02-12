/* Class to represent a closed booking. Closed bookings cannot be approved, rejected, or cancelled. */
public class BookingClosed implements BookingState {

    @Override
    public Booking.BookingStateType getStateType() {
        return Booking.BookingStateType.CLOSED;
    }

    @Override
    public String getStatus() {
        return "Closed, no longer available.";
    }

    @Override
    public void approve() {
        throw new IllegalStateException("Can't approve a closed booking.");
    }

    @Override
    public void reject() {
        throw new IllegalStateException("Can't reject a closed booking.");
    }

    @Override
    public void cancel() {
        throw new IllegalStateException("Booking is already closed.");
    }
}