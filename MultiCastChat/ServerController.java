// ServerController represents the server's GUI that allows the server's manager to send messages to all the clients that are signed up to the channel.
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ServerController {

    private String textSaved = "";
    private Server server;

    @FXML
    private TextArea senderText;

    public void initialize() { // Initializing a new server, when pressing ENTER in the text area, a message is being sent to all the clients that are signed up.
        server = new Server();
        senderText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    String newText = senderText.getText();
                    String sendToClient = newText.substring(textSaved.length() , newText.length()); // Cutting the new message from the rest of the old text.
                    server.sendToGroup(sendToClient); // Passing the message to the server for sending.
                    textSaved = newText; // Saving the new text in a temp string.
                }
            }
        });
    }

}
// End of class ServerController.