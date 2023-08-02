/* Trivia game - Omer Komissarchik - 215314725 */
// class ClientMain handles running the client's GUI in our connection.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientMain extends Application {

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("ClientScene.fxml"));
            Scene scene = new Scene(fxmlLoader.load() , 800 , 600);
            stage.setTitle("Trivia");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }

}
// End of class ClientMain.
