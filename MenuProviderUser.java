import java.util.HashSet;
import java.util.Arrays;
import java.time.LocalDate;

/* Base MenuProvider class for users. All users can search for listings, view their bookings, and sign out */
public abstract class MenuProviderUser extends MenuProvider {

    protected final ListingManager listingManager;
    protected final BookingManager bookingManager;

    // Constructor
    protected MenuProviderUser(AppControl app, UserManager userManager, ListingManager listingManager, 
                                BookingManager bookingManager) {
        super(app, userManager);
        this.listingManager = listingManager;
        this.bookingManager = bookingManager;
    }

    /**
    * Abstract method to handle results generated from a search
    * @param results the results of the search
    */
    protected abstract void handleSearchResults(Listing[] results);

    // Abstract method to view the user's bookings
    protected abstract void viewBookings();

    // Sign out method
    protected void signOut() {
        System.out.println("Goodbye " + app.getSignedInUser().getName() + ", you have been signed out.");
        userManager.signOut(app);
    }

    // Search listings method
    protected void searchListings() {
        
        // Sort options
        ListingSorter.SortAttribute sortAttribute = ListingSorter.SortAttribute.NONE;
        ListingSorter.SortOrder sortOrder = ListingSorter.SortOrder.ASCENDING;
        ListingSorter.SortType sortType = ListingSorter.SortType.AUTOMATIC;
        ListingSorter sorter = null;

        // Search filters
        Object[] searchFilters = new Object[] {null, null, null, null, null};

        // Create menus
        String[] options = {"search", "edit filters", 
                            "toggle between no sorting, sort by price and sort by earliest availability", 
                            "toggle sort order between ascending and descending",
                            "toggle sort type between automatic, bubble sort and merge sort"};
        MenuOptions menu = new MenuOptions(app, "Search Listings", options, true);
        
        // Loop until exit is selected
        while (app.isRunning()) {

            // Display sort options
            displaySortOptions(sortAttribute, sortOrder, sortType);

            // Display search filters
            displaySearchFilters(searchFilters);
                
            // Select to search, edit filters or toggle sorting options
            Integer choice = menu.start();
            if (choice == null) { return;}
            switch (choice) {
                case 1 -> {
                    if (sortAttribute != ListingSorter.SortAttribute.NONE){
                        sorter = new ListingSorter(sortAttribute, sortOrder, sortType);
                    }
                    search(searchFilters, sorter);
                }
                case 2 -> searchFilters = editSearchFilters(searchFilters);
                case 3 -> sortAttribute = ListingSorter.SortAttribute.values()[(sortAttribute.ordinal() + 1) % 3];
                case 4 -> sortOrder = ListingSorter.SortOrder.values()[(sortOrder.ordinal() + 1) % 2];
                case 5 -> sortType = ListingSorter.SortType.values()[(sortType.ordinal() + 1) % 3];
            }
        }   
    }
    
    /**
    * Method for modifying a selected booking
    * @param booking the selected booking
    * @param userType the user type
    */
    protected void modifyBooking(Booking booking, User.UserType userType) {

        MenuOptions menu;
        Integer choice;

        // Loop until user exits
        while (app.isRunning()) {

            // Display selected booking
            System.out.println("\nSelected booking:");
            BookingViewer.display(booking, userType);

            // Get appropriate booking menu for state and user type
            menu = BookingMenu.create(app, userType, booking.getState());
            
            // Get user input
            choice = menu.start();

            // Handle input
            if (choice == null) {return;};
            BookingMenu.handleInput(choice, userType, booking);
        }
    }

    /**
    * Print sort options to the console
    * @param sortAttribute the attribute to sort by
    * @param sortOrder the order to sort in
    * @param sortType the type of sort
    */
    private void displaySortOptions(ListingSorter.SortAttribute sortAttribute, 
                                    ListingSorter.SortOrder sortOrder, 
                                    ListingSorter.SortType sortType) {

        System.out.println("Sorting options:");

        // Sort attribute
        System.out.print("Sort by: ");
        switch (sortAttribute) {
            case NONE -> System.out.println("No sorting");
            case PRICE -> System.out.println("Price");
            case AVAILABILITY -> System.out.println("Earliest availability");
        }

        // Sort order
        System.out.print("Sort order: ");
        switch (sortOrder) {
            case ASCENDING -> System.out.println("Ascending");
            case DESCENDING -> System.out.println("Descending");
        }

        // Sort type
        System.out.print("Sort type: ");
        switch (sortType) {
            case AUTOMATIC -> System.out.println("Automatic");
            case BUBBLE -> System.out.println("Bubble sort");
            case MERGE -> System.out.println("Merge sort");
        }
    }

    /**
    * Print search filters to the console
    * @param searchFilters the search filters to print
    */
    @SuppressWarnings("unchecked")
    private void displaySearchFilters(Object[] searchFilters) {
        
        // Get string representations of filters
        String[] searchFiltersString = new String[5];
        searchFiltersString[0] = citiesToString((HashSet<String>) searchFilters[0]);
        searchFiltersString[1] = priceToString((Double) searchFilters[1]);
        searchFiltersString[2] = priceToString((Double) searchFilters[2]);
        searchFiltersString[3] = dateToString((LocalDate) searchFilters[3]);
        searchFiltersString[4] = dateToString((LocalDate) searchFilters[4]);

        // Filter names
        String[] filterNames = {"cities", "minimum price", "maximum price", "start date", "end date"};

        // Display filters
        System.out.println("\nSearch filters:");
        for (int i = 0; i < 5; i++) {

            if (searchFiltersString[i] != null && searchFiltersString[i] != "") {
                System.out.println(filterNames[i] + ": " + searchFiltersString[i]);
            }
            else {
                System.out.println(filterNames[i] + ": No filter selected");
            }
        }
    }

