import java.util.ArrayList;

/* Default MenuProvider if no user is signed in. Users can only sign in or register. */
public class MenuProviderDefault extends MenuProvider{

    // Constructor
    public MenuProviderDefault(AppControl app, UserManager userManager) {
        super(app, userManager);
    }

    // Sign in / register menu
    @Override
    public void start() {

        // Create main options menu
        String title = "Welcome to the Student-Homeowner Matchmaker.";
        String[] options = {"sign in", "register"};
        MenuOptions menu = new MenuOptions(app, title, options, false);

        // Start menu and get user input
        Integer choice = menu.start();
        if (choice == null) {return;}
         
        // Handle user input
        switch (choice) {
            case 1 -> signIn();
            case 2 -> register();
        }
    }
    
    // Sign in method
    private void signIn() {

        // Create menus for email and password
        final String title = "Sign-in to the Student-Homeowner Matchmaker";
        Menu emailAddressMenu = new MenuField(app, title, "email address", null, true);
        Menu passwordMenu = new MenuField(app, title, "password", null, true);

        // Create form from menus
        MenuForm signInMenu = new MenuForm(app, new Menu[] {emailAddressMenu, passwordMenu});

        // Input variables
        ArrayList<Object> values;
        String emailAddress;
        String password;

        // Loop until valid sign-in
        boolean valid = false;
        while (!valid && app.isRunning()) {

            // Get user input from menu
            values = signInMenu.start();

            // Quit or exit
            if (values == null) {
                return;
            }

            // Separate values
            emailAddress = (String) values.get(0);
            password = (String) values.get(1);

            // Attempt to sign in
            valid = userManager.signIn(app, emailAddress, password);

            // Display result
            if (valid) {
                System.out.println("Sign-in successful. Welcome, " + app.getSignedInUser().getName() + "!");
            } else {
                System.out.println("Sign-in failed. Invalid email or password.");
            }
        }
    }

    // Register method
    private void register() { 

        User user;

        // Create menus
        final String title = "Register for the Student-Homeowner Matchmaker";
        final String[] userTypeOptions = {"register as a student", "register as a homeowner"};
        Menu userTypeMenu = new MenuOptions(app, title, userTypeOptions, true);
        Menu nameMenu = new MenuField(app, title, "name", 
                                                ValidatorChainFactory.buildNameValidator(), true);
        Menu emailAddressMenu = new MenuField(app, title, "email address", 
                                                ValidatorChainFactory.buildEmailValidator(userManager), true);
        Menu passwordMenu = new MenuField(app, title, "password", 
                                                ValidatorChainFactory.buildPasswordValidator(), true);

        // Input variables
        ArrayList<Object> values;
        int userType;
        User.UserType type;
        String emailAddress;
        String name;
        String password;

        // Create form from menus
        MenuForm registerMenu = new MenuForm(app, new Menu[] {userTypeMenu, nameMenu, emailAddressMenu, passwordMenu});

        // Run menu and get user input
        values = registerMenu.start();

        // Quit or exit
        if (values == null) {
            return;
        }

        // Separate values
        userType = (int) values.get(0);
        name = (String) values.get(1);
        emailAddress = (String) values.get(2);
        password = (String) values.get(3);
            
        // Register user
        type = User.UserType.values()[userType - 1];
        user = userManager.registerUser(type, name, emailAddress, password);

        // If registration was successful
        if (user != null) {

            // Display outcome
            System.out.println("Registration successful. Welcome, " + name + "!");

            // Sign in user
            userManager.signIn(app, emailAddress, password);
        }
        else {
            System.out.println("Registration failed.");
        }
    }
}
