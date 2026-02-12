import java.time.LocalDate;
import java.util.Arrays;

/* Class used to sort an array of listings. Used when displaying search results.*/
public class ListingSorter {

    // Enum to represent the type of sort
    public enum SortType {
        AUTOMATIC, BUBBLE, MERGE
    };

    // Enum to represent the attribute to sort by
    public enum SortAttribute {
        NONE, PRICE, AVAILABILITY
    };

    // Enum to represent the order to sort by
    public enum SortOrder {
        ASCENDING, DESCENDING
    };

    // Sorter attributes
    private SortAttribute sortAttribute;
    private SortOrder sortOrder;
    private SortType sortType;

    // Constructor
    public ListingSorter(SortAttribute sortAttribute, SortOrder sortOrder, SortType sortType) {
        this.sortAttribute = sortAttribute;
        this.sortOrder = sortOrder;
        this.sortType = sortType;
    }

    /**
    * Sort an array of Listing objects.
    * @param listings The array of listings to sort.
    * @return The sorted array of listings.
    */
    public Listing[] sort(Listing[] listings) {

        // Select sort type
        switch (sortType) {
            case AUTOMATIC -> {
                // If array has 30 elements or less, use bubble sort
                if (listings.length <= 30) {
                    listings = bubbleSort(listings);
                }
                // Else use merge sort
                else {
                    listings = mergeSort(listings);
                }
            }
            case BUBBLE -> listings = bubbleSort(listings);
            case MERGE -> listings = mergeSort(listings);
        }
        return listings;
    }

    /**
    * Use bubble sort to sort the array.
    * @param listings The array of listings to sort.
    * @return The sorted array of listings.
    */
    private Listing[] bubbleSort(Listing[] listings) {

        boolean swap;
        Listing temp;

        // Loop for the number of elements in the array
        for (int numOfElements = 0; numOfElements < listings.length - 1; numOfElements++) {

            // Track whever a swap has been made
            swap = false;

            // Loop through the array
            for (int index = 0; index < listings.length - numOfElements - 1; index++) {

                // If the current element is greater than the next element
                if (shouldSwap(listings[index], listings[index + 1])) {

                    // Swap the elements
                    temp = listings[index];
                    listings[index] = listings[index + 1];
                    listings[index + 1] = temp;
                    swap = true;
                }
            }
            // If no swap has been made, the array is sorted
            if (!swap) break;
        }
        return listings;
    }

    /**
    * Use merge sort to sort the array.
    * @param listings The array of listings to sort.
    * @return The sorted array of listings.
    */
    private Listing[] mergeSort(Listing[] listings) {

        int midPoint;
        Listing[] leftHalf;
		Listing[] rightHalf;
        Listing[] left;
		Listing[] right;

        // If array has 0 or 1 elements, it's already sorted
        if (listings.length <= 1) {
            return listings;
        }

        // Find the midpoint
        midPoint = listings.length / 2;
        
        // Split the array into two halves
		leftHalf = Arrays.copyOfRange(listings, 0,  midPoint);
		rightHalf = Arrays.copyOfRange(listings, midPoint, listings.length);

        // Recursively call mergeSort on both halves
		left = mergeSort(leftHalf);
		right = mergeSort(rightHalf);

        // Call merge to combine the sorted halves back into the original list
		return merge(left, right, listings);
    }

    /**
     * Merges two sorted arrays back into a single sorted array.
     * @param leftHalf The first sorted half.
     * @param rightHalf The second sorted half.
     * @param originalListings The array to merge the results into.
     * @return The merged array.
     */
    private Listing[] merge(Listing[] leftHalf, Listing[] rightHalf, Listing[] originalListings) {

        // Use three pointers: i for leftHalf, j for rightHalf, k for originalListings
		int i = 0;
		int j = 0;
		int k = 0;

		while (i < leftHalf.length && j < rightHalf.length){

			// Compare elements from leftHalf and rightHalf
            if (!shouldSwap(leftHalf[i], rightHalf[j])){

				// Place the greater element into originalListings and advance the appropriate pointer
				originalListings[k] = rightHalf[j];
				j++;
            }
			else {
                originalListings[k] =  leftHalf[i];
				i++;
			}
			k++;
		}
		// Handle remaining elements from either half
		while(i < leftHalf.length){
			originalListings[k] = leftHalf[i];
			i++;
			k++;
		}
		while(j < rightHalf.length){
			originalListings[k] = rightHalf[j];
			j++;
			k++;
		}
		return originalListings;
    }
    
    /**
    * Compare two Listing objects.
    * @param listing1 The first Listing object. 
    * @param listing2 The second Listing object.
    * @return True if listing1 should swap places with listing2, false otherwise.
    */
    private boolean shouldSwap(Listing listing1, Listing listing2) {
        
        boolean shouldSwap = false;
        switch (sortAttribute) {

            // Do nothing
            case NONE -> {}

            // Compare prices
            case PRICE -> {
                switch (sortOrder) {
                    case ASCENDING -> shouldSwap = listing1.getPricePerMonth() > listing2.getPricePerMonth();
                    case DESCENDING -> shouldSwap = listing1.getPricePerMonth() < listing2.getPricePerMonth();
                }
            }

            // Compare earliest availability
            case AVAILABILITY -> {

                // Get availabilities
                DateRange[] availabilities1 = listing1.getAvailabilities();
                DateRange[] availabilities2 = listing2.getAvailabilities();
                LocalDate earliestAvailability1 = availabilities1[0].getStart();
                LocalDate earliestAvailability2 = availabilities2[0].getStart();

                // Loop through availabilities and find the earliest start date
                for (int index = 1; index < availabilities1.length; index++) {
                    if (availabilities1[index].getStart().isBefore(earliestAvailability1)) {
                        earliestAvailability1 = availabilities1[index].getStart();
                    }
                }
                for (int index = 1; index < availabilities2.length; index++) {
                    if (availabilities2[index].getStart().isBefore(earliestAvailability2)) {
                        earliestAvailability2 = availabilities2[index].getStart();
                    }
                }

                // Compare earliest start dates
                switch (sortOrder) {
                    case ASCENDING -> shouldSwap = earliestAvailability1.isAfter(earliestAvailability2);
                    case DESCENDING -> shouldSwap = earliestAvailability1.isBefore(earliestAvailability2);
                }
            }
        }
        return shouldSwap;
    }
}
