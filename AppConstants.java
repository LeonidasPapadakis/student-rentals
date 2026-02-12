import java.util.Arrays;
import java.util.HashSet;

/* Constants used in the application */
public class AppConstants {

    // Prevent instantiation
    private AppConstants() {}

    // Text spacer
    public static final String TEXT_SPACER = "\n" + "=".repeat(100);

    // String format for dates
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // String format for date ranges
    public static final String DATE_RANGE_FORMAT = DATE_FORMAT + "-" + DATE_FORMAT;

    // Regex for validating dates
    public static final String DATE_REGEX = "[0-9]{2}/[0-9]{2}/[0-9]{4}"; // Format must be DD/MM/YYYY
    
    // Regex for validating date ranges
    public static final String DATE_RANGE_REGEX = DATE_REGEX + "-" + DATE_REGEX; // Format must be (date)-(date)
    
    // Regex for validating email addresses
    public static final String EMAIL_REGEX = "[A-Za-z0-9._+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+"; // Validate email format

    // Regex for validating simple text
    public static final String SIMPLE_TEXT_REGEX = "[A-Za-z -]+"; // Accept only text, spaces and hyphens

    // Regex for validating text with punctuation
    public static final String TEXT_WITH_PUNCT_REGEX = "[A-Za-z0-9 -,.?!()']+"; // Accept only text, numbers and punctuation

    // Regex for validating passwords
    public static final String PASSWORD_REGEX = ".*[^A-Za-z0-9]+.*"; // Require at least one special character

    // Regex for validating image links
    public static final String IMAGE_LINK_REGEX = ".*\\.(png|jpe?g|gif|bmp|webp|svg).*";

    // Valid cities that can be associated with a listing
    public static final HashSet<String> BRITISH_CITIES = new HashSet<>(Arrays.asList(
        "bath", "birmingham", "bradford", "brighton",
        "bristol", "cambridge", "canterbury", "carlisle",
        "chelmsford", "chester", "chichester", "colchester",
        "coventry", "derby", "doncaster", "durham",
        "ely", "exeter", "gloucester", "hereford",
        "kingston-upon-hull", "lancaster", "leeds", "leicester",
        "lichfield", "lincoln", "liverpool", "london",
        "manchester", "milton keynes", "newcastle-upon-tyne", "norwich",
        "nottingham", "oxford", "peterborough", "plymouth",
        "portsmouth", "preston", "ripon", "salford",
        "salisbury", "sheffield", "southampton", "southend-on-sea",
        "st albans", "stoke on trent", "sunderland", "truro",
        "wakefield", "wells", "westminster", "winchester",
        "wolverhampton", "worcester", "york", "armagh",
        "bangor", "belfast", "lisburn", "londonderry",
        "newry", "aberdeen", "dundee", "dunfermline",
        "edinburgh", "glasgow", "inverness", "perth",
        "stirling", "jamestown", "cardiff", "newport",
        "st asaph", "st davids", "swansea", "wrexham",
        "douglas", "bermuda", "hamilton", "gibraltar",
        "city of gibraltar", "stanley", "saint helena"
    
    ));
}
