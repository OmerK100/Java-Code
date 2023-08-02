// ServerThread class represents a thread created by the server that passes a trivia question to the client after the client's request.
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread extends Thread{

    private Socket s;
    private String[] question;

    public ServerThread(Socket socket , String[] question) { // Creating the thread using a provided socket and a trivia questions with its answers.
        this.s = socket;
        this.question = question;
    }

    @Override
    public void run() { // Running the thread by passing the data to sending to the client.
        super.run();
        try{
            handleReadAndWrite(this.question);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void handleReadAndWrite(String[] question) throws Exception {
        // Creates an output TCP stream that sends the question and its answers to the client.
        OutputStream outputStream = this.s.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(question);
        objectOutputStream.close();
        outputStream.close();
    }

}
// End of class ServerThread.