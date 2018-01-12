import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by panchal on 4/25/15.
 */
public class Writer implements Runnable {

    private final Socket socket;
    private OutputStream outputStream;

    public Writer(Socket socket, OutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                sleep(100);
                String typedMessage = inputReader.readLine();
                if (typedMessage != null && typedMessage.length() > 0) {
                    synchronized (socket) {
                        outputStream.write(typedMessage.getBytes("UTF-8"));
                        sleep(100);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
