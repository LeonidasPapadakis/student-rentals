/* Validator to check if a given email address is available in the user manager. */
public class ValidatorEmailAvailable extends ValidatorChain {
    
    // User manager to check if email address is available
    private UserManager userManager;

    public ValidatorEmailAvailable(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
    * Method to validate input
    * @param fieldName the name of the field being validated
    * @param input the input to be validated
    * @return true if input is valid, false otherwise
    */
    @Override
    public boolean validate(String fieldName, String input) {

        // Check if email address is available
        boolean exists = userManager.emailExists(input);

        // Print error message if email address is not available
        if (exists) {
            System.out.println(fieldName + " '" + input + "'' is already in use.");
            return false;
        }
        // Pass validation to the next validator
        return validateNext(fieldName, input);
    }
}
