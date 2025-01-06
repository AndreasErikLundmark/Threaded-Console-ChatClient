import java.util.LinkedList;
import java.util.Queue;

public class MessageQue {

    Queue<String> messageQueue;

    private int maxQeueSize = 100;

    public MessageQue() {
        this.messageQueue = new LinkedList<>();
    }

    public void addMsg(String message) {
        this.messageQueue.add(message);
        qeueSizeHandler();
    }

    public String getMsg() {
        return this.messageQueue.poll();
    }

    private void qeueSizeHandler() {
        if (messageQueue.size() < maxQeueSize) {
            return;
        } else {
            messageQueue.poll();
        }

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (String s : messageQueue) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
