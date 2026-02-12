import java.util.ArrayList;

/* Menu to get a list of values for a field. Uses a ValidatorChain object to validate input. Users can enter as many
values as they want. If a list is required, users must enter at least one value. */
public class MenuList extends Menu {

    private String title;
    private String fieldName;
    private ValidatorChain validator;
    private Boolean required;

    // Constructor
    public MenuList(AppControl app, String title, String fieldName, ValidatorChain validator, boolean required) {
        super(app);
        this.title = title;
        this.fieldName = fieldName;
        this.validator = validator;
        this.required = required;
    }

    @Override
    /**
    * Method to start the menu
    * @return Array of user inputs
    */
    public String[] start() {

        // ArrayList to hold user inputs
        ArrayList<String> inputList = new ArrayList<String>();
        String input;
        boolean valid;

        // Loop until value returned
        while (true) {

            // Display menu
            printMenu();

            // Get user input
            input = getUserInput();
            System.out.println(AppConstants.TEXT_SPACER);

            // Check for exit or quit option
            if (checkForExit(input) || checkForQuit(input)) {
                return null;
            }

            // Complete list if user input is empty
            else if (input == "") {

                // If list is required and no inputs have been entered, give an error message
                if (required && inputList.size() == 0) {
                    System.out.println("\nYou must enter at least one " + fieldName + ".");
                    System.out.println(AppConstants.TEXT_SPACER);
                }
                else {
                    return inputList.toArray(new String[0]);
                }
            }

            // Validate input if validator has been set
            valid = true;
            if (validator != null) {
                valid = validator.validate(fieldName, input); 
            }

            // Add input to list if valid
            if (valid) {
                System.out.println("\n" + fieldName + " entered: " + input + ".");
                System.out.println(AppConstants.TEXT_SPACER);
                inputList.add(input);
            }
        }
    }

    // Method to print the menu to the console
    private void printMenu() {
        
        // Display title, field, exit and quit options
        System.out.println("\n" + title);
        if (required) {
            System.out.println("- Enter at least one " + fieldName + " to proceed.");
        } else {
            System.out.println("- Enter " + fieldName + " to proceed.");
        }
        System.out.println("- Press ENTER to finish.");
        System.out.println(EXIT_OPTION_TEXT);
        System.out.println(QUIT_OPTION_TEXT);
    }    
}