import java.io.IOException;
import java.util.Scanner;

public class Main {
    /**
     * Main class to start the chat app.
     * User can provide host or port or both as command line args when launching the app.
     * For example: java Main localhost 2000
     * or java Main localhost
     * or java Main
     * If no args are given default host and port will be used.
     * The MessageReceiver and MessageSender are run is separate threads."SendChatThread" and "ReceiveChatThread".
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        String defaultHost = "localhost";
        int defaultPort = 2000;
        MessageSender messageSender = new MessageSender(defaultHost, defaultPort);

        if(args != null && args.length > 0){
            messageSender.setHost(args[0]);
        }
        if(args != null && args.length > 1){
            messageSender.setPort(Integer.parseInt(args[1]));
        }

        MessageReciever messageReciever = new MessageReciever(messageSender);

        Scanner scanner = new Scanner(System.in);

        SendChatThread sendChatThread = new SendChatThread(messageSender, scanner);

        RecieveChatThread recieveChatThread = new RecieveChatThread(messageReciever);

        TerminateChat terminateChat = new TerminateChat();

        sendChatThread.setTerminateChat(terminateChat);
        recieveChatThread.setTerminateChat(terminateChat);

        //Console input for author name
        System.out.print("Enter your name: ");
        String author = scanner.nextLine();

        while (author.length() == 0) {
            System.out.println("You must enter a name");
            System.out.print("Enter your name: ");
            author = scanner.nextLine();
        }
        messageSender.setAuthor(author + ": ");
        System.out.println("Starting chat.. type 'exit' to stop chat");

        //Start threads for sending and receiving messages
        sendChatThread.start();
        recieveChatThread.start();

        try {
            while (!terminateChat.isStopped()) {
                Thread.sleep(1000);
            }
        } finally {

            sendChatThread.stopThis();
            recieveChatThread.stopThis();
            scanner.close();
        }
        System.out.println("Application terminated.");
    }
}