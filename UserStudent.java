import java.util.ArrayList;

/* Class to represent a student user. Extends the base user class and adds an ArrayList of the user's bookings */
public class UserStudent extends User{

    // Attributes
    private ArrayList<Booking> bookings;

    // Constructor
    public UserStudent(String name, String emailAddress, String password){
        
        super(name, emailAddress, password);
        this.bookings = new ArrayList<Booking>();
        this.type = UserType.STUDENT;
    }

    /**
    * Get bookings as an array
    * @return Array of user's bookings
    */
    public Booking[] getBookings(){
        return bookings.toArray(new Booking[0]);
    }

    /**
    * Add a new booking to the user's booking list
    * @param booking The booking to add
    */
    public void addBooking(Booking booking){
        bookings.add(booking);
    }
}