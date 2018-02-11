package p1;

public class Iperfer {

	public static void main( String[] args ) {
		
		// if there are not enough arguments in, print error message and exit program with status 1
		if( args.length < 3 ) {
			System.err.println( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		
		// check if server or client mode was called, if neither, print error message and exit with status 1
		if( args[0].equals( "-c" ) ) {
			clientMode( args );
		} else if( args[0].equals( "-s" ) ) {
			serverMode( args );
		} else {
			System.err.print( "Error: missing or additional arguments" );
			System.exit( 1 );
		}
		
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


