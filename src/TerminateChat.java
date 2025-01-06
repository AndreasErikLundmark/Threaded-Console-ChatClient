public class TerminateChat {

    private volatile boolean stop = false;

    public void execute() {
        this.stop = true;
    }

    public boolean isStopped() {
        return this.stop;
    }
}
