import java.util.ArrayList;
import java.util.Arrays;

/* MenuProvider class for home owners. Home owners can view their listings and bookings, create a new listing, 
search for listings, and sign out */
class MenuProviderHomeOwner extends MenuProviderUser{
    
    private UserHomeOwner homeOwner;

    // Constructor
    public MenuProviderHomeOwner(UserHomeOwner homeOwner, AppControl app, UserManager userManager, 
                                ListingManager listingManager, BookingManager bookingManager) {
        super(app, userManager, listingManager, bookingManager);
        this.homeOwner = homeOwner;
    }

    // Display HomeOwner menu and handle user input
    @Override
    public void start() {

        // Create main options menu
        String title = "HomeOwner Menu for " + homeOwner.getName();
        String[] options = {"view your listings", "view your bookings", "create a new listing", "search listings", "sign out"};
        MenuOptions menu = new MenuOptions(app, title, options, false);

        // Start menu and get user input
        Integer choice = menu.start();
        if (choice == null) {return;}
         
        // Handle user input
        switch (choice) {
            case 1 -> viewListings();
            case 2 -> viewBookings();
            case 3 -> createListing();
            case 4 -> searchListings();
            case 5 -> signOut();
        }
    }

    /**
    * Handle results generated from a search. HomeOwners can view search results only
    * @param results the results of the search
    */
    @Override
    public void handleSearchResults(Listing[] results) {
        
        // Create empty menu
        String menuTitle = "Browse search results.";
        MenuOptions emptyMenu = new MenuOptions(app, menuTitle, new String[0], true);
        Integer choice;

        // Start menu and get user input
        choice = emptyMenu.start();
        if (choice == null) {return;}
    }

    // Method for viewing the user's bookings
    @Override
    protected void viewBookings() {

        Listing[] listings;
        Booking[] listingBookings;
        ArrayList<Booking> bookings;
        MenuOptions selectBookingMenu;
        String selectBookingTitle  = "Select a booking.";;
        String[] selectBookingOptions;
        Integer selectBookingChoice;

        // Loop until user exits
        while (app.isRunning()) {
            
            bookings = new ArrayList<Booking>();

            // Get user's listings
            listings = homeOwner.getListings();

            // Display user's listings
            if (listings.length == 0) {
                System.out.println("No listings yet.");
            }
            else{
                // Loop through users listings
                for (int listingIndex = 0; listingIndex < listings.length; listingIndex++) {

                    // Display listing
                    System.out.println("\n(Listing " + (listingIndex + 1) + "):");
                    ListingViewer.displayMinimal(listings[listingIndex]);

                    // Get listing's bookings
                    listingBookings = (bookingManager.getBookings(listings[listingIndex]));

                    // Display bookings if they exist
                    System.out.println("\nBookings for (Listing " + (listingIndex + 1) + "):");
                    if (listingBookings.length > 0) {

                        // Loop through bookings
                        for (int bookingIndex = 0; bookingIndex < listingBookings.length; bookingIndex++) {

                            // Display booking
                            System.out.println("\n(Booking " + (bookings.size() + bookingIndex + 1) + "):");
                            BookingViewer.display(listingBookings[bookingIndex], User.UserType.HOME_OWNER);
                        }

                        // Add bookings to list
                        bookings.addAll(Arrays.asList(listingBookings));
                    }
                    else {
                        System.out.println("No bookings yet.");
                    }
                    System.out.println(AppConstants.TEXT_SPACER);
                }
            }

            // Set menu
            selectBookingOptions = new String[bookings.size()];
            for (int i = 0; i < bookings.size(); i++) {
                selectBookingOptions[i] = "select booking " + (i + 1);
            }
            selectBookingMenu = new MenuOptions(app, selectBookingTitle, selectBookingOptions, true);

            // Start selection menu and get user input
            selectBookingChoice = selectBookingMenu.start();
            if (selectBookingChoice == null) {return;};

            // Modify selected booking
            modifyBooking(bookings.get(selectBookingChoice - 1), User.UserType.HOME_OWNER);
        }
    }

