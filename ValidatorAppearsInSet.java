import java.util.HashSet;

/* Validator to check if input appears in a set of valid options. Uses a HashSet for efficient lookups.
Casing is ignored for flexibility. */
public class ValidatorAppearsInSet extends ValidatorChain{
    
    // Set of valid options
    private HashSet<String> validOptions;
    
    // Constructor
    public ValidatorAppearsInSet(HashSet<String> validOptions) {
        this.validOptions = validOptions;
    }
    
    /**
    * Method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    @Override
    public boolean validate(String fieldName, String input) {

        // Ignore case
        input = input.toLowerCase();

        // Check if input is in valid options
        if (!validOptions.contains(input)) {
            
            // If input is not in valid options, print error message
            System.out.println("'" + input + "' is not a valid " + fieldName + ".");
            return false;
        }

        // Pass validation to the next validator
        return validateNext(fieldName, input);
    }
}
