/* Validator to check input matches a given regular expression. A custom error message can be provided
to give clarity to the user. */
public class ValidatorMatchesRegex extends ValidatorChain{
    
    private String regex; // Regex string to compare
    private String message; // Error message to explain required pattern

    // Constructor
    ValidatorMatchesRegex(String regex, String message) {
        this.regex = regex;
        this.message = message;
    }

    /**
    * Method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    @Override
    public boolean validate(String fieldName, String input) {
        
        // Check if input matches regex
        if (!input.matches(regex)) {
            System.out.println(fieldName + " " + message + ".");
            return false;
        }
        // Pass validation to the next validator
        return validateNext(fieldName, input);
    }
}
