/* ThreadWalk class represents the thread that runs teh flickering of the pedestrian traffic lights while the traffic lights simulation is on. */
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ThreadWalk extends Thread {

    private GraphicsContext f1; /* Store the traffic lights. */
    private GraphicsContext f2;
    private int time; /* Time between traffic light color switching. */
    private int num; /* Choosing between flickering left/right and top/bottom.*/
    private final int LEFT_RIGHT = 1;
    private final int TOP_BOTTOM = 2;

    public ThreadWalk(GraphicsContext f1 , GraphicsContext f2 , int time , int num) { /* Thread constructor with parameters. */
        this.f1 = f1;
        this.f2 = f2;
        this.time = time;
        this.num = num;
    }

    public void run() { /* Runs the thread. */
        boolean mainTrafficLightSleep = true; /* While the current lights are on in the simulation. */
        int counter = 0; /* Counting the number of flickers (10 flickers for each round of the simulation). */
        boolean flicker = true; /* Switch between white and green (the flicker). */
        while (mainTrafficLightSleep == true) {
            if (flicker == true) {
                if (this.num == LEFT_RIGHT) { /* Coloring the pedestrian traffic lights green, left and right. */
                    this.f1.setFill(Color.GREEN);
                    this.f1.fillRect(279 , 81 , 12 , 20);
                    this.f2.setFill(Color.GREEN);
                    this.f2.fillRect(279 , 81 , 12 , 20);
                }
                else if (this.num == TOP_BOTTOM) { /* Coloring the pedestrian traffic lights green, top and bottom. */
                    this.f1.setFill(Color.GREEN);
                    this.f1.fillRect(139 , 81 , 12 , 20);
                    this.f2.setFill(Color.GREEN);
                    this.f2.fillRect(113 , 81 , 12 , 20);
                }
            }
            else {
                if (this.num == LEFT_RIGHT) { /* Coloring the pedestrian traffic lights white, left and right. */
                    this.f1.setFill(Color.WHITE);
                    this.f1.fillRect(279 , 81 , 12 , 20);
                    this.f2.setFill(Color.WHITE);
                    this.f2.fillRect(279 , 81 , 12 , 20);
                }
                else if (this.num == TOP_BOTTOM) { /* Coloring the pedestrian traffic lights white, top and bottom. */
                    this.f1.setFill(Color.WHITE);
                    this.f1.fillRect(139 , 81 , 12 , 20);
                    this.f2.setFill(Color.WHITE);
                    this.f2.fillRect(113 , 81 , 12 , 20);
                }
            }

            try{
                /* Putting the flickering thread to sleep so the lights stay as they are for the 10th of the time parameter, and then the sleep ends. */
                sleep(time / 10);
                counter++; /* Counting the flickers. */
                if (counter >= 10) { /* After 10 flickers, the traffic light colors switch, we end the thread. */
                    mainTrafficLightSleep = false;
                }
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
            flicker = !flicker; /* Switching the flickering colors after the flickering sleep. */
        }
    }

}
// End of class ThreadWalk.