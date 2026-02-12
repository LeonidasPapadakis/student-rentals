/* Validator to check the length of an input lies between a given minimum and maximum. */
public class ValidatorLength extends ValidatorChain {

    // Minimum and maximum length
    private int minimum;
    private int maximum;

    // Constructor
    ValidatorLength(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
    * Method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    @Override
    public boolean validate(String fieldName, String input) {

        // Check length
        int inputLength = input.length();
        if (inputLength < minimum || inputLength > maximum) {
            System.out.println(fieldName + " must be between " + minimum + " and " + maximum + " characters.");
            return false;
        }
        // Pass validation to the next validator
        return validateNext(fieldName, input);
    }
}