// ClientController represents the client's GUI which receives messages from the server using a multicast UDP channel.
import java.net.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javax.swing.*;
import java.io.IOException;

public class ClientController {

    @FXML
    private TextArea screenText;
    private String text = "";
    private  String groupIP;
    private final int SERVERS_PORT = 6666;
    private MulticastSocket socket;
    private boolean joined = false;

    @FXML
    void clearPressed(ActionEvent event) {
        screenText.clear();
        text = ""; /// fix if extra past text is still added
    }

    public void initialize() {
        screenText.setEditable(false);
    }

    @FXML
    void leavePressed(ActionEvent event) {
        // Handles the pressing of the "leave" button which allows users to leave the multicast channel in order to stop receiving messages.
        if (this.joined == false) { // If the user is already not in a channel we notify.
            JOptionPane.showConfirmDialog(null , "Leaving failed, you were not part of the channel currently." , "Not in the channel" , JOptionPane.CLOSED_OPTION);
        }
        else {
            try {
                InetAddress group = InetAddress.getByName(groupIP);
                socket.leaveGroup(group); // Leaving the channel.
                JOptionPane.showConfirmDialog(null , "You have left the channel." , "Goodbye" , JOptionPane.CLOSED_OPTION);
                this.joined = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                socket.close();
            }
        }

    }

    @FXML
    void signUpPressed(ActionEvent event) {
        // Handles the pressing of the "sign up" button which allows users to join the multicast channel in order to receive messages.
        if (this.joined == true) { // If the user has already joined a channel we notify.
                JOptionPane.showConfirmDialog(null , "Joining failed, you are a part of the channel already." , "Already in the channel" , JOptionPane.CLOSED_OPTION);
        }
        else {
            try {
                groupIP = JOptionPane.showInputDialog("Please enter the server's IP address (four numbers from 0 to 255 separated by dots):"); // Gathering the server's IP.
                if (groupIP.equals("230.0.0.1") == false) {
                    groupIP = "230.0.0.1";
                }
                socket = new MulticastSocket(SERVERS_PORT);
                InetAddress group = InetAddress.getByName(groupIP);
                socket.joinGroup(group); // Joining the channel.
                JOptionPane.showConfirmDialog(null , "You have joined the channel successfully." , "Welcome!" , JOptionPane.CLOSED_OPTION);
                MessageReceiverThread receiverThread = new MessageReceiverThread(socket , screenText); // Beginning a thread that constantly waits to receive messages.
                receiverThread.start();
                this.joined = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
// End of class ClientController.