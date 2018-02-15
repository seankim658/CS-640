import java.io.IOException;
import java.net.*;

public class Iperfer {

    public static void main(String[] args) {

        // if there are not enough arguments in, print error message and exit program with status 1
        if (args.length < 3) {
            System.err.println("Error: missing or additional arguments");
            System.exit(1);
        }

        // variables needed for running Iperfer in client mode
        boolean clientMode = false;
        boolean hostNamePresent = false;
        String hostName = null;
        boolean portPresent = false;
        int portNum = 0;
        boolean timePresent = false;
        int time = -1;

        // variable needed for running Iperfer in server mode
        boolean serverMode = false;

        // traverse through input to get correct mode and corresponding variables
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-c")) {
                clientMode = true;

            } else if (args[i].equals("-s")) {
                serverMode = true;

            } else if (args[i].equals("-h")) {
                hostNamePresent = true;
                hostName = args[i + 1];
                i++;

            } else if (args[i].equals("-p")) {
                portPresent = true;

                try {
                    portNum = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Error: port number must be in the range 1024 to 65535");
                    System.exit(1);
                }

                if (portNum < 1024 || portNum > 65535) {
                    System.out.println("Error: port number must be in the range 1024 to 65535");
                    System.exit(1);
                }

                i++;

            } else if (args[i].equals("-t")) {
                timePresent = true;

                try {
                    time = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Error: missing or additional arguments");
                    System.exit(1);
                }

                if (time < 0) {
                    System.out.println("Error: missing or additional arguments");
                    System.exit(1);
                }

                i++;

            } else {
                System.out.print("Error: missing or additional arguments");
                System.exit(1);

            }
        }

        // call client or server mode method based on input
        if (clientMode && hostNamePresent && portPresent && timePresent) {
            clientMode(hostName, portNum, time);
        } else if (serverMode && portPresent) {
            serverMode(portNum);
        } else {
            System.out.println("Error: missing or additional arguments");
            System.exit(1);
        }

        // exits program normally
        System.exit(0);

    }

    // run in client mode
    public static void clientMode(String hostName, int port, int time) {
        double rate = 0.0;
        long dataSize = 0;
        long endTime = 0;
        byte[] chunk = new byte[1000];


        Socket c = new Socket();
        InetSocketAddress remote = new InetSocketAddress(hostName, port);

        try {

            c.connect(remote);
            System.out.println("Error: Connect");

            //calculate end time after connecting to server
            endTime = System.nanoTime() + (time * 1000000000);


            while (System.nanoTime() < endTime) {
                c.getOutputStream().write(chunk);
                dataSize += 1000;
            }


            c.close();

            //calculate rate
            rate = (((double) dataSize * 8.0 / 1000.0) / (double) time);

            System.out.println("sent=" + dataSize + " KB rate=" + rate + " Mbps");

        } catch (IOException e) {
            System.out.println("Error: IOException client");
            System.exit(1);
        }
    }

    // run in server mode
    public static void serverMode(int portNum) {

        // variables for tracking how much data was sent by the server and for how long did the client send data
        double dataReceived = 0;
        double dataSpeed = 0;
        long startTime = 0;
        long endTime = 0;
        double elapsedTime;
        byte[] chunk = new byte[1000];
        int readReturn = 0;

        try {
            ServerSocket server = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress(portNum);
            server.bind(address);
            Socket client = server.accept();
            startTime = System.currentTimeMillis();
            while (readReturn > -1) {
                readReturn = client.getInputStream().read(chunk, 0, 1000);
                if (readReturn >= 1) {
                    dataReceived = readReturn / 1000;
                }
            }
            endTime = System.currentTimeMillis();
            client.close();
            server.close();

        } catch (IOException e) {
            System.out.println("Error: IOException");
            System.exit(1);
        }

        elapsedTime = (endTime - startTime) / 1000.0;
        dataSpeed = ((dataReceived * 8) / 1000.0) / elapsedTime;
        System.out.println("received=" + dataReceived + " KB rate=" + dataSpeed + " Mbps");

    }

}


