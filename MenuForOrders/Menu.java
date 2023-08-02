/* Menu class represents a menu with all the items combined and sorted by their types. */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private ArrayList appetizer; /* ArrayList attribute for each type of item. */
    private ArrayList mainCourse;
    private ArrayList lastMeal;
    private ArrayList beverage;
    private final int TRUE = 0; /* Finals. */
    private final int FIRST_COMES_NAME = 1;
    private final int SECOND_COMES_TYPE = 2;
    private final int THIRD_COMES_PRICE = 3;

    public Menu() { /* Empty builder. */
        this.appetizer = new ArrayList();
        this.mainCourse = new ArrayList();
        this.lastMeal = new ArrayList();
        this.beverage = new ArrayList();
    }

    public void addItem(Item item) { /* addItem function receives an Item object and adds it to the Menu according to its type. */
        if (item.getType().compareTo("Appetizer") == TRUE) {
            this.appetizer.add(item);
        }
        else if (item.getType().compareTo("Main Course") == TRUE) {
            this.mainCourse.add(item);
        }
        else if (item.getType().compareTo("Last Meal") == TRUE) {
            this.lastMeal.add(item);
        }
        else if (item.getType().compareTo("Beverage") == TRUE) {
            this.beverage.add(item);
        }
    }

    public ArrayList getAppetizer() { /* Getters for each type ArrayList in the Menu. */
        return new ArrayList(this.appetizer);
    }

    public ArrayList getMainCourse() {
        return new ArrayList(this.mainCourse);
    }

    public ArrayList getLastMeal() {
        return new ArrayList(this.lastMeal);
    }

    public ArrayList getBeverage() {
        return new ArrayList(this.beverage);
    }

    public String readFile(String file) { /* readFile receives "menu.txt", reads its lines and creates a Menu object from it. */
        /* "menu.txt" goes as follows: each line represents the description, type or price of each Item. When we gather all
        attributes of an Item we add it to the Menu and start gathering data for the next Item. */
        String des = ""; /* Variables will store description, type and price of each Item we add to the menu. */
        String type = "";
        double price = 0;
        int i = FIRST_COMES_NAME; /* Index that runs from 1 to 3 and vice-versa to represent which attribute of the Item is copied from the file next. */
        try {
            Scanner input = new Scanner(new File(file));
            while (input.hasNext()) {
                boolean foundChar = false;
                String str = new String(input.nextLine());
                boolean flag = true;
                for (int j = 0 ; j < str.length() && flag == true ; j++) { /* Checking whether the line is empty (only white chars). */
                    if (str.charAt(j) != ' ' && str.charAt(j) != '\n' && str.charAt(j) != '\t') {
                        foundChar = true;
                        flag = false;
                    }
                }
                if (foundChar == true) { /* Skipping an empty line, adding to Item else. */
                    if (i == FIRST_COMES_NAME) { /* Next line represents an Item's description. */
                        des = new String(str);
                        i++;
                    } else if (i == SECOND_COMES_TYPE) { /* Next line represents an Item's type. */
                        if (str.compareTo("Appetizer") == TRUE || str.compareTo("Main Course") == TRUE || str.compareTo("Last Meal") == TRUE || str.compareTo("Beverage") == TRUE) {
                            type = new String(str);
                            i++;
                        }
                        else {
                            return "A type for an item in the menu was not entered properly in \"menu.txt\", no menu was created :(";
                        }
                    } else if (i == THIRD_COMES_PRICE) { /* Next line represents an Item's price. */
                        if (checkCorrectDouble(str) == false) {
                            return "A price for an item in the menu was not entered properly in \"menu.txt\", no menu created :(";
                        }
                        price = Double.parseDouble(str);
                        i = FIRST_COMES_NAME;
                        if (des != null && type != null) { /* Adding the Item to the Menu and starting to gather a new Item. */
                            Item add = new Item(des , type , price);
                            this.addItem(add);
                        }
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) { /* An exception might occur while opening "menu.txt", if there is we immediately exit. */
            System.out.println("File opening failure.");
            System.exit(0);
        }

        return null;
    }

    private boolean checkCorrectDouble(String price) {
        int dotCounter = 0;
        for (int i = 0 ; i < price.length() ; i++) {
            if ((price.charAt(i) < '0' || price.charAt(i) > '9') && price.charAt(i) != '.') {
                return false;
            }
            else if (price.charAt(i) == '.') {
                dotCounter++;
            }
        }
        if (dotCounter >= 2) {
            return false;
        }

        return true;
    }

}
/* End of class Menu. */