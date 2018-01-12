import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by panchal on 4/25/15.
 */
public class Reader implements Runnable {

    private final Socket socket;
    private InputStream inputStream;
    private String timeStamp;

    public Reader(Socket socket, InputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                byte[] readBuffer = new byte[200];
                int num = inputStream.read(readBuffer);
                if (num > 0) {
                    byte[] arrayBytes = new byte[num];
                    System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
                    String receivedMessage = new String(arrayBytes, "UTF-8");
                    timeStamp = new java.util.Date().toString();
                    System.out.println(timeStamp + ": " + receivedMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
