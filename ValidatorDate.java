import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/* Validater to check that a date input is valid and in the correct format. First uses a regex validator to check the format, 
then Uses java's LocalDate class to attempt to parse the date, then checks that the date is not in the past or more than
10 years in the future. */
public class ValidatorDate extends ValidatorChain{
    
    /**
    * Method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    @Override
    public boolean validate(String fieldName, String input) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);
        LocalDate date;
        ValidatorMatchesRegex formatValidator;

        // Validate format using regex
        formatValidator = new ValidatorMatchesRegex(
            AppConstants.DATE_REGEX, "does not match the expected format"
        );
        if (!formatValidator.validate(fieldName, input)) {
            return false;
        }

        // Check date is valid
        try{

            // Parse date from input
            date = LocalDate.parse(input, formatter);
        } 
        catch (DateTimeParseException e) {

            // Print error message if parsing fails
            System.out.println("Date entered is not valid.");
            return false;
        }

        // Check date is not in the past
        if (date.isBefore(LocalDate.now())) {
            System.out.println("Date cannot be in the past.");
            return false;
        }

        // Check date in no longer than 10 years in the future
        if (date.isAfter(LocalDate.now().plusYears(10))) {
            System.out.println("Date cannot be more than 10 years in the future.");
            return false;
        }

        // Pass validation to the next validator
        return validateNext(fieldName, input);
    }
}
