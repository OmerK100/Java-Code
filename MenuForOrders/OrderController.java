/* OrderController class creates the menu interface that allows the user to make an order and save it. */
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javax.swing.JOptionPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import static javafx.scene.paint.Color.GREY;

public class OrderController {

    @FXML
    private ScrollPane gridMain;
    @FXML
    private Button orderButton;
    @FXML
    private VBox vboxMain;
    private ArrayList names = new ArrayList(); /* Variables to store different variables and Scene objects. */
    private ArrayList prices = new ArrayList();
    private ArrayList checkBoxes = new ArrayList();
    private ArrayList comboBoxes = new ArrayList();
    private ArrayList nameTexts = new ArrayList();
    private ArrayList priceTexts = new ArrayList();
    private final int APPETIZER_INDEX = 1; /* Finals for separating different types of items. */
    private final int MAIN_COURSE_INDEX = 2;
    private final int LAST_MEAL_INDEX = 3;
    private final int BEVERAGE_INDEX = 4;
    private final int FINISH = 1; /* Finals for different operations and conditions. */
    private final int CANCEL = 2;
    private final int TYPE_FONT = 17;
    private final int FIRST = 0;
    private final int SECOND = 1;
    private final int THIRD = 2;
    private final int FOURTH = 3;
    private final int EMPTY = 0;
    private final int W1 = 556; /* Finals for different sizes for the scene objects. */
    private final int H1 = 30;
    private final int W2 = 50;
    private final int H2 = 10;
    private final int SIZE1 = 100;
    private final int SIZE2 = 150;
    private final int SIZE3 = 200;
    private final int SIZE4 = 220;
    private final int SIZE5 = 240;

    public void initialize() { /* Starts the scene. */
        Menu menu = new Menu(); /* Creating a Menu object, filling it up by reading the "menu.txt" file and passing it to the next functions. */
        String warning  = menu.readFile("menu.txt");
        if (warning != null) { /* Making sure that when creating the menu no error has occurred, else displaying the error and exiting the program. */
            JOptionPane.showConfirmDialog(null , warning , "Finish" , JOptionPane.CLOSED_OPTION);
            System.exit(0);
        }
        if (menu.getAppetizer().size() != EMPTY) { /* Adding each type to the menu, if the type isn't empty. */
            addItems(menu , APPETIZER_INDEX);
        }
        if (menu.getMainCourse().size() != EMPTY) {
            addItems(menu , MAIN_COURSE_INDEX);
        }
        if (menu.getLastMeal().size() != EMPTY) {
            addItems(menu , LAST_MEAL_INDEX);
        }
        if (menu.getBeverage().size() != EMPTY) {
            addItems(menu , BEVERAGE_INDEX);
        }
    }

