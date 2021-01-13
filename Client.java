import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Client
 */
public class Client implements Runnable{
    private StatusList statusList;
    private DatagramSocket socket;
    public boolean running;

    public Client(String standort) throws IOException{
        
        this.statusList = new StatusList(standort);
        try {
            socket = findFreePort();
        } catch (IOException e) {
            throw e;
        }
        running = true;
        
        System.out.println("Client läuft auf port " + socket.getLocalPort());
    }

    @Override
    public void run() {

        //Start thread to randomize status of this client
        Randomize rnd = new Randomize(statusList.getLocalyKnownStatus().get(0));
        Thread rndthrd = new Thread(rnd);
        rndthrd.start();

        //start thread to advertise to other clients
        Advertise ad = new Advertise(socket, socket.getLocalPort());
        Thread thrd2 = new Thread(ad);
        thrd2.start();

        //
        while (running) {
            try {
                //create byte array to deposit incoming UDP packets into
                byte[] buf = new byte[8192];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                //blocking until packet is received
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());


                if (received.equals("request")) {
                    //if request is received local status is sent as structured string to the requesting party
                    byte[] msg = statusList.toStructuredString().getBytes();
                    DatagramPacket toSend = new DatagramPacket(msg , msg.length, packet.getAddress(), packet.getPort());
                    socket.send(toSend);
                }
                else if (received.startsWith("Standort->")) {
                    //if formated status is received, it gets incorporated into the local list 
                    statusList.update(StatusList.parse(received));
            }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        //stopping all created threads if this thread is stopped
        rnd.setRunning(false);
        ad.setRunning(false);

        //close udp socket
        socket.close();
    }

    //finds free port in the range 50000-50010 and creates UDP socket on this port
    private DatagramSocket findFreePort() throws IOException{
        for (int i = 0; i < 11; i++) {
            try {
                return new DatagramSocket(50000+i, InetAddress.getByName("localhost"));
            } catch (SocketException se) {
                continue;
            }
        }
        throw new IOException("Kein freier port in der spezifizierten portrange gefunden");
    }

    public void printStatusList() {
        System.out.println(statusList);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
} 
