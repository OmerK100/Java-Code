/* A traffic light simulator using GUI and multithreading for the parallel flickering of the lights - Omer Komissarchik - 215314725 */
/* Main class, runs the application. */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TrafficLightsScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load() , 600 , 400);
        stage.setTitle("Traffic Lights Simulation");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        TrafficLightsController.setTime(args[0]); /* Passing the command line argument that stores the time for the traffic lights to the app. */
        launch();
    }

}
// End of class Main.
