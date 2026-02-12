/* Menu to get input for a list of options. If exitOption is false, only the quit option is displayed. */
public class MenuOptions extends Menu {

    private String title;
    private String[] options;
    private boolean exitOption;

    // Constructor
    public MenuOptions(AppControl app, String title, String[] options, boolean exitOption) {
        super(app);
        this.title = title;
        this.options = options;
        this.exitOption = exitOption;
    }

    /**
    * Method to start the menu
    * @return the user input integer
    */
    @Override
    public Integer start() {

        // Loop until a valid option is returned
        while (true) {

            // Display prompt
            printMenu();

            // Get user input
            String input = getUserInput();
            System.out.println(AppConstants.TEXT_SPACER);

            // Check for exit or quit option
            if (exitOption) {
                if (checkForExit(input)){
                    return null;
                }
            } 
            if (checkForQuit(input)){
                return null;
            }
            
            // Check if input is in options list
            try {
                int integerInput = Integer.parseInt(input);
                if (integerInput <= options.length && integerInput > 0) {

                    // Valid input
                    System.out.println("\nOption selected: " + input + ".");
                    System.out.println(AppConstants.TEXT_SPACER);
                    return integerInput;
                }
            }
            catch (NumberFormatException e) {
                // Do nothing
            }
            // Display invalid input message
            System.out.println("\nInvalid input. Please try again.");
            System.out.println(AppConstants.TEXT_SPACER);
        }
    }

    // Method to print a list of options to the console
    private void printMenu() {
        
        // Display title and options
        System.out.println("\n" + title);
        for (int i = 0; i < options.length; i++) {
            System.out.println("- Enter '" + (i + 1) + "' to " + options[i] + ".");
        }

        //Display exit option if enabled
        if (exitOption) {
            System.out.println(EXIT_OPTION_TEXT);
        }

        // Display quit option
        System.out.println(QUIT_OPTION_TEXT);
    }
}