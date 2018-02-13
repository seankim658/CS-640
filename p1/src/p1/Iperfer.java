package p1;

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
				
			}
		}
		
		// exits program normally
		System.exit( 0 );
		
	}
	
	// run in client mode
	public static void clientMode( String[] args ) {
		
		// if too many arguments, print error and exit with status 1
		if( args.length != 7 ) {
			System.err.println( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		
		// check for correct arguments 
		if( !args[1].equals( "-h" ) ) {
			System.err.println( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		if( !args[3].equals( "-p" ) ) {
			System.err.println( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		if( !args[5].equals( "-t" ) ) {
			System.err.println( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		
		// argument variables
		String serverHost = args[2];
		String tempServerPort = args[4];
		String tempTime = args[6];
		
		// convert server port and time to Integers 
		int serverPort = Integer.parseInt( tempServerPort );
		int time = Integer.parseInt( tempTime );
		
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


