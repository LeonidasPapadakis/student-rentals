/* Class to represent a menu with a single text field. Uses a ValidatorChain object to validate input */
public class MenuField extends Menu {

    private String title; 
    private String fieldName;
    private ValidatorChain validator;
    private boolean required;

    // Constructor
    public MenuField(AppControl app, String title, String fieldName, ValidatorChain validator, boolean required) {
        super(app);
        this.title = title;
        this.fieldName = fieldName;
        this.validator = validator;
        this.required = required;
    }

    /**
    * Method to start the menu
    * @return the user input given
    */
    @Override
    public String start() {

        String input = "";
        boolean valid;

        // Loop until a value is returned
        while (true) {

            // Display menu
            printMenu();

            // Get user input
            input = getUserInput();
            System.out.println(AppConstants.TEXT_SPACER);

            // Check for exit or quit option
            if (checkForExit(input) || checkForQuit(input)){
                return null;
            }

            // If no input has been entered
            if (input == "") {

                // If field is required, give an error message
                if (required) {
                    System.out.println("\nYou must enter a " + fieldName + ".");
                    System.out.println(AppConstants.TEXT_SPACER);
                }
                else{
                    return "";
                }
            }

            // Validate input if validator has been set
            valid = true;
            if (validator != null) {
                valid = validator.validate(fieldName, input); 
            }

            // Return input if valid
            if (valid) {
                System.out.println("\n" + fieldName + " entered: " + input + ".");
                System.out.println(AppConstants.TEXT_SPACER);
                return input;
            }
        }
    }

    // Method to print the menu to the console
    private void printMenu() {
        
        // Display title, field, exit and quit options
        System.out.println("\n" + title);
        System.out.println("- Enter " + fieldName + " to proceed.");
        System.out.println(EXIT_OPTION_TEXT);
        System.out.println(QUIT_OPTION_TEXT);
    }
}