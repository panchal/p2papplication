import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by panchal on 4/9/15.
 */
public class ChatServer {

    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    public ChatServer() {
    }

    public void createSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(3339);
            System.out.println("Server ready to accept connections.");
            int count  = 0;
            // Allow serve to accept multiple client connections.
            while (true) {
                socket = serverSocket.accept(); // This is blocking call.
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                System.out.println("Client #" + ++count + " connected.");
                initReaderThread();
                initWriterThread();
            }
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to localhost. " + e.getMessage());
        }
    }

    public void initReaderThread() {
        Reader reader = new Reader(socket, inputStream);
        Thread readThread = new Thread(reader);
        readThread.setPriority(Thread.MAX_PRIORITY);
        readThread.start();
    }

    public void initWriterThread() {
        Writer writer = new Writer(socket, outputStream);
        Thread writeThread = new Thread(writer);
        writeThread.setPriority(Thread.MAX_PRIORITY);
        writeThread.start();
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.createSocket();
    }

}
