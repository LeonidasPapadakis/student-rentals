/* Validator to check input is a valid price. Ensures input is numeric, greater than 0 and 
has no more than 2 decimal places. */
public class ValidatorPrice extends ValidatorChain{
    
    /**
    * Method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    @Override
    public boolean validate(String fieldName, String input) {
        
        // Check if input is numeric
        try {
            Double price = Double.parseDouble(input);

            // If input is not greater than 0, print error message
            if (price <= 0) {
                System.out.println(fieldName + " must be greater than 0.");
                return false;
            }

            // If input has more than 2 decimal places, print error message
            double priceInPence = price * 100;
            if (priceInPence % 1 != 0) {
                System.out.println(fieldName + " must have no more than 2 decimal places.");
                return false;
            }

        // If not numeric, print error message
        } catch (NumberFormatException e) {
            System.out.println(fieldName + " must be a number.");
            return false;
        }
        // Pass validation to the next validator
        return validateNext(fieldName, input);
    }
}
