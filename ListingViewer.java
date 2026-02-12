// Static class to print listing attributes to console
public class ListingViewer {

    // Prevent instantiation
    private ListingViewer() {}

    /**
    * Method to print the listing's attributes to the console
    * @param listing the listing to display
    */
    public static void display(Listing listing) {

        // Get listing attributes
        String title = listing.getTitle();
        String city = listing.getCity();
        String streetAddress = listing.getStreetAddress();
        double pricePerMonth = listing.getPricePerMonth();
        String description = listing.getDescription();
        DateRange[] availabilities = listing.getAvailabilities();
        String[] images = listing.getImages();

        // Format city
        String formattedCity = city.substring(0, 1).toUpperCase() + city.substring(1);

        System.out.println("\nTitle: " + title);
        System.out.println("City: " + formattedCity);
        System.out.println("Street Address: " + streetAddress);
        System.out.println("Price Per Month: £" + pricePerMonth);
        System.out.println("Description: " + description);
        System.out.println("Availability Periods:");
        for (int i = 0; i < availabilities.length; i++) {
            System.out.println("- " + availabilities[i].getStart().format(DateRange.getFormatter()) 
                                + " to " + availabilities[i].getEnd().format(DateRange.getFormatter()));
        }
        System.out.println("Images:");
        for (String image : images) {
            System.out.println("- " + image);
        }
        System.out.println(AppConstants.TEXT_SPACER);
    }

    /**
    * Method to print a minimal view of a listing to the console
    * @param listing the listing to display
    */
    public static void displayMinimal(Listing listing) {

        // Get listing attributes
        String title = listing.getTitle();
        String city = listing.getCity();
        String streetAddress = listing.getStreetAddress();
        DateRange[] availabilities = listing.getAvailabilities();

        // Format city
        String formattedCity = city.substring(0, 1).toUpperCase() + city.substring(1);

        System.out.println("\nTitle: " + title);
        System.out.println("City: " + formattedCity);
        System.out.println("Street Address: " + streetAddress);
        System.out.println("Availability Periods:");
        for (int i = 0; i < availabilities.length; i++) {
            System.out.println("- " + availabilities[i].getStart().format(DateRange.getFormatter()) 
                                + " to " + availabilities[i].getEnd().format(DateRange.getFormatter()));
        }
    }
    
    /**
    * Method to print multiple listings to the console
    * @param listings the array of listings to display
    */
    public static void displayMultiple(Listing[] listings) {

        // Loop through listings
        for (int index = 0; index < listings.length; index++) {

            // Display listing
            System.out.println("\n(Listing " + (index + 1) + "):");
            display(listings[index]);
        }
    }
}
