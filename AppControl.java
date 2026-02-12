/* Interface used to control the lifecycle of the application. Enables additional implementations
of application control to be added in the future, such as a GUI based controller. */
public interface AppControl {

    // Signed in user methods
    public User getSignedInUser();
    public void setSignedInUser(User user);

    // Application control methods
    public boolean isRunning();
    public void start();
    public void stop();
}
