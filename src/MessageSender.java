import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class MessageSender {

    private static final String DEFAULT_HOST = "localhost";

    private static final int DEFAULT_PORT = 2000;

    private String host;

    private int port;

    private Socket socket = null;

    private PrintWriter out;

    private static final String CONNECTED_MESSAGE = "Connected to host ";

    private static final String FAILED_MESSAGE = "Failed to establish connection";

    private String author = "Anonymous: ";

    public MessageSender(String hostName, int port) throws IOException {
        setHost(hostName);
        setPort(port);
        try {
            while (socket==null) {
                System.out.println("Connecting to server...");
                socket = new Socket(getHost(), getPort());

            }
            if (validateConnection(socket)) {
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.ISO_8859_1), true);
            }
        }catch(ConnectException e){
            System.out.println(FAILED_MESSAGE);

        }catch(IOException e){
            System.out.println(FAILED_MESSAGE);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String customHost) {
        this.host = customHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public boolean validateConnection(Socket socket) {
        if(socket==null){
            System.out.println(FAILED_MESSAGE);
            return false;
        }

        if (socket.isConnected()) {
            System.out.println(CONNECTED_MESSAGE + socket.getInetAddress().getHostName() + " on port " + socket.getPort());
        }else{
                System.out.println(FAILED_MESSAGE);
                return false;
            }
        return socket.isConnected();
    }

    public void close() {
        try {
            out.close();
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
