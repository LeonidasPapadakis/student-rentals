/* Interface to represent the state of a booking. Uses the state pattern. */
public interface BookingState {
    public Booking.BookingStateType getStateType();
    public String getStatus();
    public void approve();
    public void reject();
    public void cancel();
}
