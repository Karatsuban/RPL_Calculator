import java.io.*;
import java.net.*;

public class CalcServer{



    private boolean logSession;
    private boolean replaySession;
    private boolean localSession;
    private boolean singleUser;
    private boolean sharedSession;
	private boolean showHelp;
	private int pileSize;

    private String logFilename;

	private InputStream userIn;
	private OutputStream userOut;

	private int port = 12345;
	ServerSocket servSocket;
	Socket socket;


	public CalcServer(String[] args){

        Parser argsParser = new Parser(args);

		// default parameters
        this.logSession = false; // no log
        this.replaySession = false; // no replay
        this.localSession = true; // local
        this.singleUser = true; // single user
        this.sharedSession = false;  // no shared session
		this.showHelp = false;

		this.pileSize = -1;
		String defaultLogFilename = "default_logfile.log";

        if (argsParser.getToken().equals("size")){
            this.pileSize = Integer.valueOf(argsParser.getAhead(1));
			argsParser.advance(2);
        }


		String tempArg = "";

        switch (argsParser.consume()){
            case "user":
				while (!argsParser.getToken().equals("NO_TOKEN")){
					switch (tempArg = argsParser.consume()){
	                    case "log":
	                        this.logSession = true;
							if (argsParser.hasTokens())
		                        this.logFilename = argsParser.consume(); // get the file name
							else
								this.logFilename = defaultLogFilename;
	                        break;
	                    case "replay":
	                        this.replaySession = true;
							if (argsParser.hasTokens())
		                        this.logFilename = argsParser.consume(); // get the file name
							else
								this.logFilename = defaultLogFilename;
	                        break;
	                    case "local":
	                        this.localSession = true;
	                        break;
	                    case "remote":
	                        this.localSession = false;
	                        break;
						default:
							System.out.println("Unknown argument: "+tempArg);
                	}
				}
			break;

            case "users":
				this.singleUser = false; // multiple users
				this.localSession = false; // remote for multiple users
				while (!argsParser.getToken().equals("NO_TOKEN")){
	                switch (tempArg = argsParser.consume()){
	                    case "remote": // do nothing
	                        break;
	                    case "shared":
	                        this.sharedSession = true;
							break;
						case "not-shared":
							this.sharedSession = false;
							break;
						default:
							System.out.println("Unknown argument: "+tempArg);
					}
                }
                break;

			case "help":
				this.showHelp = true;
				break;

			case "NO_TOKEN":
				System.out.println("No argument given. Default mode : local single-user");
				break;


			default:
				System.out.println("Arguments unrecognized. Switching to default local single-user mode");
        }


		if (this.showHelp)
		{
			this.displayHelp();
		}else{
			System.out.println(this);
			if (this.setStreams()){
				this.launch();
			}else{
				System.out.println("Aborting launch");
			}
		}
	}


	private boolean setStreams(){
		// setting the streams
		boolean out = true;

		if (this.localSession){
			this.userIn = System.in;
			this.userOut = System.out;
		}
		
		if (this.replaySession){
			File f = new File(this.logFilename);
			// verifies the file exists
			if (!f.isFile() || !f.exists()){
				System.out.println("Replay file "+this.logFilename+" does not exists!");
				out = false;
			}
		}
		return out;

	}


	private void launch(){
		// launch either a single user or a multi-user session
		if (this.singleUser){
			this.launchSingle();
		}else{
			this.launchMultiple();
		}
	}

	private void launchSingle(){
		// launche a single-user session
		PileRPL pile = new PileRPL(this.pileSize); // create only one pile

		if (this.localSession)
		{
			// local session
			new CalcUI(pile, userIn, userOut, this.logFilename, this.logSession, this.replaySession, this.localSession, null);
		}
		else
		{
			ServerSocket waiter;
			Socket socket;
			// launch a server
			try {
				waiter = new ServerSocket(this.port);
				socket = waiter.accept();
				new CalcUI(pile, null, null, this.logFilename, this.logSession, this.replaySession, this.localSession, socket);
				
			}catch (IOException e){
			}
		}
	}


	private void launchMultiple(){
		// launch a multi-user session
		ServerSocket waiter;
		Socket socket;

		if (this.sharedSession)
		{
			PileRPL sharedPile = new PileRPL(this.pileSize); // create the shared pile
			try {
				waiter = new ServerSocket(this.port);
				// indefinitely launch the listening server
				while (true)
				{
					socket = waiter.accept();
					new CalcUI(sharedPile, null, null, this.logFilename, this.logSession, this.replaySession, this.localSession, socket);
				}
			}catch (IOException e){
				System.out.println("Error when connecting!");
			}

		}
		else
		{
			// not shared session
			try {
				waiter = new ServerSocket(this.port);
				while (true)
				{
					socket = waiter.accept();
					PileRPL newPile = new PileRPL(this.pileSize); // create a pile per user
					new CalcUI(newPile, null, null, this.logFilename, this.logSession, this.replaySession, this.localSession, socket);
				}
			}catch (IOException e){
				System.out.println("Error when connecting!");
			}
		}
	}



	public String toString(){
		// Displays the chosen modes
		String out = "Launched in mode:\n";
		if (this.localSession){
			out += "Local\n";
		}else{
			out += "Remote\n";
		}

		if (singleUser){
			out += "Single user\n";
			if (this.logSession){
				out += "Log ("+this.logFilename+")\n";
			}
			if (this.replaySession){
				out += "Replay ("+this.logFilename+")\n";
			}
		}else{
			if (this.sharedSession){
				out += "Shared\n";
			}else{
				out += "NOT shared\n";
			}
		}
		return out;
	}


	private void displayHelp(){
		String out = "";
		out += "Usage:\n";
        out += "$ java CalcUI size <size> (user ([log|replay] <file>) [local|remote]) (users remote [shared|not_shared])\n";
        out += "- size <value> : the created RPL will be created with this size (OPTIONAL)\n";
        out += "- user : specify the there will be only one user\n";
        out += "- users : multiple connection can be made\n";
        out += "\nOptions for single users\n";
        out += "- remote : the program can be accessed via socket\n";
        out += "- local : the program is directly accessible from the command line that launched it\n";
        out += "- log <file> : all of the user's commands will be stored in the file (OPTIONAL)\n";
        out += "- replay <file> : the file is used as input of the calculator (OPTIONAL)\n";
        out += "\nOptions for multiple users\n";
        out += "- remote : the program will be available via socket (OPTIONAL)\n";
        out += "- shared : a unique RPL Calculator is created, shared by every user\n";
        out += "- not-shared : each user gets its own RPL Calculator\n";
        out += "\nIf no arguments are provided, the program will start for one user in local mode\n";
        out += "The replay/log options are not available for multiple users\n";
        out += "There is no local options for multiple users\n";

		System.out.println(out);
	}


    public static void main(String[] args){

        CalcServer server = new CalcServer(args);
    }


}
