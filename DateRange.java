import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* Class to represent a date range using two LocalDate objects */
public class DateRange {
   
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);

    // Attributes
    private LocalDate start;
    private LocalDate end;

    // Constructor
    public DateRange(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        this.start = start;
        this.end = end;
    }

    // Getters
    public static DateTimeFormatter getFormatter() {
        return dateFormatter;
    }
    public LocalDate getStart() {
        return start;
    }
    public LocalDate getEnd() {
        return end;
    }

    // Setters
    public void setStart(LocalDate start) {
        this.start = start;
    }
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
    * Check if date range fits within another date range
    * @param other the other date range
    * @return true if date range fits within other date range, false otherwise
    */
    public boolean fitsIn(DateRange other) {

        // Get dates from other date range
        LocalDate otherStart = other.getStart();
        LocalDate otherEnd = other.getEnd();

        // Check if start and end dates are within other date range
        return (start.isAfter(otherStart) || start.isEqual(otherStart)) 
                && (end.isBefore(otherEnd) || end.isEqual(otherEnd));
    }

    /**
    * Parse date range from a string
    * @param dateRangeString the string to parse
    * @return the parsed date range
    */
    public static DateRange parse(String dateRangeString) {

        // Split date range into start and end dates
        String[] dates = dateRangeString.split("-");

        // Parse start and end dates from string
        LocalDate start = LocalDate.parse(dates[0], dateFormatter);
        LocalDate end = LocalDate.parse(dates[1], dateFormatter);

        return new DateRange(start, end);
    }

    /**
    * Parse date ranges from an array of strings
    * @param dateRangeStrings the array of strings to parse
    * @return the array of parsed date ranges
    */
    public static DateRange[] parseMultiple(String[] dateRangeStrings) {

        DateRange[] dateRanges = new DateRange[dateRangeStrings.length];

        // Parse each date range
        for (int i = 0; i < dateRangeStrings.length; i++) {
            dateRanges[i] = parse(dateRangeStrings[i]);
        }

        return dateRanges;
    }
}
