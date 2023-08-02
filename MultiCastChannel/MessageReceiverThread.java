// MessageReceiverThread is working while the user has joined a channel and is constantly waiting for new messages.
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.Date;

public class MessageReceiverThread extends Thread{

    private MulticastSocket socket;
    private TextArea messageArea;
    private final int SIZE = 1024;

    public MessageReceiverThread(MulticastSocket socket, TextArea messageArea) {
        this.socket = socket;
        this.messageArea = messageArea;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (socket.isClosed() == false) {
            // While the user is signed up to a channel, we gather messages from the server and append them to the user's screen.
            try {
                socket.receive(packet);
                String message = new String(packet.getData() , 0 , packet.getLength());
                Platform.runLater(() -> {
                    messageArea.appendText(message + new Date().toString() + "\n"); // Adding the new message to the screen with the current date.
                });
            } catch (IOException e) {

            }
        }
    }

}
// End of class MessageThreadReceiver.