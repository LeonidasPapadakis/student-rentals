/* Factory class to create an appropriate menu provider for the signed in user */
public class MenuProviderFactory {
    
    // Prevent instantiation
    private MenuProviderFactory() {}

    /**
    * Factory method to create a menu provider for the signed in user
    * @param app the app control object
    * @param userManager the user manager
    * @param listingManager the listing manager
    * @param bookingManager the booking manager
    * @return the menu provider created
    */
    public static MenuProvider create(AppControl app, UserManager userManager, 
                                ListingManager listingManager, BookingManager bookingManager) {

        // Get signed in user
        User user = app.getSignedInUser();

        // Create student menu provider
        if (user instanceof UserStudent student) {
            return new MenuProviderStudent(student, app, userManager, listingManager, bookingManager);
        }
        // Create home owner menu provider
        else if (user instanceof UserHomeOwner homeOwner) {
            return new MenuProviderHomeOwner(homeOwner, app, userManager, listingManager, bookingManager);
        }
        // Create default menu provider
        else {
            return new MenuProviderDefault(app, userManager);
        }
    }
}
