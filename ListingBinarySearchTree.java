import java.util.ArrayList;
import java.time.LocalDate;

/* Class to store listings in a binary search tree. */
public class ListingBinarySearchTree {
    
    // Root of the binary search tree
    private ListingNode root;

    // Constructor
    public ListingBinarySearchTree() {
        root = null;
    }

    /**
    * Add a new listing to the tree
    * @param newListing the listing to add
    */
    public void addListing(Listing newListing) {

        // If tree is empty, add listing as the root
        if (root == null) {
            root = new ListingNode(newListing);
        } 
        // Otherwise add listing to the tree
        else {
            root.add(newListing);
        }
    }

    /**
    * Remove a listing from the tree
    * @param listing the listing to remove
    */
    public void removeListing(Listing listing) {

        // If tree is empty do nothing
        if (root == null) {
            return;
        }
        // Otherwise remove listing from the tree
        root = root.remove(listing);
    }

    /**
    * Search for a listing in the tree
    * @param minPrice the minimum price filter
    * @param maxPrice the maximum price filter
    * @param startDate the start date filter
    * @param endDate the end date filter
    * @return the results of the search
    */
    public ArrayList<Listing> searchListings(Double minPrice, Double maxPrice, 
                                            LocalDate startDate, LocalDate endDate) {
        
        // If tree is empty, return null
        if (root == null) {
            return null;
        } 
        // Otherwise start search at the root node
        else {
            ArrayList<Listing> results = new ArrayList<>();
            results = root.searchListings(results, minPrice, maxPrice, startDate, endDate);
            return results;
        }
    }
}




