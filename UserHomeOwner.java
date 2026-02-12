import java.util.ArrayList;

/* Class to represent a home owner. Extends the base user class and adds an ArrayList of the 
user's personal listings. */
public class UserHomeOwner extends User{

    // Attributes
    private ArrayList<Listing> listings;

    // Constructor
    public UserHomeOwner(String name, String emailAddress, String password){

        super(name, emailAddress, password);
        listings = new ArrayList<Listing>();
        this.type = UserType.HOME_OWNER;
    }

    /**
    * Get user's listings as array
    * @return Array of user's listings
    */
    public Listing[] getListings() {
        return listings.toArray(new Listing[0]);
    }

    /**
    * Add a listing to the user's listings
    * @param listing The listing to add
    */
    public void addListing(Listing listing) {
        listings.add(listing);
    }

    /**
    * Remove a listing from the user's listings
    * @param listing The listing to remove
    */
    public void removeListing(Listing listing) {
        listings.remove(listing);
    }
}