/**
 * Calls randomization method of first entry in local Status list every 10 secs
 */
public class Randomize implements Runnable{

    private Status toRandomize;
    private boolean running;


    public Randomize(Status toUpdate) {
        this.toRandomize = toUpdate;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            toRandomize.wertGenerator();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setRunning(boolean rng) {
        running = rng;
    }
}