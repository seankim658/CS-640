package p1;
import java.net.*;

public class Iperfer {

	public static void main( String[] args ) {
		
		// if there are not enough arguments in, print error message and exit program with status 1
		if( args.length < 3 ) {	
			System.err.println( "Error: missing or additional arguments" );
			System.exit( 1 );			
		}
		
		// variables needed for running Iperfer in client mode
		boolean clientMode = false;
		boolean hostNamePresent = false;
		String hostName; 
		boolean portPresent = false;
		int portNum = 0;
		boolean timePresent = false;
		int time = -1;
		
		// variable needed for running Iperfer in server mode 
		boolean serverMode = false;
		
		// traverse through input to get correct mode and corresponding variables 
		for( int i = 0; i < args.length; i++ ) {
			
			if( args[i].equals( "-c" ) ) {
				clientMode = true;
				
			} else if( args[i].equals( "-s" ) ) {
				serverMode = true;
				
			} else if( args[i].equals( "-h" ) ) {
				hostNamePresent = true; 
				hostName = args[i + 1];
				
			} else if( args[i].equals( "-p" ) ) {
				portPresent = true;
				
				try {
					portNum = Integer.parseInt( args[i + 1] );
				} catch( NumberFormatException e ) {
					System.out.println( "Error: port number must be in the range 1024 to 65535" );
					System.exit( 1 );
				}
				
				if( portNum < 1024 || portNum > 65535 ) {
					System.out.println( "Error: port number must be in the range 1024 to 65535" );
					System.exit( 1 );
				}
				
			} else if( args[i].equals( "-t" ) ) {
				timePresent = true;
				
				try {
					time = Integer.parseInt( args[i + 1] );
				} catch( NumberFormatException e ) {
					System.out.println( "Error: missing or additional arguments" );
					System.exit( 1 );
				}
				
				if( time < 0 ) {
					System.out.println( "Error: missing or additional arguments" );
					System.exit( 1 );
				}
				
			} else {
				System.out.print( "Error: missing or additional arguments" );
				System.exit( 1 );
				
			}
		}
		
		// call client or server mode method based on input
		if( clientMode && hostNamePresent && portPresent && timePresent ) {
			clientMode( hostName, portNum, time );
		} else if( serverMode && portPresent ) {
			serverMode( portNum );
		} else {
			System.out.println( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		
		// exits program normally
		System.exit( 0 );
		
	}
	
	// run in client mode
	public static void clientMode(String hostName, int port, int time) {
        double rate = 0.0;
        long dataSize = 0;
        long endTime = 0;
        byte[] chunk = new byte[1000];


        Socket c = new Socket();
        InetSocketAddress remote = new InetSocketAddress(hostname, port);


        try{

            client.connect(remote);


            //calculate end time after connecting to server
            endTime = System.nanoTime() + (time * 1000000000);


            while(System.nanoTime() < endTime){
                c.getOutputStream.write(chunk);
                dataSize += 1000;
            }


            c.close();

            //calculate rate
            rate = (((double)dataSize * 8.0/1000.0) / (double)time);

            System.out.println("sent=" + dataSize + " KB rate=" + rate + " Mbps");

        }
	}
	
	// run in server mode
	public static void serverMode( String[] args ) {
		
		// if too many arguments, print error and exit with status 1
		if( args.length != 3 ) {
			System.err.println( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		
	}
	
}


