// Class ClientThread handles the client's request for a new trivia question by creating a thread that communicates with the server and receives a question.
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private ClientController cont;
    private String ip;
    private final int PORT = 3333;

    public ClientThread(ClientController cont , String ip) {
        this.cont = cont;
        this.ip = ip;
    }

    @Override
    public void run() { // Runs the thread.
        super.run();
        try {
            this.handleReadAndWrite();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public void handleReadAndWrite() throws Exception {
        // Connects to the server through a port and ip, receives a Question object and sets it within the client's GUI.
        Socket s = new Socket(this.ip , PORT);
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        String[] question = (String[])objectInputStream.readObject(); // Reading and setting the question with the answers.
        this.cont.setQuestion(question);
        objectInputStream.close();
        inputStream.close();
        s.close();
    }

}
// End of class ClientThread.