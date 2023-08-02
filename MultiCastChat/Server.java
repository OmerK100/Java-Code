// Server class represents the server that communicates by sending messages to the clients signed up to the channel. The server and receiver use multicast.
import java.io.*;
import java.net.*;

public class Server {

    private DatagramSocket socket;
    private String group = "230.0.0.1"; // Server's IP and port.
    private int port = 6666;
    private final int SIZE = 256;

    public Server() {
        try{
            socket = new DatagramSocket();
        } catch(SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendToGroup(String msg) {
        // Receives a message from the GUI, creates a new packet and sends it through a multicast channel to all the signed up clients.
        DatagramPacket packet;
        try{
            InetAddress address = InetAddress.getByName(group);
            byte[] buf = new byte[SIZE];
            buf = msg.getBytes();
            packet = new DatagramPacket(buf , buf.length , address , port);
            socket.send(packet); // Sending the packet that contains the message.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
// End of class Server.