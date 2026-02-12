public class Main {

    // Managers
    private static UserManager userManager;
    private static ListingManager listingManager;
    private static BookingManager bookingManager;

    // Create example values for testing and demonstration
    @SuppressWarnings("unused")
    private static void createExampleData() {

        // Create example HomeOwners
        UserHomeOwner homeOwner1 = (UserHomeOwner) userManager.registerUser(
            User.UserType.HOME_OWNER,
            "Lola Lampshade",
            "lolal@gmail.com", 
            "password123#"
        );
        UserHomeOwner homeOwner2 = (UserHomeOwner) userManager.registerUser(
            User.UserType.HOME_OWNER,
            "Paul Porridge",
            "paulp@gmail.com", 
            "password123#"
        );
        UserHomeOwner homeOwner3 = (UserHomeOwner) userManager.registerUser(
            User.UserType.HOME_OWNER,
            "Jackson Juniper",
            "jacksonj@gmail.com", 
            "password123#"
        );
        
        // Create example listings
        Listing listing1 = listingManager.createListing(
            homeOwner1,
            "Cottage room", 
            "Cardiff", 
            "43 Cottage Lane", 
            600,
            "Ensuite room in a little cottage.",
            DateRange.parseMultiple(new String[] {"01/01/2026-01/01/2027", "01/01/2028-01/01/2029"}), 
            new String[] {"https://images.com/cottage.jpg"}
        );
        Listing listing2 = listingManager.createListing(
            homeOwner1,
            "Castle room", 
            "Cardiff", 
            "1 Castle Street", 
            2000,
            "Master bedroom in a castle. Room has a balcony, ensuite bathroom and even its own butler.",
            DateRange.parseMultiple(new String[] {"01/02/2026-01/07/2026", "01/03/2028-01/09/2029"}), 
            new String[] {"https://images.com/castle.png"}
        );
        Listing listing3 = listingManager.createListing(
            homeOwner2,
            "Small room in the heart of the city", 
            "London", 
            "12 Church Street", 
            500,
            "Excellent room with its own bathroom, access to a shared kitchen and communal area",
            DateRange.parseMultiple(new String[] {"01/01/2026-01/01/2028"}), 
            new String[] {"https://images.com/room.jpg", "https://images.com/kitchen.jpg"}
        );
        Listing listing4 = listingManager.createListing(
            homeOwner2,
            "Penthouse apartment right next to university", 
            "Bristol", 
            "5 Park Road", 
            1200,
            "Large penthouse with a view of the city, plenty of facilities available",
            DateRange.parseMultiple(new String[] {"01/04/2027-01/04/2028"}), 
            new String[] {"https://images.com/penthouse.jpg"}
        );
        Listing listing5 = listingManager.createListing(
            homeOwner3,
            "Dim room in a basement flat", 
            "Exeter", 
            "8 Dog Street", 
            400,
            "Large room with shared facilities.",
            DateRange.parseMultiple(new String[] {"01/01/2029-01/01/2030"}), 
            new String[] {"https://images.com/dimRoom.bmp"}
        );
        Listing listing6 = listingManager.createListing(
            homeOwner3,
            "Very lovely room at the top of a skyscraper", 
            "Manchester", 
            "82 Highrise Road", 
            800,
            "Modern bedroom with ensuite bathroom",
            DateRange.parseMultiple(new String[] {"01/04/2026-01/10/2026", "01/04/2027-01/04/2028"}), 
            new String[] {"https://images.com/skyscraper.jpg"}
        );
    }

    public static void main(String[] args) {

        // Get singleton manager instances
        userManager = UserManager.getInstance();
        listingManager = ListingManager.getInstance();
        bookingManager = BookingManager.getInstance();

        // Create new app session
        AppControl app = new Application(userManager, listingManager, bookingManager);

        // Create example data
        createExampleData();

        // Start application
        app.start();
    }
}
