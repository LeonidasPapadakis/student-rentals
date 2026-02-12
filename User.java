/* Base class to represent a system user. Encapsulates shared attributes and behavior between user types. */
public abstract class User{

    // User types
    public enum UserType {
        STUDENT, HOME_OWNER
    }

    // Attributes
    protected UserType type;
    protected String name;
    protected String emailAddress;
    protected String password;

    // Constructor
    protected User(String name, String emailAddress, String password){
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    // Getters
    public UserType getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public String getEmailAddress(){
        return emailAddress;
    }

    /**
    * Check a password without exposing it
    * @param passwordInput the password to check
    * @return true if the password is correct, false otherwise
    */
    public boolean validatePassword(String passwordInput){
        return password.equals(passwordInput);
    }
}