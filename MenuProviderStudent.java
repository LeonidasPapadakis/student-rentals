/* MenuProvider class for student users. Students can search for listings, request bookings, view their bookings, and sign out */
public class MenuProviderStudent extends MenuProviderUser{

    private UserStudent student;

    // Constructor
    public MenuProviderStudent(UserStudent student, AppControl app, UserManager userManager, 
                                ListingManager listingManager, BookingManager bookingManager) {
        
        super(app, userManager, listingManager, bookingManager);
        this.student = student;
    }

    // Start the menus
    @Override
    public void start() {

        // Create main options menu
        String title = "Student Menu for " + student.getName();
        String[] options = {"search listings", "view bookings", "sign out"};
        MenuOptions menu = new MenuOptions(app, title, options, false);

        // Start menu and get user input
        Integer choice = menu.start();

        // Handle user input
        if (choice == null) {return;}
        switch (choice) {
            case 1 -> searchListings();
            case 2 -> viewBookings();
            case 3 -> signOut();
        } 
    }

    // Method for viewing user's bookings
    @Override
    protected void viewBookings() {

        Booking[] bookings;
        MenuOptions selectBookingMenu;
        String selectBookingTitle  = "Select a booking.";;
        String[] selectBookingOptions;
        Integer selectBookingChoice;

        // Loop until user exits
        while (app.isRunning()) {

            // Get user's bookings
            bookings = student.getBookings();
            selectBookingOptions = new String[bookings.length];

            // Display bookings if they exist
            if (bookings.length > 0) {

                // Loop through bookings
                for (int index = 0; index < bookings.length; index++) {

                    // Display booking
                    System.out.println("\n(Booking " + (index + 1) + "):");
                    BookingViewer.display(bookings[index], User.UserType.STUDENT);

                    // Add booking to menu options
                    selectBookingOptions[index] = "select booking " + (index + 1);
                }
            }
            else {
                System.out.println("No bookings yet.");
            }
            System.out.println(AppConstants.TEXT_SPACER);

            // Create menu
            selectBookingMenu = new MenuOptions(app, selectBookingTitle, selectBookingOptions, true);

            // Start selection menu and get user input
            selectBookingChoice = selectBookingMenu.start();
            if (selectBookingChoice == null) {return;};

            // Modify selected booking
            modifyBooking(bookings[selectBookingChoice - 1], User.UserType.STUDENT);
        }
    }

    /**
    * Handle results generated from a search. After searching for listings, students can request a booking
    * @param results the results of the search
    */
    @Override
    protected void handleSearchResults(Listing[] results) {

        // Declare menu and user input variables
        String selectListingTitle = "Select a listing to book.";
        String[] selectListingOptions = new String[results.length];
        MenuOptions selectListingMenu;
        Integer selectListingChoice;
        Listing selectedListing;

        // Create menu
        for (int i = 0; i < results.length; i++) {
            selectListingOptions[i] = "book listing " + (i + 1);
        }
        selectListingMenu = new MenuOptions(app, selectListingTitle, selectListingOptions, true);

        // Select listing to book
        selectListingChoice = selectListingMenu.start();
        if (selectListingChoice == null) {return;}

        // Get selected listing and request a booking
        selectedListing = results[selectListingChoice - 1];
        requestBooking(selectedListing);
    }

    /**
    * Request a booking for a listing
    * @param targetListing the listing to book
    */
    private void requestBooking(Listing targetListing) {

        // Show target listing
        ListingViewer.display(targetListing);

        // Create menu
        String bookListingTitle = "Request a booking period in the format " + AppConstants.DATE_RANGE_FORMAT + ".";
        String bookListingFieldName = "desired booking date range";
        MenuField bookListingMenu = new MenuField(app, bookListingTitle, bookListingFieldName, 
                                                ValidatorChainFactory.buildDateRangeValidator(), true);
        String requestedDateRange;
        
        // Get input for booking the listing
        requestedDateRange = bookListingMenu.start();
        if (requestedDateRange == null) {return;}

        // Request booking
        bookingManager.requestBooking(targetListing, student, DateRange.parse(requestedDateRange));
    }
}