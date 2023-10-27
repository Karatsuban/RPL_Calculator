import java.io.*;
import java.net.*;

public class CalcServer{



    private boolean logSession;
    private boolean replaySession;
    private boolean localSession;
    private boolean singleUser;
    private boolean sharedSession;
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

			case "NO_TOKEN":
				System.out.println("No argument given. Default mode : local single-user");
				break;

			default:
				System.out.println("In default");
        }

		System.out.println(this);

		if (this.setStreams()){
			this.launch();
		}else{
			System.out.println("Aborting launch");
		}

	}

	private boolean setStreams(){
		boolean out = true;

		if (this.localSession){
			this.userIn = System.in;
			this.userOut = System.out;
		}
		
		if (this.replaySession){
			File f = new File(this.logFilename);
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
		PileRPL pile = new PileRPL(this.pileSize);

		if (this.localSession)
		{
			new CalcUI(pile, userIn, userOut, this.logFilename, this.logSession, this.replaySession, this.localSession, null);
		}
		else
		{
			ServerSocket waiter;
			Socket socket;

			try {
				waiter = new ServerSocket(this.port);
				socket = waiter.accept();
				new CalcUI(pile, null, null, this.logFilename, this.logSession, this.replaySession, this.localSession, socket);
				
			}catch (IOException e){
			}
		}
	}


	private void launchMultiple(){
		ServerSocket waiter;
		Socket socket;

		if (this.sharedSession)
		{
			PileRPL sharedPile = new PileRPL(this.pileSize);
			try {
				waiter = new ServerSocket(this.port);
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

			try {
				waiter = new ServerSocket(this.port);
				while (true)
				{
					socket = waiter.accept();
					PileRPL newPile = new PileRPL(this.pileSize);
					new CalcUI(newPile, null, null, this.logFilename, this.logSession, this.replaySession, this.localSession, socket);
				}
			}catch (IOException e){
				System.out.println("Error when connecting!");
			}
		}
	}



	public String toString(){
		String out = "Vars:\n";
		out += "log :\t\t"+this.logSession+"\n";
        out += "replay: \t"+this.replaySession+"\n";
        out += "local: \t\t"+this.localSession+"\n";
        out += "singleUser: \t"+this.singleUser+"\n";
        out += "shared: \t"+this.sharedSession+"\n";
		out += "\n";
		return out;
	}



    public static void main(String[] args){

        // usage :
        // $ java CalcUI size <size> (user [log|replay|local|remote]) (users remote [shared|solo])


		// TODO AJOUTER L'AIDE (-h ou help)

        CalcServer server = new CalcServer(args);
    }


}