    /**
    * Edit an array of search filters
    * @param searchFilters the search filters to edit
    * @return the updated search filters
    */
    private Object[] editSearchFilters(Object[] searchFilters) {
        
        // Create menus
        String[] options = {"cities", "minimum price", "maximum price", "start date", "end date"};
        String dateFormatMessage = " in the format " + AppConstants.DATE_FORMAT;
        MenuOptions editFiltersMenu = new MenuOptions(app, "Edit Filters", options, true);
        Menu chosenFilterMenu = null;
        Integer filterChoice;
        Object newValue;
        String[] cities;

        // Loop until exit is selected
        while (app.isRunning()) {

            // Show search filters
            displaySearchFilters(searchFilters);

            // Select attribute to edit
            filterChoice = editFiltersMenu.start();

            // Return updated filter list if exit selected
            if (filterChoice == null) {
                return searchFilters;
            }

            // Create menu for selected filter
            switch (filterChoice) {
                case 1 -> chosenFilterMenu = new MenuList(app, "Edit " + options[0], options[0], 
                                                            ValidatorChainFactory.buildCityValidator(), false);
                case 2 -> chosenFilterMenu = new MenuField(app, "Edit " + options[1], options[1],
                                                             ValidatorChainFactory.buildPriceValidator(), false);
                case 3 -> chosenFilterMenu = new MenuField(app, "Edit " + options[2], options[2], 
                                                            ValidatorChainFactory.buildPriceValidator(), false);
                case 4 -> chosenFilterMenu = new MenuField(app, "Edit " + options[3], options[3] + dateFormatMessage, 
                                                            ValidatorChainFactory.buildDateValidator(), false);
                case 5 -> chosenFilterMenu = new MenuField(app, "Edit " + options[4], options[4] + dateFormatMessage, 
                                                            ValidatorChainFactory.buildDateValidator(), false);
            }

            // Get user input
            newValue = chosenFilterMenu.start();

            // Return updated filter list if exit selected
            if (newValue == null) {
                return searchFilters;
            }

            // If no input is given, change selected filter to null
            if (newValue == "") {
                searchFilters[filterChoice - 1] = null;
            }
            else {
                // Update filter with new value
                switch (filterChoice) {
                    case 1 -> {
                        cities = (String[]) newValue;
                        
                        // Convert values in city hashset to lowercase
                        for (int i = 0; i < cities.length; i++) {
                            cities[i] = cities[i].toLowerCase();
                        }
                        searchFilters[0] = new HashSet<>(Arrays.asList(cities));   
                    } 
                    case 2 -> searchFilters[1] = Double.parseDouble((String) newValue); // Minimum price
                    case 3 -> searchFilters[2] = Double.parseDouble((String) newValue); // Maximum price
                    case 4 -> searchFilters[3] = LocalDate.parse((String) newValue, DateRange.getFormatter()); // Start date
                    case 5 -> searchFilters[4] = LocalDate.parse((String) newValue, DateRange.getFormatter()); // End date
                }
            }
        }
        return searchFilters;
    }

    /**
    * Search for listings
    * @param searchFilters the search filters
    * @param sorter the sorter object
    */
    @SuppressWarnings("unchecked")
    private void search(Object[] searchFilters, ListingSorter sorter) {

        Listing[] results;

        // Get search filters
        HashSet<String> cities = (HashSet<String>) searchFilters[0];
        Double minPrice = (Double) searchFilters[1];
        Double maxPrice = (Double) searchFilters[2];
        LocalDate startDate = (LocalDate) searchFilters[3];
        LocalDate endDate = (LocalDate) searchFilters[4];

        // Search for listings
        results = listingManager.searchListings(cities, minPrice, maxPrice, startDate, endDate);
        if (results.length == 0) {
            System.out.println("No results found.");
        }

        // Sort results
        if (sorter != null) {
            results = sorter.sort(results);
        }

        // Display results
        ListingViewer.displayMultiple(results);

        // Call user specific method to handle results
        handleSearchResults(results);
    }

    /**
    * Convert hashset of cities to a string
    * @param cities the hashset of cities
    * @return the string of cities
    */
    private String citiesToString(HashSet<String> cities) {
        
        String citiesString = "";

        // If cities filter is given
        if (cities != null) {

            // Loop through cities add them to the string
            String[] citiesArray = cities.toArray(new String[0]);
            for (int i = 0; i < citiesArray.length; i++) {
                if (i != 0) {
                    citiesString = citiesString + ", ";
                }
                citiesArray[i] = citiesArray[i].substring(0,1).toUpperCase() 
                                + citiesArray[i].substring(1);
                citiesString = citiesString + citiesArray[i];
            }
        }
        else {
            return null;
        }
        return citiesString;
    }

    /**
    * Convert price to a string
    * @param price the price to convert
    * @return the string of the price
    */
    private String priceToString(Double price) {
        
        if (price != null) {
            return "£" + price;
        }
        else {
            return null;
        }
    }

    /**
    * Convert date to a string
    * @param date the date to convert
    * @return the string of the date
    */
    private String dateToString(LocalDate date) {
        if (date != null) {
            return date.format(DateRange.getFormatter());
        }
        else {
            return null;
        }
    }
}
