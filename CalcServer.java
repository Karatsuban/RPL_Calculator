import java.io.*;

public class CalcServer{



    private boolean logSession;
    private boolean replaySession;
    private boolean localSession;
    private boolean singleUser;
    private boolean sharedSession;

    private String logFilename;

	private CalcUI calc;

	public CalcServer(String[] args){

		for (String a: args)
			System.out.println("#"+a+"#");

        Parser argsParser = new Parser(args);

        this.logSession = false;
        this.replaySession = false;
        this.localSession = true;
        this.singleUser = true;
        this.sharedSession = false;

		int size = -1;

        if (argsParser.getToken().equals("size")){
            size = Integer.valueOf(argsParser.getAhead(1));
            argsParser.advance(2);
        }

		System.out.println("size: "+size+"\n");


        switch (argsParser.consume()){
            case "user":
				System.out.println("In user");
				switch (argsParser.consume()){
                    case "log":
                        this.logSession = true;
                        this.logFilename = argsParser.consume(); // get the file name
                        break;
                    case "replay":
                        this.replaySession = true;
                        this.logFilename = argsParser.consume(); // get the file name
                        break;
                    case "local":
                        this.localSession = true;
                        break;
                    case "remote":
                        this.localSession = false;
                        break;
                    case "NO_TOKEN":
                        this.localSession = true;
                        this.logSession = false;
                        this.logFilename = null;
						break;
                }
                break;

            case "users":
				System.out.println("in USERS");
				this.singleUser = false;
				this.localSession = false;
				System.out.println("HERE");
                switch (argsParser.consume()){
                    case "remote":
                        break;
                    case "shared":
                        this.sharedSession = true;
						break;

                }
                break;

			case "NO_TOKEN":
				System.out.println("Malformed arguments!");
				break;

			default:
				System.out.println("In default");
        }

		System.out.println(this);


		// values by default
		InputStream userIn = System.in;
		OutputStream userOut = System.out;
		OutputStream logOut = null;

		if (this.localSession){
			userIn = System.in;
			userOut = System.out;
		}

		if (this.replaySession){
			try{
				userIn = new FileInputStream(new File(this.logFilename));
			}catch (FileNotFoundException e){
				System.out.println(e.getMessage());
			}
			userOut = System.out;
		}


		this.calc = new CalcUI(size, userIn, userOut, this.logFilename, this.logSession, this.replaySession);

	}


	public String toString(){
		String out = "Vars:\n";
		out += "log :"+this.logSession+"\n";
        out += "replay: "+this.replaySession+"\n";
        out += "local: "+this.localSession+"\n";
        out += "singleUser: "+this.singleUser+"\n";
        out += "shared: "+this.sharedSession+"\n";
		out += "\n";
		return out;
	}



    public static void main(String[] args){

        // usage :
        // $ java CalcUI size <size> (user [log|replay|local|remote]) (users remote [shared|solo])

        CalcServer server = new CalcServer(args);
    }


}
