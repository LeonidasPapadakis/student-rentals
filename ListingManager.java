import java.util.HashMap;
import java.util.Set;
import java.time.LocalDate;
import java.util.ArrayList;

/* Class to manage listings. Uses the singleton pattern to ensure that all components of the system have 
access to the same set of listings. Responsible for creating, storing, and retreiving listing objects. */
public class ListingManager {

    // Hashmap to store seperate BSTs for each city
    private HashMap<String, ListingBinarySearchTree> listings;

    // Singleton instance
    private static ListingManager instance = null;

    // Private constructor
    private ListingManager() {

        // Create a new bst for each city
        listings = new HashMap<>();
        for (String city : AppConstants.BRITISH_CITIES) {
            listings.put(city, new ListingBinarySearchTree());
        }
    }
    
    // Get singleton instance
    public static ListingManager getInstance() {
        if (instance == null) {
            instance = new ListingManager();
        }
        return instance;
    }

    /**
    * Listing factory method
    * @param homeOwner the homeowner creating the listing
    * @param title the title of the listing
    * @param city the city of the listing
    * @param streetAddress the street address of the listing
    * @param pricePerMonth the price per month of the listing
    * @param description the description of the listing
    * @param availabilties the availability date ranges of the listing
    * @param images the image links of the listing
    * @return the created listing
    */
    public Listing createListing(UserHomeOwner homeOwner, String title, String city, String streetAddress, double pricePerMonth, 
                    String description, DateRange[] availabilties, String[] images) {
        
        // Create new listing and add to manager
        Listing newListing = new Listing(homeOwner, title, city, streetAddress, pricePerMonth, description, availabilties, images);
        addListing(newListing);

        return newListing;
    }

    /**
    * Add a listing to the manager
    * @param newListing the listing to add
    */
    public void addListing(Listing newListing) {

        // Get listing details
        String city = newListing.getCity();
        UserHomeOwner homeOwner = newListing.getHomeOwner();

        // Add listing to its city's BST
        listings.get(city).addListing(newListing);

        // Add listing to homeowner's listings
        homeOwner.addListing(newListing);
    }

    /**
    * Remove a listing from the manager
    * @param listing the listing to remove
    */
    public void removeListing(Listing listing) {

        // Get listing details
        String city = listing.getCity();
        UserHomeOwner homeOwner = listing.getHomeOwner();

        // Remove listing from its city's BST
        listings.get(city).removeListing(listing);

        // Remove listing from homeowner's listings
        homeOwner.removeListing(listing);
    }

    /**
    * Method to search for listings within the manager
    * @param cities the cities to search in
    * @param minPrice the minimum price filter
    * @param maxPrice the maximum price filter
    * @param startDate the start date filter
    * @param endDate the end date filter
    * @return the results of the search
    */
    public Listing[] searchListings(Set<String> cities, Double minPrice, Double maxPrice, LocalDate startDate, LocalDate endDate) {

        ListingBinarySearchTree tree; 
        ArrayList<Listing> results = new ArrayList<>();
        ArrayList<Listing> treeResults;

        // If no cities selected, search all cities
        if (cities == null || cities.isEmpty()) {
            cities = AppConstants.BRITISH_CITIES;
        }

        // If no price range selected, search all prices
        if (minPrice == null) {
            minPrice = 0.0;
        }
        if (maxPrice == null) {
            maxPrice = Double.MAX_VALUE;
        }

        // Search for listings in each city
        for (String city : cities) {

            // Get the BST for the city
            tree = listings.get(city);

            // Search for listings in the BST
            treeResults = tree.searchListings(minPrice, maxPrice, startDate, endDate);
            if (treeResults != null) {
                results.addAll(treeResults);
            }
        }
        return results.toArray(new Listing[0]);
    }
}