import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by panchal on 4/9/15.
 */
public class ChatClient {

    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    public ChatClient() {}

    public void createSocket() {
        try {
            socket = new Socket("localHost", 3339);
            System.out.println("Connected to server.");
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            initReaderThread();
            initWriterThread();
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
        ChatClient myChatClient = new ChatClient();
        myChatClient.createSocket();
    }

}
