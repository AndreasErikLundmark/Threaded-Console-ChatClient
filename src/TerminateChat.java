public class TerminateChat {

    private volatile boolean stop = false;

    private MessageReciever messageReciever;
    private MessageSender messageSender;

    public void execute() {
        this.stop = true;


    }

    public boolean isStopped() {
        return this.stop;
    }
}
