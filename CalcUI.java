import java.lang.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;
import java.net.*;

class CalcUI{


	private PileRPL pile;
	private Pattern realPattern;
	private Pattern complexPattern;
	private Pattern vector3DPattern;
	private Pattern cmdPattern;

	private boolean logSession;
	private boolean replaySession;

	private InputStream inStream;
	private OutputStream outStream;

	private String filename;

	private PrintStream outputUser;
	private BufferedReader inputUser;
	private PrintStream outFileStream;

	public CalcUI(int size, InputStream inStream, OutputStream outStream, String filename, boolean log, boolean replay){

		if (size > 0)
			this.pile = new PileRPL(size);
		else
			this.pile = new PileRPL();

		this.inStream = inStream;
		this.outStream = outStream;
		this.filename = filename;
		this.logSession = log;
		this.replaySession = replay;

		try{
			this.initStreams();
		}catch (FileNotFoundException e){
			System.exit(1);
		}
		this.initPatterns();
		this.launch();
		this.closeStreams();
	}


	private void initStreams() throws FileNotFoundException{

		if (this.replaySession){
			this.inputUser = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.filename)))); // input is a file
		}else{
			this.inputUser = new BufferedReader(new InputStreamReader(this.inStream)); // input is from the user
		}
	
		this.outputUser = new PrintStream(this.outStream); // output is always to the user

		if (this.logSession)
			this.outFileStream = new PrintStream(new FileOutputStream(new File(this.filename))); // log is always is file
	}


	private void closeStreams(){
		// close the streams here
		try {
		this.inputUser.close();
		}catch (IOException e){
			this.outputUser.println("Error when closin input!");
		}
	
		this.outputUser.close();
	}

	private void initPatterns(){
		this.realPattern = Pattern.compile("[\\+-]?\\p{Digit}+(\\.\\p{Digit}+)?");
		this.complexPattern = Pattern.compile("[\\+-]?\\p{Digit}+(\\.\\p{Digit}+)?[\\+-](\\p{Digit}+(\\.\\p{Digit}+)?)?[iI]");
		this.vector3DPattern = Pattern.compile("([\\+-]?\\p{Digit}+(\\.\\p{Digit}+)?,){2}[\\+-]?(\\p{Digit})+(\\.\\p{Digit}+)?");
		this.cmdPattern = Pattern.compile("(push|pop|disp|add|sub|exit|quit)");
	}

	private boolean isReal(String w){
		return this.realPattern.matcher(w).matches();
	}

	private boolean isComplex(String w){
		return this.complexPattern.matcher(w).matches();
	}

	private boolean isVector3D(String w){
		return this.vector3DPattern.matcher(w).matches();
	}

	private boolean isCommand(String w){
		return this.cmdPattern.matcher(w).matches();
	}

	private void addReal(String w){
		ObjEmp r = new RealEmp(Double.parseDouble(w));
		this.pile.push(r);
	}

	
	private void addComplex(String w){
		int indexSepSign = Math.max(w.indexOf("+", 1), w.indexOf("-", 1));
		double im = 1;
		double re = 1;

		if (indexSepSign == -1){
			// the string is only composed of an imaginary number !
			re = 0;
			indexSepSign = 0;
		}else{

			String res = w.substring(0,indexSepSign); // get the real part
			re = Double.parseDouble(res); // convert the real part to double
		}

		if (w.charAt(indexSepSign) == '-')
			im = -1; // if the sign is '-', the im part will be negative
		
		
		String ims = w.substring(indexSepSign+1, w.length()-1); // get the imaginary part
		if (ims.length() != 0) // the im part is only composed of i...
			im *= Double.parseDouble(ims); // so im = 1
		
		ObjEmp c = new ComplexEmp(re, im);
		this.pile.push(c);
	}

	private void addVector3D(String w){
		String[] words = w.split(",");
		double[] values = new double[3];
		for (int i=0; i<3; i++)
			values[i] = Double.parseDouble(words[i]);
		ObjEmp v3d = new Vector3DEmp(values[0], values[1], values[2]);
		this.pile.push(v3d);
	}
	


	public void launch(){
		boolean isOver = false;
		String[] words;
		Boolean isCmd;
		
		String line = "";

		try{
			line = this.inputUser.readLine();
		}catch (IOException e){
		}

		if (line == null)
			isOver = true;

		while (!isOver) {

			if (this.logSession){ // in log mode
				this.outFileStream.println(line); // print the commands to the log file
			}

			if (this.replaySession){ // in replay mode
				this.outputUser.println(line); // print the commands to the user
			}

			words = line.split(" ");

			if (this.isCommand(words[0])){
				switch (words[0]){

					case "exit":
					case "quit":
						isOver = true;
						break;
			
					case "push":
						for (int i=1; i<words.length; i++){

							if (this.isReal(words[i]))
								this.addReal(words[i]);
							else if (this.isComplex(words[i]))
								this.addComplex(words[i]);
							else if (this.isVector3D(words[i]))
								this.addVector3D(words[i]);

						}

						break;

					case "pop":
						this.pile.pop();
						break;
		
					case "disp":
						this.outputUser.println(this.pile);
						break;
			
					case "add":
						this.pile.add();
						break;
			
					case "sub":
						this.pile.sub();
						break;
				};

			}else{
				this.outputUser.println("Unknonw command: '"+words[0]+"'");
			}

			
			try{
				line = this.inputUser.readLine();
			}catch (IOException e){
				isOver = true;
			}

			if (line == null)
				isOver = true;

		}while (!isOver);
	}

}
