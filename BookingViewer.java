/* Static class to print booking details to console */
public class BookingViewer {

    // Prevent instantiation
    private BookingViewer() {}

    /**
    * Method to display booking
    * @param booking the booking
    * @param userType the user type
    */
    public static void display(Booking booking, User.UserType userType) {
        switch (userType) {
            case STUDENT -> displayForStudent(booking);
            case HOME_OWNER -> displayForHomeOwner(booking);
        }
    }

    /**
    * Method to display booking for student
    * @param  booking the booking to display
    */
    private static void displayForStudent(Booking booking) {

        // Get attributes
        Listing listing = booking.getListing();
        UserHomeOwner homeOwner = listing.getHomeOwner();
        String listingTitle = listing.getTitle();
        String city = listing.getCity();
        String streetAddress = listing.getStreetAddress();
        String stateName = booking.getState().getStatus();
        String homeOwnerName = homeOwner.getName();
        String homeOwnerEmail = homeOwner.getEmailAddress();
        String start = booking.getPeriod().getStart().format(DateRange.getFormatter());
        String end = booking.getPeriod().getEnd().format(DateRange.getFormatter());

        // Format city
        String formattedCity = city.substring(0, 1).toUpperCase() + city.substring(1);;

        // Display attributes
        System.out.println("\nBooking Status: " + stateName);
        System.out.println("Listing Title: " + listingTitle);
        System.out.println("Listing City: " + formattedCity);
        System.out.println("Listing Street Address: " + streetAddress);
        System.out.println("Home Owner Name: " + homeOwnerName);
        System.out.println("Home Owner Email: " + homeOwnerEmail);
        System.out.println("Booking Period: " + start + " to " + end); 
    }

    /**
    * Method to display booking for home owner
    * @param booking the booking to display
    */
    private static void displayForHomeOwner(Booking booking) {

        // Get attributes
        UserStudent student = booking.getStudent();
        String stateName = booking.getState().getStatus();
        String studentName = student.getName();
        String studentEmail = student.getEmailAddress();
        String start = booking.getPeriod().getStart().format(DateRange.getFormatter());
        String end = booking.getPeriod().getEnd().format(DateRange.getFormatter());

        // Display attributes
        System.out.println("\nBooking Status: " + stateName);
        System.out.println("Student Name: " + studentName);
        System.out.println("Student Email: " + studentEmail);
        System.out.println("Booking Period: " + start + " to " + end); 
    }
}
