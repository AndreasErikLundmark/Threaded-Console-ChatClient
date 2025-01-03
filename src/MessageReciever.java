import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageReciever {

    //    fetch host and port from MessageSender

    private final static String FAILED_SENDER = "MessageSender is not connected";
    private final static String CHAT_HEADER = "<--------Chat--------> ";
    private Socket socket;
    private BufferedReader in;
    private MessageQue messageQue;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    public MessageReciever(MessageSender messageSender) throws IOException {
        if (validateMessageSender(messageSender)) {
            in = new BufferedReader(new InputStreamReader(messageSender.getSocket().getInputStream()));
            messageQue = new MessageQue();
        } else {
            System.out.println(FAILED_SENDER);
        }
    }

    public String getMessage() {
        LocalDateTime dateTime = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        String result = "";
        try {
            result = in.readLine();
        } catch (IOException e) {
            e.getMessage();
        }
        sb.append(dtf.format(dateTime));
        sb.append(" - ");
        sb.append(result);
        messageQue.addMsg(sb.toString());
        return sb.toString();
    }

    public String printHeader() {
        return CHAT_HEADER;
    }

    public String printChat() {
        return messageQue.toString();
    }

    public boolean validateMessageSender(MessageSender messageSender) {
        if (messageSender == null) {
            return false;
        }
        return messageSender.getSocket().isConnected();
    }

    public void close() {
        try {
            in.close();
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
