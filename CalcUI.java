import java.lang.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class CalcUI{


	private boolean isOver;
	private PileRPL pile;
	private Pattern doublePattern;
	private Pattern realPattern;

	public CalcUI(int size){
		this.isOver = false;
		this.pile = new PileRPL(size);
		this.initPatterns();
	}

	public CalcUI(){
		this.isOver = false;
		this.pile = new PileRPL();
		this.initPatterns();
	}


	private void initPatterns(){
		this.realPattern = Pattern.compile("[\\+-]?\\p{Digit}(\\.\\p{Digit})?");
		this.imaginaryPattern = Pattern.compile("[\\+-]?\\p{Digit}(\\.\\p{Digit})?[\\+-]\\p{Digit}(\\.\\p{Digit})?[iI]");
		this.cmdPattern = Pattern.compile("(push|disp|add|sub)");
	}

	private boolean isReal(String w){
		return this.realPattern.matcher(w).matches();
	}

	private boolean isImaginary(String w){
		return this.imaginaryPattern.matcher(w).matches();
	}

	private boolean isCommand(String w){
		return this.cmdPattern.matcher(w).matches();
	}

		

	private void analyze(String s){
		String[] words = s.split(" "); // split at spaces
		
			
	}

	public void launch(){
		Scanner sc;
		String[] words;
		Boolean isCmd;
		String line;
		while (!this.isOver){
			sc = new Scanner(System.in);
			line = sc.nextLine();
			words = line.split(" ");
			if (words[0].equals("exit"))
				this.isOver = true;

			isCmd = this.isCommand(line);
			

			if (this.isCommand(line)){
				switch (line){

					case "push":
						System.out.println("This is push!");
						break;
					case "disp":
						System.out.println(this.pile);
						break;
					case "add":
						System.out.println("This is add!");
						break;
					case "sub":
						System.out.println("This is sub!");
						break;
					default:

				};
			}

		}
	}

	public static void main(String[] args){

		CalcUI UI = null;

		if (args.length > 0)
			UI = new CalcUI(Integer.valueOf(args[0]));
		else
			UI = new CalcUI();

		UI.launch();
	}
}
