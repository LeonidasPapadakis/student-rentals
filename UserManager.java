import java.util.HashMap;

/* Class for managing users within the system. Responsible for authentication and creating, 
storing, and retreiving user objects. Implements the Singleton design pattern to ensure that 
all components of the system have access to the same set of users. */
public class UserManager {

    // Store users with a hash map
    private HashMap<String, User> users;

    // Singleton instance
    private static UserManager instance = null;

    // Private constructor
    private UserManager() {
        users = new HashMap<>();
    }
    
    // Get singleton instance
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
    * Register a new user account
    * @param type the user's type
    * @param name the user's name
    * @param emailAddress the user's email address
    * @param password the user's password
    * @return the registered user
    */
    public User registerUser(User.UserType type, String name, String emailAddress, String password) {
        
        // Create new user object
        User newUser = createUser(type, name, emailAddress, password);
        
        // If creation was successful
        if (newUser != null) {

            // Add user to manager
            addUser(newUser);

            return newUser;
        }
        return null;
    }

    /**
    * Sign in to a user account
    * @param app the application control object
    * @param emailAddress the user's email address
    * @param password the user's password
    * @return true if sign-in was successful, false otherwise
    */
    public boolean signIn(AppControl app, String emailAddress, String password) {
        
        // Get user by email address
        User user = getUser(emailAddress);
        
        // Check if user exists and password is correct
        if (user != null) {
            if (user.validatePassword(password)) {

                // Set signed-in user and return true
                app.setSignedInUser(user);
                return true;
            }
        }
        // Return false if sign-in failed
        return false;
    }

    /**
    * Sign out of a user account
    * @param app the application control object
    */
    public void signOut(AppControl app) {
        app.setSignedInUser(null);
    }

    /**
    * Factory method for creating users
    * @param type the user's type
    * @param name the user's name
    * @param emailAddress the user's email address
    * @param password the user's password
    * @return the created user
    */
    private User createUser(User.UserType type, String name, String emailAddress, String password) {
        
        // Create user based on type
        switch (type) {
            case STUDENT:
                return new UserStudent(name, emailAddress, password);
            case HOME_OWNER:
                return new UserHomeOwner(name, emailAddress, password);
            default:
                return null;
        }
    }

    /**
    * Add a user to the manager
    * @param user the user to add
    */
    private void addUser(User user) {
        users.put(user.getEmailAddress(), user);
    }

    /**
    * Get a user by email address
    * @param emailAddress the user's email address
    * @return the user
    */
    public User getUser(String emailAddress) {
        return users.get(emailAddress);
    }

    /**
    * Check if an email address exists in the system
    * @param emailAddress the email address to check
    * @return true if the email address exists, false otherwise
    */
    public boolean emailExists(String emailAddress) {
        return users.containsKey(emailAddress);
    }
}