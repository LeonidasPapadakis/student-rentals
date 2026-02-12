/* Class to create and handle a menu with the appropriate options for a given user type and 
booking state */
public class BookingMenu {
    
    /**
    * Menu factory method for creating a menu for a given user type and booking state
    * @param app the app control object
    * @param userType the user type enum
    * @param state the booking state
    * @return the menu created
    */
    public static MenuOptions create(AppControl app, User.UserType userType, BookingState state) {
        
        MenuOptions menu;

        // Choose correct menu for state
        switch (state.getStateType()) {
            case REQUESTED -> menu = createRequestedMenu(app, userType);
            case APPROVED -> menu = createApprovedMenu(app);
            default -> menu = createClosedMenu(app);
        }
        return menu;
    }

    /**
    * Method to handle user input for a given menu
    * @param choice the option selected by the user
    * @param userType the user type enum
    * @param booking the booking object
    */
    public static void handleInput(Integer choice, User.UserType userType, Booking booking) {

        // Choose correct response for booking state
        switch (booking.getState().getStateType()) {
            case REQUESTED -> handleInputRequested(booking, choice, userType);
            case APPROVED -> handleInputApproved(booking);
            default -> {} // Do nothing
        }
    }

    /**
    * Create menu for requested booking
    * @param app the app control object
    * @param userType the user type
    * @return the menu created
    */
    private static MenuOptions createRequestedMenu(AppControl app, User.UserType userType) {
        
        String title = "";
        String[] options = {};

        // Choose menu for user type
        switch (userType) {
            case STUDENT -> {
                title = "Booking requested, awaiting home owner's approval.";
                options = new String[] {"cancel booking request"};
            }
            case HOME_OWNER -> {
                title = "Approve or reject booking";
                options = new String[] {"approve booking", "reject booking"};
            }
        }
        return new MenuOptions(app, title, options, true);
    }

    /**
    * Create menu for approved booking
    * @param app the app control object
    * @return the menu created
    */
    private static MenuOptions createApprovedMenu(AppControl app) {
        
        // Same menu for both user types
        String title = "Booking approved.";
        String[] options = new String[] {"cancel booking"};

        return new MenuOptions(app, title, options, true);
    }

    /**
    * Create menu for closed booking
    * @param app the app control object
    * @return the menu created
    */
    private static MenuOptions createClosedMenu(AppControl app) {
        
        // Same menu for both user types
        String title = "Booking closed.";
        String[] options = new String[] {};

        return new MenuOptions(app, title, options, true);
    }

    /**
    * Handle user input for requested booking
    * @param booking the booking object
    * @param choice the option selected by the user
    * @param userType the user's type
    */
    private static void handleInputRequested(Booking booking, Integer choice, User.UserType userType) {
        
        // Choose correct response for user type
        switch (userType) {
            case STUDENT -> booking.cancel();
            case HOME_OWNER -> {
                switch (choice) {
                    case 1 -> booking.approve();
                    case 2 -> booking.reject();
                }
            }
            default -> {} // Do nothing
        }
    }

    /**
    * Handle user input for approved booking
    * @param booking the booking object
    */
    private static void handleInputApproved(Booking booking) {
        
        // For both user types, the only choice is to cancel
        booking.cancel();
    }
}
