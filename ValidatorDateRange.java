import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* Validater to check that a date range input is valid and in the correct format. First uses a regex validator to check the format, 
then split the dates into start and end dates and uses java's LocalDate class to attempt to parse dates. Additionally checks that
the start date is before the end date */
public class ValidatorDateRange extends ValidatorChain{
    
    /**
    * Method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    @Override
    public boolean validate(String fieldName, String input) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);
        String[] dates;
        LocalDate startDate;
        LocalDate endDate;
        ValidatorMatchesRegex formatValidator;
        ValidatorDate dateValidator;

        // Validate format using regex
        formatValidator = new ValidatorMatchesRegex(
            AppConstants.DATE_RANGE_REGEX, "does not match the expected format"
        );
        if (!formatValidator.validate(fieldName, input)) {
            return false;
        }

        // Split dates into start and end
        dates = input.split("-");

        // Validate start and end dates using ValidatorDate
        dateValidator = new ValidatorDate();
        if (!dateValidator.validate(fieldName, dates[0])) {
            return false;
        }
        if (!dateValidator.validate(fieldName, dates[1])) {
            return false;
        }

        // Parse start and end dates from string
        startDate = LocalDate.parse(dates[0], formatter);
        endDate = LocalDate.parse(dates[1], formatter);

        // Check that start date is before end date
        if (startDate.isAfter(endDate)) {

            System.out.println("Start date must be before end date.");
            return false;
        }

        // Pass validation to the next validator
        return validateNext(fieldName, input);
    }
}
