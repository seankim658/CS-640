import java.io.IOException;
import java.io.*;
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
                } catch (ArrayIndexOutOfBoundsException a) {
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
    public static void clientMode(String hostName, int portNumber, int time) {
        byte[] chunk = new byte[1000];

        long dataSize = 0;
        long endTime = 0;


        try (Socket c = new Socket(hostName, portNumber);) {

            //calculate end time after connecting to server
            endTime = System.currentTimeMillis() + (time * 1000);


            while (endTime > System.currentTimeMillis()) {
                c.getOutputStream().write(chunk, 0, 1000);
                dataSize += 1;
            }

            System.out.println("sent=" + dataSize + " KB rate=" + (((double) dataSize * 8.0 / 1000.0) / (double) time) + " Mbps");

        } catch (IOException e) {
            System.out.println("Error: IOException client");
            System.exit(1);
        }
    }

    // run in server mode
    public static void serverMode(int portNum) {

        // variables for tracking how much data was sent by the server and for how long did the client send data
        long dataReceived = 0;
        long startTime = 0;
        long endTime = 0;

        int readReturn = 0;

        double dataSpeed = 0;

        byte[] chunk = new byte[1000];


        try (
                ServerSocket server = new ServerSocket(portNum);
                Socket client = server.accept();
        ) {


            startTime = System.currentTimeMillis();

            while (readReturn > -1) {
                readReturn = client.getInputStream().read(chunk);
                if (readReturn > 0) {
                    dataReceived += readReturn;
                }
            }

            endTime = System.currentTimeMillis();

        } catch (IOException e) {
            System.out.println("Error: IOException");
            System.exit(1);
        }


        //Calculate values for output
        dataReceived = dataReceived / 1000;

        dataSpeed = ((dataReceived * 8.0) / 1000.0) / ((endTime - startTime) / 1000.0);


        System.out.println("received=" + dataReceived + " KB rate=" + dataSpeed + " Mbps");

    }

}


