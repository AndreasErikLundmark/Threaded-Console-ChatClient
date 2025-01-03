public class RecieveChatThread extends Thread {


    private boolean stop = false;

    private final MessageReciever messageReciever;

    private static final int DEFAULT_SLEEP_MILLIS = 1000;

    private int sleepMillis = DEFAULT_SLEEP_MILLIS;

    private TerminateChat terminateChat = null;

    public RecieveChatThread(MessageReciever messagerReciever) {
        validateMessageReciever(messagerReciever);
        this.messageReciever = messagerReciever;
    }

    private synchronized boolean running() {
        return !this.stop;
    }

    public synchronized void stopThis() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (running()) {
            try {
                if (terminateChat.isStopped()) {
                    stopThis();
                    break;
                }
                messageReciever.getMessage();

                if (!terminateChat.isStopped()) {

                    System.out.println(messageReciever.printHeader());
                    System.out.println(messageReciever.printChat());
                    System.out.print("\n");
                    System.out.flush();
                }
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Chat connection lost");
            } finally {
                if (terminateChat.isStopped()) {
                    System.out.println("Chat receiver stopped");
                }
            }
        }
    }

    /**
     * Sets the length in between every print done by run.
     *
     * @param sleepMillis the delay in milliseconds between every print to console
     */
    public void setSleepMillis(int sleepMillis) {
        this.sleepMillis = sleepMillis;
    }


    public boolean validateMessageReciever(MessageReciever messageReciever) {
        if (messageReciever == null) {
            throw new IllegalArgumentException("MessageReciever can not be null");
        }
        return true;
    }

    public void setTerminateChat(TerminateChat terminateChat) {
        this.terminateChat = terminateChat;
    }
}