    // Method for viewing the user's listings
    private void viewListings() {
        
        Listing[] listings;
        MenuOptions selectListingMenu;
        String selectListingTitle  = "Select a listing to edit.";;
        String[] selectListingOptions;
        Integer selectListingChoice;
        Listing selectedListing;

        // Loop until user exits
        while (app.isRunning()) {

            // Get user's listings
            listings = homeOwner.getListings();

            // Set menu attributes
            selectListingOptions = new String[listings.length];

            // Display user's listings
            if (listings.length > 0) {
                ListingViewer.displayMultiple(listings);
            }
            else {
                System.out.println("You have no listings.");
            }

            // Create menu
            for (int i = 0; i < listings.length; i++) {
                selectListingOptions[i] = "edit listing " + (i + 1);
            }
            selectListingMenu = new MenuOptions(app, selectListingTitle, selectListingOptions, true);

            // Start selection menu and get user input
            selectListingChoice = selectListingMenu.start();
            if (selectListingChoice == null) {return;}

            // Get selected listing
            selectedListing = listings[selectListingChoice - 1];

            // Edit selected listing
            editListing(selectedListing);
        }
    }

    /**
    * Method for editing a listing
    * @param targetListing the listing to edit
    */
    private void editListing(Listing targetListing) {

        // Create menus
        String title = "Edit your listing.";
        String[] options = {"edit title", "edit city", "edit street address", "edit price per month", 
            "edit description", "edit availability dates", "edit images", "delete listing"};
        MenuOptions menu = new MenuOptions(app, title, options, true);
        Integer choice;

        // Loop until user exits
        while (app.isRunning()) {

            // Display target listing
            ListingViewer.display(targetListing);

            // Start menu and get user input for attribute to edit
            choice = menu.start();
            if (choice == null) {return;}

            // Delete listing option
            else if (choice == 8) {

                // Remove listing from manager
                listingManager.removeListing(targetListing);

                // Close associated bookings
                for (Booking booking : bookingManager.getBookings(targetListing)) {
                    booking.setState(new BookingClosed());
                }

                System.out.println("Your listing has been deleted.");
                return;
            }

            // Edit listing attribute
            editListingAttribute(targetListing, choice);
        }
    }

    /**
    * Method for editing a listing's attribute
    * @param targetListing the listing to edit
    * @param editAttributeChoice the user's choice of attribute from the given options
    */
    private void editListingAttribute(Listing targetListing, int editAttributeChoice) {

        enum ListingAttributes {TITLE, CITY, STREET_ADDRESS, PRICE_PER_MONTH, DESCRIPTION, AVAILABILITY_DATES, IMAGES};

        // Get chosen attribute
        Object edditedAttribute;
        ListingAttributes attribute = ListingAttributes.values()[editAttributeChoice - 1];
        
        // Create menu for chosen attribute
        Menu editAttributeMenu = null;
        switch (attribute) {
            case TITLE -> editAttributeMenu = getTitleMenu("Edit title");
            case CITY -> editAttributeMenu = getCityMenu("Edit city");
            case STREET_ADDRESS -> editAttributeMenu = getStreetAddressMenu("Edit street address");
            case PRICE_PER_MONTH -> editAttributeMenu = getPricePerMonthMenu("Edit price per month");
            case DESCRIPTION -> editAttributeMenu = getDescriptionMenu("Edit description");
            case AVAILABILITY_DATES -> editAttributeMenu = getAvailabilityDatesMenu("Edit availability dates");
            case IMAGES -> editAttributeMenu = getImagesMenu("Edit images");
        }
    
        // Display target listing
        ListingViewer.display(targetListing);

        // Edit selected attribute
        edditedAttribute = editAttributeMenu.start();
        if (edditedAttribute == null) {return;}

        // Update chosen attribute
        switch (attribute) {
            case TITLE -> targetListing.setTitle((String) edditedAttribute);
            case CITY -> {
                // Remove listing from manager
                listingManager.removeListing(targetListing);

                // Update city
                targetListing.setCity((String) edditedAttribute);
                
                // Add listing back to manager
                listingManager.addListing(targetListing);
            }
            case STREET_ADDRESS -> targetListing.setStreetAddress((String) edditedAttribute);
            case PRICE_PER_MONTH -> targetListing.setPricePerMonth(Double.parseDouble((String) edditedAttribute));
            case DESCRIPTION -> targetListing.setDescription((String) edditedAttribute);
            case AVAILABILITY_DATES -> targetListing.setAvailabilities(DateRange.parseMultiple((String[]) edditedAttribute));
            case IMAGES -> targetListing.setImages((String[]) edditedAttribute);
        }

        // Success message
        System.out.println("Your listing has been updated.");
    }

