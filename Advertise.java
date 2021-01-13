import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Advertise
 */
public class Advertise implements Runnable {
    private DatagramSocket socket;
    private byte[] msg = "request".getBytes();
    private boolean running;
    private int port;

    public Advertise(DatagramSocket socket, int port){
        this.socket = socket;
        this.running = true;
        this.port = port;
    }

    @Override
    public void run() {
        //every two seconds a request is send to ports 50000-50010 except for the port this client is running on
        while (running) {
            try {
                for (int i = 0; i < 11; i++) {
                    if (!((50000+i) == port)) {
                        DatagramPacket packet = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), 50000+i);
                        socket.send(packet);
                    }
                }
                Thread.sleep(2000);
                }
            catch (Exception e) {
                System.out.println(e.getMessage());
                }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}