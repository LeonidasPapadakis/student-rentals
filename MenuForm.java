import java.util.ArrayList;

/* Menu composed of other menus, which are run in sequence. Used to collect multiple user inputs. */
public class MenuForm extends Menu {

    private Menu[] menus;

    // Constructor
    public MenuForm(AppControl app, Menu[] menus) {
        super(app);
        this.menus = menus;
    }

    /**
    * Method to start the menu
    * @return ArrayList of user inputs
    */
    @Override
    public ArrayList<Object> start() {

        // Variables to hold user inputs
        ArrayList<Object> inputs = new ArrayList<Object>();
        Object input;

        // Loop through each menu
        for (Menu menu : menus) {
            
            // Start menu and get user input
            input = menu.start();

            // If exit or quit option is selected, return null
            if (input == null) {
                return null;
            }

            // Add user input to list
            inputs.add(input);
        }

        return inputs;
    }
}