    // Method for creating listings
    private void createListing() {

        // Create menus
        final String menuTitle = "Create your listing.";
        Menu titleMenu = getTitleMenu(menuTitle);
        Menu cityMenu = getCityMenu(menuTitle);
        Menu streetAddressMenu = getStreetAddressMenu(menuTitle);
        Menu pricePerMonthMenu = getPricePerMonthMenu(menuTitle);
        Menu descriptionMenu = getDescriptionMenu(menuTitle);
        Menu availabilityDatesMenu = getAvailabilityDatesMenu(menuTitle);
        Menu imagesMenu = getImagesMenu(menuTitle);

        // Create form from menus
        MenuForm createListingMenu = new MenuForm(app, new Menu[] {
            titleMenu, cityMenu, streetAddressMenu, pricePerMonthMenu, descriptionMenu, availabilityDatesMenu, imagesMenu
        });
        
        // Input variables
        ArrayList<Object> values;
        String listingTitle;
        String city;
        String streetAddress;
        String pricePerMonth;
        String description;
        String[] availabilities;
        String[] images;

        // Get user input
        values = createListingMenu.start();

        // Quit or exit
        if (values == null) {return;}
        
        // Seperate values
        listingTitle = (String) values.get(0);
        city = (String) values.get(1);
        streetAddress = (String) values.get(2);
        pricePerMonth = (String) values.get(3);
        description = (String) values.get(4);
        availabilities = (String[]) values.get(5);
        images = (String[]) values.get(6);

        // Create new listing
        listingManager.createListing(homeOwner, listingTitle, city, streetAddress,
                                        Double.parseDouble(pricePerMonth), description,
                                        DateRange.parseMultiple(availabilities), images);

        // Display success message
        System.out.println("Listing created.");
    }

    // Factory methods for listing attribute menus commonly used by methods
    private Menu getTitleMenu(String title) {
        return new MenuField(app, title, "title", ValidatorChainFactory.buildTextValidator(), true);
    }
    private Menu getCityMenu(String title) {
        return new MenuField(app, title, "city", ValidatorChainFactory.buildCityValidator(), true);
    }
    private Menu getStreetAddressMenu(String title) {
        return new MenuField(app, title, "street address", ValidatorChainFactory.buildTextValidator(), true);
    }
    private Menu getPricePerMonthMenu(String title) {
        return new MenuField(app, title, "price per month", ValidatorChainFactory.buildPriceValidator(), true);
    }
    private Menu getDescriptionMenu(String title) {
        return new MenuField(app, title, "description", ValidatorChainFactory.buildTextValidator(), true);
    }
    private Menu getAvailabilityDatesMenu(String title) {
        return new MenuList(app, title, "availability date range in the format " + AppConstants.DATE_RANGE_FORMAT, 
                                        ValidatorChainFactory.buildDateRangeValidator(), true);
    }
    private Menu getImagesMenu(String title) {
        return new MenuList(app, title, "image link", ValidatorChainFactory.buildImageValidator(), false);
    }
}