    @FXML
    void orderPressed(ActionEvent event) { /* If the "Finish Order" button is pressed we let the user finish or continue his order however he wants. */
        String order = "";
        double priceSum = 0;
        String options[] = {"Continue Adding" , "Finish Order" , "Cancel Order"}; /* Dialog box with options. */
        var selection = JOptionPane.showOptionDialog(null , "Select one:" , "Proceed?" , 0 , 3 , null , options , options[0]);
        if (selection == FINISH) {
            /* If user chose to finish his order we create a string that describes the order, calculate a total price. */
            for (int i = 0 ; i < checkBoxes.size() ; i++) {
                if (((CheckBox)checkBoxes.get(i)).isSelected() == true) {
                    int amount = (int)((ComboBox)comboBoxes.get(i)).getSelectionModel().getSelectedItem();
                    if (amount != 0 ) {
                        priceSum = priceSum + amount * Double.parseDouble((((TextField)prices.get(i)).getText()));;
                        double price = Double.parseDouble((((TextField)prices.get(i)).getText()));
                        order = order + ((TextField)names.get(i)).getText() + " - " + "x" + amount + " , " + price + "\n";
                    }
                }
            }
            order = order + '\n';
            order = order + "Total order price: " + priceSum;
            JOptionPane.showConfirmDialog(null , "Press 'OK' and enter your name followed by your ID." , "Finish" , JOptionPane.CLOSED_OPTION);
            /* We ask the user to enter his name followed up by his ID, and create an Order object and finally save the order data in a .txt file. */
            String resp = "";
            String input = JOptionPane.showInputDialog(null , resp);
            String ID = null;
            String name = null;
            boolean flag = true;
            boolean correctInput = false;
            while (correctInput == false) {
                for (int i = 0 ; i < input.length() && flag == true ; i++) { /* Separating the name from the ID. */
                    if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                        name = input.substring(0 , i);
                        ID = input.substring(i , input.length());
                        flag = false;
                    }
                }
                if (ID == null || ID == "" || name == null || name == "") {
                    /* Making sure that the name and ID have been received properly. */
                    JOptionPane.showConfirmDialog(null , "Your details were not inserted properly, please try again" , "Finish" , JOptionPane.CLOSED_OPTION);
                    /* Display box with the message. */
                    input = JOptionPane.showInputDialog(null , resp);
                    /* Asking for input until received properly. */
                    ID = null;
                    name = null;
                    flag = true;
                }
                else {
                    correctInput = true;
                }
            }
            Order finishOrder = new Order(name , ID , order);
            for (int i = 0 ; i < checkBoxes.size() ; i++) { /* Clearing the scene and getting ready to accept a new order. */
                ((CheckBox)checkBoxes.get(i)).setSelected(false);
                ((ComboBox)comboBoxes.get(i)).getSelectionModel().clearSelection();
            }
            finishOrder.createOrderFile();
        }
        else if (selection == CANCEL) { /* If user chose to cancel the order we just clear up the scene and get ready to receive a new order. */
            for (int i = 0 ; i < checkBoxes.size() ; i++) {
                ((CheckBox)checkBoxes.get(i)).setSelected(false);
                ((ComboBox)comboBoxes.get(i)).getSelectionModel().clearSelection();
            }
        }
        /* Else if user chose to continue, we do nothing and by that the dialog box is exited, and we return to the scene. */
    }

    void textPressed(ActionEvent event) { /* textPressed function handles the event that the user tries to change the menu items on the scene. */
        JOptionPane.showConfirmDialog(null , "Please don't try to change the menu items and prices." , "Warning" , JOptionPane.CLOSED_OPTION);
        /* We open a dialog box with a warning for the user not to change the Items on the menu. */
        TextField src = (TextField)event.getSource();
        /* Then we find the Item that was changed (the source), and because we stored all the names and prices we can track down the Item that was changed and fix it. */
        for (int i = 0 ; i < nameTexts.size() ; i++) {
            if (src == names.get(i)) {
                ((TextField)names.get(i)).setText((String)nameTexts.get(i));
            }
            if (src == prices.get(i)) {
                ((TextField)prices.get(i)).setText((String)priceTexts.get(i));
            }
        }
    }

    private void addItems(Menu menu , int index) { /* addItems adds the items from the Menu object to the scene. */
        /* We have 4 kinds of indexes, each represents a different type of food in the menu. */
        ArrayList products = new ArrayList();
        int size = 0;
        if (index == APPETIZER_INDEX) { /* Storing the Item ArrayList according to the type through the index. */
            products = menu.getAppetizer();
            size = products.size(); /* Size of each type ArrayList. */
        }
        else if (index == MAIN_COURSE_INDEX) {
            products = menu.getMainCourse();
            size = products.size();
        }
        else if (index == LAST_MEAL_INDEX) {
            products = menu.getLastMeal();
            size = products.size();
        }
        else {
            products = menu.getBeverage();
            size = products.size();
        }
        GridPane kind = new GridPane(); /* GridPane to store the title. */
        kind.setMinSize(W1 , H1);
        kind.setAlignment(Pos.CENTER);
        if (index == APPETIZER_INDEX) {
            Label appetizer = new Label("Appetizers"); /* Label for the title, for each type according to the index. */
            appetizer.setFont(new Font(TYPE_FONT));
            kind.add(appetizer , W2 , H2);
        }
        else if (index == MAIN_COURSE_INDEX) {
            Label mainCourse = new Label("Main Courses");
            mainCourse.setFont(new Font(TYPE_FONT));
            kind.add(mainCourse , W2 , H2);
        }
        else if (index == LAST_MEAL_INDEX) {
            Label lastMeal = new Label("Last Meals");
            lastMeal.setFont(new Font(TYPE_FONT));
            kind.add(lastMeal , W2 , H2);
        }
        else {
            Label beverage = new Label("Beverages");
            beverage.setFont(new Font(TYPE_FONT));
            kind.add(beverage , W2 , H2);
        }
        vboxMain.getChildren().add(kind); /* Adding the new GridPane to the VBox in the scene. */
        GridPane types = new GridPane(); /* GridPane to store what each column represents. */
        types.setMinSize(W1 , H1);
        types.setAlignment(Pos.CENTER);
        Label label = new Label("Dish Name       |       Dish Price       |       Add?       |       Quantity"); /* Label for each column. */
        label.setTextFill(GREY);
        types.add(label , W2 , H2);
        vboxMain.getChildren().add(types); /* Adding the new GridPane to the VBox in the scene. */
        int j = SIZE1;
        for (int i = 0 ; i < size ; i++) { /* We run with a loop and add each Item to the scene menu, according to the type. */
            GridPane add = new GridPane(); /* New GridPane for each Item according to the type. */
            add.setMinSize(W1 , H1);
            add.setAlignment(Pos.CENTER);
            add.addColumn(FIRST); /* 4 columns, description, price, CheckBox to add to the order and ComboBox to choose amount for the order.  */
            add.addColumn(SECOND);
            add.addColumn(THIRD);
            add.addColumn(FOURTH);
            TextField name = new TextField(((Item)products.get(i)).getDescription() + ""); /* TextField for the description, first column. */
            names.add(name);
            ((TextField)names.get(names.size() - 1)).setOnAction(new EventHandler<ActionEvent>() {
                /* Handling the case in which the user tries to change the Item's description. */
                @Override
                public void handle(ActionEvent e) {
                    textPressed(e);
                }
            });
            nameTexts.add(((Item)products.get(i)).getDescription() + ""); /* Storing each description in an ArrayList. */
            add.add(name , SIZE1 , j); /* Adding the TextField to the GridPane. */
            TextField price = new TextField(((Item)products.get(i)).getPrice() + ""); /* TextField for the price, second column. */
            prices.add(price);
            ((TextField)prices.get(prices.size() - 1)).setOnAction(new EventHandler<ActionEvent>() {
                /* Handling the case in which the user tries to change the Item's price. */
                @Override
                public void handle(ActionEvent e) {
                    textPressed(e);
                }
            });
            priceTexts.add(((Item)products.get(i)).getPrice() + ""); /* Storing each price in an ArrayList. */
            add.add(price , SIZE3 , j); /* Adding the TextField to the GridPane. */
            CheckBox check = new CheckBox(); /* CheckBox for whether the user wants to add the Item to the order or not, third column. */
            checkBoxes.add(check);
            add.add(check , SIZE4 , j); /* Adding the CheckBox to the GridPane and storing it. */
            ComboBox quantity = new ComboBox(); /* ComboBox for the amount of each Item that the user wants to add to his order. */
            for (int z = 0 ; z <= SIZE1 ; z++) { /* ComboBox quantities range from 0 to 100. */
                quantity.getItems().add(z);
            }
            comboBoxes.add(quantity); /* Adding the ComboBox to the GridPane and storing it. */
            add.add(quantity , SIZE5 , j);
            j += H2;
            vboxMain.getChildren().add(add); /* Adding the Item's GridPane to the VBox in the scene. */
        }
        GridPane space = new GridPane();
        /* Creating and adding a GridPane with empty labels in order to add a space between all the types of Items in the menu. */
        space.setMinSize(W1 , H1);
        space.setAlignment(Pos.CENTER);
        Label lab1 = new Label(""); /* Empty labels. */
        space.add(lab1 , W2 , SIZE1);
        Label lab2 = new Label("");
        space.add(lab2 , W2 , SIZE2);
        vboxMain.getChildren().add(space);
    }

}
// End of class OrderController.