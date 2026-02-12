import java.util.Scanner;

/* Abstract class to request and get input from the user. */
public abstract class Menu {

    // Text for exit and quit menu options
    protected final static String EXIT_OPTION_TEXT = "- Enter 'e' to exit this menu.";
    protected final static String QUIT_OPTION_TEXT = "- Enter 'q' to quit the application.";

    // User manager and input scanner
    protected final AppControl app;
    //protected final UserManager userManager;
    private final Scanner scanner;

    // Constructor
    protected Menu(AppControl app) {
        this.app = app;
        this.scanner = new Scanner(System.in);
        //this.userManager = UserManager.getInstance();
    }

    /**
    * Abstract method to start the menu
    * @return user input
    */
    public abstract Object start();

    /**
    * Get input from user
    * @return user input
    */
    protected String getUserInput() {  
        return scanner.nextLine().trim();
    }

    /**
    * Check for quit option from user
    * @param input user input string to check
    * @return true if quit option selected, false otherwise
    */
    protected boolean checkForQuit(String input) {

        // If quit option selected
        if (input.equalsIgnoreCase("q")) {
            
            // Print goodbye message and shut down application
            System.out.println("Thank you for using the Student-Homeowner Matchmaker. Goodbye!");
            app.stop();
            return true;
        }
        return false;
    }

    /**
    * Check for exit option from user
    * @param input user input string to check
    * @return true if exit option selected, false otherwise
    */
    protected boolean checkForExit(String input) {

        // If exit option selected
        if (input.equalsIgnoreCase("e")) {

            // Print exit message
            System.out.println("Exiting menu.");
            return true;
        }
        return false;
    }
}
