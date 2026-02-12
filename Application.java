/* Class to control the state of the application. Holds the current signed in user and includes methods to
sign in, sign out, and run the application.  */
public class Application implements AppControl {

    // Manager instances
    private final UserManager userManager;
    private final ListingManager listingManager;
    private final BookingManager bookingManager;

    // Signed in user
    private User signedInUser;

    // Application's running state
    private boolean running;

    // Constructor
    public Application(UserManager userManager, ListingManager listingManager, BookingManager bookingManager) {
        this.userManager = userManager;
        this.listingManager = listingManager;
        this.bookingManager = bookingManager;
        this.signedInUser = null;
    }

    // Signed in user getter
    @Override
    public User getSignedInUser() {
        return signedInUser;
    }

    // Signed in user setter
    @Override
    public void setSignedInUser(User user) {
        signedInUser = user;
    }

    // Method to stop the application
    @Override
    public void stop() {
        signedInUser = null;
        running = false;
    }

    // Method to get the application's running state
    @Override
    public boolean isRunning() {
        return running;
    }

    // Start the user interface
    @Override
    public void start() {

        MenuProvider menuProvider;
        
        // Loop menu providers until the application is closed
        running = true;
        while (running) {

            // Create menu provider
            menuProvider = MenuProviderFactory.create(this, userManager, listingManager, bookingManager);

            // Start menu
            menuProvider.start();
        }
    }
}
