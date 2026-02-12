import java.time.LocalDate;
import java.util.ArrayList;

/* Class to represent a node in a listing binary search tree. */
public class ListingNode {

    // Attributes
    private Listing listing;
    private ListingNode left;
    private ListingNode right;

    // Constructor
    public ListingNode(Listing listing) {
        this.listing = listing;
        this.left = null;
        this.right = null;
    }

    // Listing getter
    public Listing getListing() {
        return listing;
    }

    /**
    * Compare two listings.
    * @param otherListing the listing to compare
    * @return the result of the comparison. Negative if this listing is less than the other listing, 
    *         0 if they are equal, and positive if this listing is greater than the other listing
    */
    private int compareTo(Listing otherListing) {
        
        // Get listing prices
        Double thisPrice = listing.getPricePerMonth();
        Double otherPrice = otherListing.getPricePerMonth();

        // Compare prices
        return Double.compare(thisPrice, otherPrice);
    }

    /**
    * Add a new listing to the current node
    * @param newListing the listing to add
    */
    public void add(Listing newListing) {

        // Compare current listing with new listing
        if (compareTo(newListing) < 0) {

            // Add to left child
            left = addToChild(newListing, left);
        } 
        else {
            // Add to right child
            right = addToChild(newListing, right);
        }
    }

    /**
    * Add a new listing to a child node
    * @param newListing the listing to add
    * @param child the child node to add to
    * @return the child node
    */
    private ListingNode addToChild(Listing newListing, ListingNode child) {

        // If child is null, add as new node
        if (child == null) {
            child = new ListingNode(newListing);
        } 
        // Otherwise, add new listing to the child
        else {
            child.add(newListing);
        }
        return child;
    }

    /**
    * Remove a listing from the current node
    * @param listing the listing to remove
    * @return the new root of the subtree
    */
    public ListingNode remove(Listing listing) {

        // Check if this node holds the listing to be removed
        if (this.listing.equals(listing)) {

            // If no children, return null
            if (left == null && right == null) {
                return null;
            }

            // If only one child, return that child
            else if (left == null) {
                return right;
            } 
            else if (right == null) {
                return left;
            }

            // If two children, replace with the closest descendant
            else {
                this.listing = findClosestDescendant().listing;
                right = right.remove(this.listing);
                return this;
            }
        }
        // If this node does not hold the listing to be removed, check children
        else {
            if (left != null && compareTo(listing) < 0) {
                left = left.remove(listing);
            }
            else if (right != null && compareTo(listing) >= 0) {
                right = right.remove(listing);
            }
            // Return this node
            return this;
        }
    }

    /**
    * Find the closest descendant of the current node
    * @return the closest descendant
    */
    private ListingNode findClosestDescendant() {

        // Start at the right child
        ListingNode closestDescendant = right;

        // Loop through left descendants until no more exist
        while (closestDescendant.left != null) {
            closestDescendant = closestDescendant.left;
        }
        
        return closestDescendant;
    }

    /**
    * Search for a listing in the tree
    * @param results the results of the search to add to
    * @param minPrice the minimum price filter
    * @param maxPrice the maximum price filter
    * @param startDate the start date filter
    * @param endDate the end date filter
    * @return the results of the search
    */
    public ArrayList<Listing> searchListings(ArrayList<Listing> results, 
                                            Double minPrice, Double maxPrice, 
                                            LocalDate startDate, LocalDate endDate) {

        // Check if listing is within price range
        if (listing.getPricePerMonth() >= minPrice 
                && listing.getPricePerMonth() <= maxPrice) {
            
            // Check if listing is available during selected dates
            if (listing.checkAvailability(startDate, endDate)) {
                
                // Add listing to results
                results.add(listing);
            }
            
            // Search left child
            if (left != null) {
                left.searchListings(results, minPrice, maxPrice, startDate, endDate);
            }

            // Search right child
            if (right != null) {
                right.searchListings(results, minPrice, maxPrice, startDate, endDate);
            }
        }
        return results;
    }
}