/* Sudoku game with JavaFX GUI - Omer Komissarchik - 215314725 */
/* Main class contains the main function of this program and knows how to run the JavaFX program. */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SudokuScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350 , 400);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
// End of class Main
