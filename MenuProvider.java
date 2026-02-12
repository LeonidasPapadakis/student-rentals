/* Abstract class to create create and run a series of menus */
public abstract class MenuProvider {

    // Instance variables
    protected final AppControl app;
    protected final UserManager userManager;

    // Constructor
    public MenuProvider(AppControl app, UserManager userManager) {
        this.app = app;
        this.userManager = userManager;
    }

    // Abstract method to start the menus
    public abstract void start();
}
