/* TrafficLightsController runs the traffic lights simulation using threads. */
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TrafficLightsController extends Thread {

    @FXML
    private Canvas bottomLight; /* Canvas to draw on for each traffic light. */
    @FXML
    private Canvas leftLight;
    @FXML
    private Canvas rightLight;
    @FXML
    private Canvas topLight;
    private boolean leftRight = false; /* Variable to switch between left/right and top/bottom traffic lights. */
    private ThreadWalk walk; /* Thread for flickering the pedestrian traffic light while its green. */
    private static int time; /* Time between switching the traffic lights. */
    private static final int CONST_TIME = 3500;
    private final int LEFT_RIGHT = 1;
    private final int TOP_BOTTOM = 2;

    public static void setTime(String s) {
        try{
            time = Integer.parseInt(s); /* Receiving the time as a parameter and setting it. */
        } catch (Exception e) {
            time = CONST_TIME;
        }
    }


    public void initialize() {
        GraphicsContext gc1 = bottomLight.getGraphicsContext2D(); /* Initialising the canvases. */
        GraphicsContext gc2 = topLight.getGraphicsContext2D();
        GraphicsContext gc3 = leftLight.getGraphicsContext2D();
        GraphicsContext gc4 = rightLight.getGraphicsContext2D();
        draw(gc1 , gc2 , gc3 , gc4); /* Drawing the traffic lights on the canvases. */
        start(); /* Starting the animation thread. */
    }

    @Override
    public void run() { /* Runs the traffic lights simulation thread. */
        super.run();
        while(true) { /* Goes on. */
            GraphicsContext gc1 = bottomLight.getGraphicsContext2D();
            GraphicsContext gc2 = topLight.getGraphicsContext2D();
            GraphicsContext gc3 = leftLight.getGraphicsContext2D();
            GraphicsContext gc4 = rightLight.getGraphicsContext2D();
            if (leftRight == true) { /* Switching to left/right. */
                walk = new ThreadWalk(gc1 , gc2 , time , LEFT_RIGHT); /* New thread for pedestrian traffic light flickering, for left and right. */

                gc1.setFill(Color.RED); /* Left and right traffic lights allow the pedestrians to pass. */
                gc1.fillOval(270 , 1 , 30 , 30);
                gc1.setFill(Color.WHITE);
                gc1.fillOval(270 , 31 , 30 , 30);
                gc1.setFill(Color.WHITE);
                gc1.fillRect(279 , 61 , 12 , 20);
                gc1.setFill(Color.GREEN);
                gc1.fillRect(279 , 81 , 12 , 20);

                gc2.setFill(Color.RED);
                gc2.fillOval(270 , 1 , 30 , 30);
                gc2.setFill(Color.WHITE);
                gc2.fillOval(270 , 31 , 30 , 30);
                gc2.setFill(Color.WHITE);
                gc2.fillRect(279 , 61 , 12 , 20);
                gc2.setFill(Color.GREEN);
                gc2.fillRect(279 , 81 , 12 , 20);

                Platform.runLater(() -> {
                    /* Running the flickering thread upon the left/right traffic lights so while leftRight == true the pedestrian's traffic light flickers. */
                    walk.setPriority(Thread.MAX_PRIORITY);
                    walk.start();
                });

                gc3.setFill(Color.WHITE); /* Top and bottom traffic lights allow the cars to drive. */
                gc3.fillOval(130 , 1 , 30 , 30);
                gc3.setFill(Color.GREEN);
                gc3.fillOval(130 , 31 , 30 , 30);
                gc3.setFill(Color.RED);
                gc3.fillRect(139 , 61 , 12 , 20);
                gc3.setFill(Color.WHITE);
                gc3.fillRect(139 , 81 , 12 , 20);

                gc4.setFill(Color.WHITE);
                gc4.fillOval(104 , 1 , 30 , 30);
                gc4.setFill(Color.GREEN);
                gc4.fillOval(104 , 31 , 30 , 30);
                gc4.setFill(Color.RED);
                gc4.fillRect(113 , 61 , 12 , 20);
                gc4.setFill(Color.WHITE);
                gc4.fillRect(113 , 81 , 12 , 20);
            }
            else { /* Switching to top/bottom. */
                walk = new ThreadWalk(gc3 , gc4 , time , TOP_BOTTOM); /* New thread for pedestrian traffic light flickering, for top and bottom. */

                gc1.setFill(Color.WHITE); /* Left and right traffic lights allow the cars to drive. */
                gc1.fillOval(270 , 1 , 30 , 30);
                gc1.setFill(Color.GREEN);
                gc1.fillOval(270 , 31 , 30 , 30);
                gc1.setFill(Color.RED);
                gc1.fillRect(279 , 61 , 12 , 20);
                gc1.setFill(Color.WHITE);
                gc1.fillRect(279 , 81 , 12 , 20);

                gc2.setFill(Color.WHITE);
                gc2.fillOval(270 , 1 , 30 , 30);
                gc2.setFill(Color.GREEN);
                gc2.fillOval(270 , 31 , 30 , 30);
                gc2.setFill(Color.RED);
                gc2.fillRect(279 , 61 , 12 , 20);
                gc2.setFill(Color.WHITE);
                gc2.fillRect(279 , 81 , 12 , 20);

                gc3.setFill(Color.RED); /* Top and bottom traffic lights allow the pedestrians to pass. */
                gc3.fillOval(130 , 1 , 30 , 30);
                gc3.setFill(Color.WHITE);
                gc3.fillOval(130 , 31 , 30 , 30);
                gc3.setFill(Color.WHITE);
                gc3.fillRect(139 , 61 , 12 , 20);
                gc3.setFill(Color.GREEN);
                gc3.fillRect(139 , 81 , 12 , 20);

                gc4.setFill(Color.RED);
                gc4.fillOval(104 , 1 , 30 , 30);
                gc4.setFill(Color.WHITE);
                gc4.fillOval(104 , 31 , 30 , 30);
                gc4.setFill(Color.WHITE);
                gc4.fillRect(113 , 61 , 12 , 20);
                gc4.setFill(Color.GREEN);
                gc4.fillRect(113 , 81 , 12 , 20);

                Platform.runLater(() -> {
                    /* Running the flickering thread upon the top/bottom traffic lights so while leftRight == false the pedestrian's traffic light flickers. */
                    walk.start();
                });
            }

            try{
                sleep(time);
                /* Putting the thread to sleep so the lights stay as they are for the time parameter passed, and then the sleep ends. */
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
            leftRight = !leftRight; /* After the sleep, the switch occurs between the traffic light colors. */
        }
    }

    private void draw(GraphicsContext gc1 , GraphicsContext gc2 , GraphicsContext gc3 , GraphicsContext gc4) {
        /* Drawing each one of the traffic lights. Top two ovals are for cars, bottom rectangles are for pedestrians. */
        gc1.strokeOval(270 , 1 , 30 , 30);
        gc1.strokeOval(270 , 31 , 30 , 30);
        gc1.strokeRect(279 , 61 , 12 , 20);
        gc1.strokeRect(279 , 81 , 12 , 20);

        gc2.strokeOval(270 , 1 , 30 , 30);
        gc2.strokeOval(270 , 31 , 30 , 30);
        gc2.strokeRect(279 , 61 , 12 , 20);
        gc2.strokeRect(279 , 81 , 12 , 20);

        gc3.strokeOval(130 , 1 , 30 , 30);
        gc3.strokeOval(130 , 31 , 30 , 30);
        gc3.strokeRect(139 , 61 , 12 , 20);
        gc3.strokeRect(139 , 81 , 12 , 20);

        gc4.strokeOval(104 , 1 , 30 , 30);
        gc4.strokeOval(104 , 31 , 30 , 30);
        gc4.strokeRect(113 , 61 , 12 , 20);
        gc4.strokeRect(113 , 81 , 12 , 20);
    }

}
// End of class TrafficLightsController.