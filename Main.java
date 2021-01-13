import java.util.Scanner;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Bitte Standort eingeben:");
        Scanner input = new Scanner(System.in);
        try {

            //start client thread
            Client client = new Client(input.nextLine());
            Thread thrd = new Thread(client);
            thrd.start();
            System.out.println("\"print\" eingeben um bekannte Messwerte auszugeben, \"quit\" eingeben um den client zu beenden");
            boolean running = true;

            //input loop
            while (running) {
                String mostRecentInput = input.nextLine();
                if (mostRecentInput.equals("print")) {
                    //prints readable representation of locally known status
                    client.printStatusList();
                } else if (mostRecentInput.equals("quit")) {
                    //stops client thread
                    client.setRunning(false);;
                    System.exit(0);
                } else {
                    System.out.println("Unbekannter Befehl, \"print\" eingeben um bekannte Messwerte auszugeben, \"quit\" eingeben um den client zu beenden");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
} 
