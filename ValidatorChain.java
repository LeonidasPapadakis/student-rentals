/* Abstract class to validate input. Uses the Chain of Responsibility pattern. Each validator
extending this class handles a single validation rule and passes validation down the chain.
This allows for easy reuse, reorder and extension of validation rules */
public abstract class ValidatorChain {

    // Next validator in the chain
    protected ValidatorChain next;

    /**
    * Static method to set a validator chain
    * @param validators a collection of validators to be chained
    * @return the starting validator in the chain
    */
    public static ValidatorChain setChain(ValidatorChain... validators) {
        
        // Set the next validator in the chain
        for (int i = 0; i < validators.length - 1; i++) {
            validators[i].next = validators[i + 1];
        }

        // Return the starting validator
        return validators[0];
    }

    /**
    * Abstract method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    public abstract boolean validate(String fieldName, String input);

    /**
    * Method to pass validation to the next validator in the chain
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    protected boolean validateNext(String fieldName, String input) {
        
        // If there is no more validators, return true
        if (next == null) {
            return true;
        }
        // Pass validation to the next validator
        return next.validate(fieldName, input);
    }
}
