/* Factory class for creating commonly used validator chains. */
public class ValidatorChainFactory {

    // Prevent instantiation
    private ValidatorChainFactory() {}
    
    // Factory method to validate user email
    public static ValidatorChain buildEmailValidator(UserManager userManager) {
        
        return ValidatorChain.setChain(
            new ValidatorLength(5, 50),
            new ValidatorMatchesRegex(AppConstants.EMAIL_REGEX, 
                "must be in a valid email format"),
            new ValidatorEmailAvailable(userManager)
        );
    }

    // Factory method to validate user name
    public static ValidatorChain buildNameValidator() { 

        return ValidatorChain.setChain(
            new ValidatorLength(2, 50),
            new ValidatorMatchesRegex(AppConstants.SIMPLE_TEXT_REGEX, 
                "must contain only text, spaces and hyphens") 
        );
    }

    // Factory method to validate user password
    public static ValidatorChain buildPasswordValidator() { 

        return ValidatorChain.setChain(
            new ValidatorLength(8, 30),
            new ValidatorMatchesRegex(AppConstants.PASSWORD_REGEX, 
                "must contain at least 1 special character")
        );
    }

    // Factory method to validate listing city
    public static ValidatorChain buildCityValidator() {
        
        return new ValidatorAppearsInSet(AppConstants.BRITISH_CITIES);
    }
    
    // Factory method to validate text inputs
    public static ValidatorChain buildTextValidator() { 
        return ValidatorChain.setChain(
            new ValidatorLength(5, 50),
            new ValidatorMatchesRegex(AppConstants.TEXT_WITH_PUNCT_REGEX, 
                "must contain only text and punctuation") 
        );
    }

    // Factory method to validate listing price
    public static ValidatorChain buildPriceValidator() { 
        
        return new ValidatorPrice();
    }

    // Factory method to validate a date
    public static ValidatorChain buildDateValidator() { 
        
        return new ValidatorDate();
    }

    // Factory method to validate a date range
    public static ValidatorChain buildDateRangeValidator() { 
        
        return new ValidatorDateRange();
    }
    
    // Factory method to validate image links
    public static ValidatorChain buildImageValidator() {
        
        return new ValidatorMatchesRegex(AppConstants.IMAGE_LINK_REGEX, 
            "must be a valid image link"
        );
    }
}
